package kz.kase.fix.core;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Detects and decodes FIX message strings in an incoming data stream. The
 * message string is then passed to MINA IO handlers for further processing.
 */
public class FixMessageDecoder {

    public static final String FIELD_DELIMITER = "\001";

    private final byte[] HEADER_PATTERN;
    private final byte[] CHECKSUM_PATTERN;
    private final byte[] LOGON_PATTERN;

    // Parsing states
    private static final int SEEKING_HEADER = 1;
    private static final int PARSING_LENGTH = 2;
    private static final int READING_BODY = 3;
    private static final int PARSING_CHECKSUM = 4;


    private int state;
    private int bodyLength;
    private int position;
    private final String charsetEncoding;

    private static class BufPos {
        int offset;
        int length;

        public BufPos(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        public String toString() {
            return offset + "," + length;
        }
    }

    private void resetState() {
        state = SEEKING_HEADER;
        bodyLength = 0;
        position = 0;
    }


    public FixMessageDecoder(String charset) {
        this(charset, FIELD_DELIMITER);
    }

    public FixMessageDecoder(String charset, String delimiter) {
        charsetEncoding = charset;
//        HEADER_PATTERN = getBytes("8=FIXt.?.?" + delimiter + "9=", charset);
        HEADER_PATTERN = getBytes("8=FIX?.?" + delimiter + "9=", charset);
        CHECKSUM_PATTERN = getBytes("10=???" + delimiter, charset);
        LOGON_PATTERN = getBytes("\00135=A" + delimiter, charset);
        resetState();
    }


    public String decode(byte[] bytes) {
        return decode(ByteBuffer.wrap(bytes));
    }

    public String decode(ByteBuffer in) {
        int messageCount = 0;
        StringBuilder out = new StringBuilder();
        while (parseMessage(in, out)) {
            messageCount++;
        }
        if (messageCount > 0) {
            // Mina will compact the buffer because we can't detect a header
            if (in.remaining() < minMaskLength(HEADER_PATTERN)) {
                position = 0;
            }
            return out.toString();
        } else {
            // Mina will compact the buffer
            position -= in.position();
            return null;
        }
    }

    /**
     * This method cannot move the buffer position until a message is found or an
     * error has occurred. Otherwise, MINA will compact the buffer and we lose
     * data.
     */
    private boolean parseMessage(ByteBuffer in, StringBuilder out) {
        try {
            boolean messageFound = false;
            while (in.hasRemaining() && !messageFound) {
                if (state == SEEKING_HEADER) {

                    BufPos bufPos = indexOf(in, position, HEADER_PATTERN);
                    int headerOffset = bufPos.offset;
                    if (headerOffset == -1) {
                        break;
                    }
                    in.position(headerOffset);

                    position = headerOffset + bufPos.length;
                    state = PARSING_LENGTH;
                }

                if (state == PARSING_LENGTH) {
                    byte ch = 0;
                    while (hasRemaining(in)) {
                        ch = get(in);
                        if (!Character.isDigit((char) ch)) {
                            break;
                        }
                        bodyLength = bodyLength * 10 + (ch - '0');
                    }
                    if (ch == '\001') {
                        state = READING_BODY;
                    } else {
                        if (hasRemaining(in)) {
                            String messageString = getMessageStringForError(in);
                            handleError(in, in.position() + 1, "Length format error in message (last character:" + ch + "): " + messageString,
                                    false);
                            continue;
                        } else {
                            break;
                        }
                    }

                }

                if (state == READING_BODY) {
                    if (remaining(in) < bodyLength) {
                        break;
                    }
                    position += bodyLength;
                    state = PARSING_CHECKSUM;
                }

                if (state == PARSING_CHECKSUM) {
                    if (startsWith(in, position, CHECKSUM_PATTERN) > 0) {

                        position += CHECKSUM_PATTERN.length;
                    } else {
                        if (position + CHECKSUM_PATTERN.length <= in.limit()) {
                            // FEATURE allow configurable recovery position
                            // int recoveryPosition = in.position() + 1;
                            // Following recovery position is compatible with QuickFIX C++
                            // but drops messages unnecessarily in corruption scenarios.
                            int recoveryPosition = position + 1;
                            handleError(in, recoveryPosition,
                                    "did not find checksum field, bad length?", isLogon(in));
                            continue;
                        } else {
                            break;
                        }
                    }
                    String messageString = getMessageString(in);

                    out.append(messageString);
                    state = SEEKING_HEADER;
                    bodyLength = 0;
                    messageFound = true;
                }
            }
            return messageFound;
        } catch (Throwable t) {
            state = SEEKING_HEADER;
            position = 0;
            bodyLength = 0;
            throw new IllegalStateException(t);
        }
    }

    private int remaining(ByteBuffer in) {
        return in.limit() - position;
    }

    private String getBufferDebugInfo(ByteBuffer in) {
        return "pos=" + in.position() + ",lim=" + in.limit() + ",rem=" + in.remaining()
                + ",offset=" + position + ",state=" + state;
    }

    private byte get(ByteBuffer in) {
        return in.get(position++);
    }

    private boolean hasRemaining(ByteBuffer in) {
        return position < in.limit();
    }

    private static int minMaskLength(byte[] data) {
        int len = 0;
        for (byte b : data) {
            if (Character.isLetter(b) && Character.isLowerCase(b))
                continue;
            ++len;
        }
        return len;
    }

    private String getMessageString(ByteBuffer buffer) throws UnsupportedEncodingException {
        byte[] data = new byte[position - buffer.position()];
        buffer.get(data);
        return new String(data, charsetEncoding);
    }

    private String getMessageStringForError(ByteBuffer buffer) throws UnsupportedEncodingException {
        int initialPosition = buffer.position();
        byte[] data = new byte[buffer.limit() - initialPosition];
        buffer.get(data);
        buffer.position(position - initialPosition);
        return new String(data, charsetEncoding);
    }

    private void handleError(ByteBuffer buffer, int recoveryPosition, String text, boolean disconnect) {
        buffer.position(recoveryPosition);
        position = recoveryPosition;
        state = SEEKING_HEADER;
        bodyLength = 0;
        if (disconnect) {
            throw new IllegalStateException(text);
        } else {
            System.err.println(text);
        }
    }

    private boolean isLogon(ByteBuffer buffer) {
        BufPos bufPos = indexOf(buffer, buffer.position(), LOGON_PATTERN);
        return bufPos.offset != -1;
    }

    private static BufPos indexOf(ByteBuffer buffer, int position, byte[] data) {
        for (int offset = position, limit = buffer.limit() - minMaskLength(data) + 1; offset < limit; offset++) {
            int length;
            if (buffer.get(offset) == data[0] && (length = startsWith(buffer, offset, data)) > 0) {
                return new BufPos(offset, length);
            }
        }
        return new BufPos(-1, 0);
    }

    /**
     * Checks to see if the byte_buffer[buffer_offset] starts with data[]. The
     * character ? is a one byte wildcard, lowercase letters are optional.
     *
     * @param buffer
     * @param bufferOffset
     * @param data
     * @return
     */
    private static int startsWith(ByteBuffer buffer, int bufferOffset, byte[] data) {
        if (bufferOffset + minMaskLength(data) > buffer.limit()) {
            return -1;
        }
        final int initOffset = bufferOffset;
        int dataOffset = 0;
        for (int bufferLimit = buffer.limit(); dataOffset < data.length
                && bufferOffset < bufferLimit; dataOffset++, bufferOffset++) {
            if (buffer.get(bufferOffset) != data[dataOffset] && data[dataOffset] != '?') {
                // Now check for optional characters, at this point we know we didn't
                // match, so we can just check to see if we failed a match on an optional character,
                // and if so then just rewind the buffer one byte and keep going.
                if (Character.toUpperCase(data[dataOffset]) == buffer.get(bufferOffset))
                    continue;
                // Didn't match the optional character, so act like it was not included and keep going
                if (Character.isLetter(data[dataOffset]) && Character.isLowerCase(data[dataOffset])) {
                    --bufferOffset;
                    continue;
                }
                return -1;
            }
        }
        return bufferOffset - initOffset;
    }


    private static byte[] getBytes(String s, String charset) {
        try {
            return s.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}