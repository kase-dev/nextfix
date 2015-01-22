package kz.kase.fix.core;

import quickfix.Message;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class FixMessageEncoder {

    private final String charsetEncoding;

    public FixMessageEncoder(String charset) {
        charsetEncoding = charset;
    }


    public byte[] encode(Message message) {
        String fixMes = message.toString();

        byte[] bytes;
        try {
            bytes = fixMes.getBytes(charsetEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.array();
    }
}
