/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved. 
 *
 * This file is part of the QuickFIX FIX Engine 
 *
 * This file may be distributed under the terms of the quickfixengine.org 
 * license as defined by quickfixengine.org and appearing in the file 
 * LICENSE included in the packaging of this file. 
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING 
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information. 
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing 
 * are not clear to you.
 ******************************************************************************/

package quickfix.mina.message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecException;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.quickfixj.CharsetSupport;
import quickfix.Message;

import java.io.UnsupportedEncodingException;

/**
 * Encodes a Message object or message string as a byte array to be
 * transmitted on MINA connection.
 */
public class FIXMessageEncoder implements MessageEncoder<Message> {

    private final String charsetEncoding;


    public FIXMessageEncoder() {
        charsetEncoding = CharsetSupport.getCharset();
    }


    public void encode(IoSession session, Message message, ProtocolEncoderOutput out)
            throws ProtocolCodecException {
        String fixMessageString = message.toString();
        byte[] src;
        try {
            src = fixMessageString.getBytes(charsetEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new ProtocolCodecException(e);
        }

        IoBuffer buffer = IoBuffer.allocate(src.length);
        buffer.put(src);
        buffer.flip();
        out.write(buffer);
    }
}
