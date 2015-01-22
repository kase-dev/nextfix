package kz.kase.fix.test;

import kz.kase.fix.core.FixMessageEncoder;
import kz.kase.fix.core.FixMessageDecoder;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.messages.Logon;
import org.junit.Test;
import org.quickfixj.CharsetSupport;
import quickfix.Message;
import quickfix.MessageUtils;
import quickfix.SessionID;

import java.io.IOException;

public class TestEncode {

    @Test
    public void test() throws IOException {

        Logon logon = new Logon()
                .setUsername("U001")
                .setPassword("123");

        prepareMessage(logon);

        System.out.println(logon);

        String charset = CharsetSupport.getCharset();

        byte[] bytes = new FixMessageEncoder(charset).encode(logon);
//        String mes = logon.toString() + FixMessageDecoder.FIELD_DELIMITER;
//        bytes = mes.getBytes();

        String decoded = new FixMessageDecoder(charset).decode(bytes);

        System.out.println("decoded = " + decoded);
//        client.sendMessage(logon);

        SessionID remoteSessionID = MessageUtils.getReverseSessionID(decoded);

        System.out.println("remoteSessionID = " + remoteSessionID);
    }



    public static void prepareMessage(Message mes) {
        String beginString = "FIX4.4";
        String senderCompID = "FixClient";
        String senderSubID = SessionID.NOT_SET;
        String senderLocationID = SessionID.NOT_SET;
        String targetCompID = "FixServer";
        String targetSubID = SessionID.NOT_SET;
        String targetLocationID = SessionID.NOT_SET;
        int expectedSenderNum = 0;
        FixUtils.initializeHeader(mes.getHeader(),
                beginString,
                senderCompID, senderSubID, senderLocationID,
                targetCompID, targetSubID, targetLocationID,
                expectedSenderNum);
    }


}
