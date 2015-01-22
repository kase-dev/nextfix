package kz.kase.fix.test.func;

import kz.kase.fix.core.FixMessageEncoder;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.messages.Heartbeat;
import kz.kase.fix.messages.Logout;
import kz.kase.fix.messages.TestRequest;
import quickfix.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class KaseFixClient implements Runnable {

    private Socket socket;
    private DataDictionary dictionary;
    private MessageFactory factory;
    private FixMessageEncoder encoder;
    private BlockingQueue<Message> sendQueue = new ArrayBlockingQueue<Message>(1000);

    private static int mesSeqNum = 1;

    public KaseFixClient(DataDictionary dictionary, MessageFactory factory, String charset) {
        this.dictionary = dictionary;
        this.factory = factory;
        encoder = new FixMessageEncoder(charset);
    }

    public void connect(String host, int port) throws IOException {
        System.out.println("Connecting client to " + host + ":" + port);
        socket = new Socket(host, port);
    }

    public void startThreads() {
        new Thread(this, "Client Reader").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Message mes = null;
                    try {
                        mes = sendQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mes != null) {
                        try {
                            OutputStream out = socket.getOutputStream();
                            out.write(encoder.encode(mes));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Client Writer").start();
    }

    public void sendMessage(Message message) throws IOException {

        prepareMessage(message);

        if (!(message instanceof Heartbeat)) {
            System.out.println("Sending message: " + message);
        }

        try {
            sendQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareMessage(Message mes) {
        String beginString = "FIX.4.4";
        String senderCompID = "FixClient";
        String senderSubID = SessionID.NOT_SET;
        String senderLocationID = SessionID.NOT_SET;
        String targetCompID = "FixServer";
        String targetSubID = SessionID.NOT_SET;
        String targetLocationID = SessionID.NOT_SET;

        FixUtils.initializeHeader(mes.getHeader(),
                beginString,
                senderCompID, senderSubID, senderLocationID,
                targetCompID, targetSubID, targetLocationID,
                mesSeqNum++);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                InputStream in = socket.getInputStream();
                int len = in.available();
                if (len > 0) {
                    byte[] bytes = new byte[len];
                    in.read(bytes);
                    process(new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void process(String message) {
        Message mes = null;
        try {
            mes = MessageUtils.parse(factory, dictionary, message);
        } catch (InvalidMessage e) {
            e.printStackTrace();
        }
        if (mes != null) {
            process(mes);
        }
    }

    private void process(Message message) {
        if (message instanceof TestRequest) {
            TestRequest test = (TestRequest) message;

            try {
                sendMessage(new Heartbeat()
                        .setTestReqId(test.getTestReqId()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (message instanceof Heartbeat) {


        } else if (message instanceof Logout) {
            Logout logout = (Logout) message;
            System.out.println("Got logout message: ");
            System.out.println("\terMes: " + logout.getText());


        } else {
            System.out.println("Got message of type: " + message.getClass() + "\n\t" + message.toString());

        }

    }

}
