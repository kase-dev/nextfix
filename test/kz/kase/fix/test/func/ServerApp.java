package kz.kase.fix.test.func;

import kz.kase.fix.SecurityListRequestType;
import kz.kase.fix.SubscriptionType;
import kz.kase.fix.messages.*;
import quickfix.*;

import java.util.Random;


public class ServerApp implements Application {



    public void onCreate(SessionID sessionID) {
    }

    public void onLogon(SessionID sessionID) {
    }

    public void onLogout(SessionID sessionID) {
    }

    public void toAdmin(Message message, SessionID sessionID) {
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
    }

    public void fromAdmin(Message message, SessionID sessionID)
            throws IncorrectDataFormat,
            IncorrectTagValue, RejectLogon {

        if (message instanceof Logon) {
            process((Logon) message, sessionID);
        }
    }

    public void fromApp(Message message, SessionID sessionID)
            throws IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {


        if (message instanceof NewOrderSingle) {
            process((NewOrderSingle) message, sessionID);

        } else if (message instanceof OrderCancelRequest) {
            process((OrderCancelRequest) message, sessionID);

        } else if (message instanceof SecurityListRequest) {
            process((SecurityListRequest) message, sessionID);

        } else if (message instanceof MarketDataRequest) {
            process((MarketDataRequest) message, sessionID);
        }

    }


//-----------------------------------------------------------------------------------------------------

    private void process(Logon request, SessionID sessionID) {

        System.out.println("Logon from: " + sessionID.getTargetCompID());

        if (request.hasUsername() && request.hasPassword()) {
            String username = request.getUsername();
            String pass = request.getPassword();

            System.out.println("\tname: " + username);
            System.out.println("\tpass: " + pass);
            System.out.println();
        }

    }


    private void process(NewOrderSingle request, SessionID sessionID) {

        Long ref = request.getRef();

        System.out.println();
        System.out.println("Got limit order request from client: " + sessionID);
        System.out.println("Price: " + request.getPrice());
        System.out.println("Qty: " + request.getQty());
        System.out.println("Side: " + request.getSide());
        System.out.println("Symbol: " + request.getSymbol());
        System.out.println("Ref: " + ref);
        System.out.println();

    }


    private void process(OrderCancelRequest request, SessionID sessionID) {
        System.out.println();
        System.out.println("Got order cancel request from: " + sessionID);
        System.out.println("Order serial: " + request.geOrderSerial());
        System.out.println("Ref: " + request.getRef());
        System.out.println();
    }


    private void process(SecurityListRequest request, SessionID sessionID) {
        Long ref = request.getRef();
        long respRef = nextRef();
        SecurityListRequestType type = request.getType();

        System.out.println("Got security-list request");
        System.out.println("ref: " + ref);
        System.out.println("type: " + type);

        SecurityList res = new SecurityList();
        res.setRef(ref);
        res.setRespId(respRef);
        sendMessage(sessionID, res);
    }


    private void process(MarketDataRequest request, SessionID sessionID) {
        Long reqRef = request.getRef();
        SubscriptionType type = request.getSubscriptionType();

        System.out.println("Got market-data request");
        System.out.println("ref: " + reqRef);
        System.out.println("type: " + type);

        long ref = nextRef();

        MDIncRefresh md = new MDIncRefresh();
        md.setRef(ref);
        md.setMDReqId(reqRef);


        if (type == SubscriptionType.SNAPSHOT) {

        } else if (type == SubscriptionType.SNAPSHOT_AND_UPDATES) {

        }

        sendMessage(sessionID, md);

    }


    public static long nextRef() {
        return new Random().nextInt(1000);
    }


//-----------------------------------------------------------------------------------------------------


    private void sendMessage(SessionID sessionID, Message message) {
        Session session = Session.lookupSession(sessionID);
        if (session != null /*&& validate(message, session)*/) {
            session.send(message);
        }
    }

}
