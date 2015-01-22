package kz.kase.fix.test;

import kz.kase.fix.TradeCondition;

import java.util.ArrayList;
import java.util.List;

public class InstrHolder {

    private Long id;
    private String symbol;
    private TradeCondition tradeCondition;

    private Double bestBuyPrice;
    private Long bestBuyVolume;

    private Double bestSellPrice;
    private Long bestSellVolume;

    private Double lowPrice;
    private Long lowVolume;

    private Double highPrice;
    private Long highVolume;

    private Double openPrice;
    private Long openVolume;

    private Double closePrice;
    private Long closeVolume;

    private Long tradeVolume;
    private String tradeSessionId;
    private String tradeSessionSubId;
    private Integer tradeSessionNum;

    private Integer numberOfOrders;
    private Double netChgPrevDay;
    private Double lastPx;
    private Double priceDelta;
    private String TradeSessionSerial;

    private List<QuoteHolder> quotes = new ArrayList<QuoteHolder>();

    public InstrHolder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TradeCondition getTradeCondition() {
        return tradeCondition;
    }

    public void setTradeCondition(TradeCondition tradeCondition) {
        this.tradeCondition = tradeCondition;
    }

    public Double getBestBuyPrice() {
        return bestBuyPrice;
    }

    public void setBestBuyPrice(Double bestBuyPrice) {
        this.bestBuyPrice = bestBuyPrice;
    }

    public Long getBestBuyVolume() {
        return bestBuyVolume;
    }

    public void setBestBuyVolume(Long bestBuyVolume) {
        this.bestBuyVolume = bestBuyVolume;
    }

    public Double getBestSellPrice() {
        return bestSellPrice;
    }

    public void setBestSellPrice(Double bestSellPrice) {
        this.bestSellPrice = bestSellPrice;
    }

    public Long getBestSellVolume() {
        return bestSellVolume;
    }

    public void setBestSellVolume(Long bestSellVolume) {
        this.bestSellVolume = bestSellVolume;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Long getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(Long tradeVolume) {
        this.tradeVolume = tradeVolume;
    }


    public String getTradeSessionId() {
        return tradeSessionId;
    }

    public void setTradeSessionId(String tradeSessionId) {
        this.tradeSessionId = tradeSessionId;
    }

    public String getTradeSessionSubId() {
        return tradeSessionSubId;
    }

    public void setTradeSessionSubId(String tradeSessionSubId) {
        this.tradeSessionSubId = tradeSessionSubId;
    }

    public Integer getTradeSessionNum() {
        return tradeSessionNum;
    }

    public void setTradeSessionNum(Integer tradeSessionNum) {
        this.tradeSessionNum = tradeSessionNum;
    }


    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public Double getNetChgPrevDay() {
        return netChgPrevDay;
    }

    public void setNetChgPrevDay(Double netChgPrevDay) {
        this.netChgPrevDay = netChgPrevDay;
    }

    public Double getLastPx() {
        return lastPx;
    }

    public void setLastPx(Double lastPx) {
        this.lastPx = lastPx;
    }


    public Double getPriceDelta() {
        return priceDelta;
    }

    public void setPriceDelta(Double priceDelta) {
        this.priceDelta = priceDelta;
    }

    public Long getLowVolume() {
        return lowVolume;
    }

    public void setLowVolume(Long lowVolume) {
        this.lowVolume = lowVolume;
    }

    public Long getHighVolume() {
        return highVolume;
    }

    public void setHighVolume(Long highVolume) {
        this.highVolume = highVolume;
    }

    public Long getOpenVolume() {
        return openVolume;
    }

    public void setOpenVolume(Long openVolume) {
        this.openVolume = openVolume;
    }

    public Long getCloseVolume() {
        return closeVolume;
    }

    public void setCloseVolume(Long closeVolume) {
        this.closeVolume = closeVolume;
    }

    public List<QuoteHolder> getQuotes() {
        return quotes;
    }

    public void addQoute(QuoteHolder quote) {
        quotes.add(quote);
    }

    public QuoteHolder getQoute(double price) {
        for (QuoteHolder e : quotes) {
            if (Math.abs(e.getPrice() - price) < 0.0000001) {
                return e;
            }
        }
        return null;
    }

    public void setTradeSessionSerial(String tradeSessionSerial) {
        TradeSessionSerial = tradeSessionSerial;
    }

    public String getTradeSessionSerial() {
        return TradeSessionSerial;
    }
}
