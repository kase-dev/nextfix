package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class MarketDataRequestReject extends Message {
    public MarketDataRequestReject() {
        super(new int[]{262, 58});
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_MARKET_DATA_REQUEST_REJECT);
    }
}
