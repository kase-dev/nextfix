package kz.kase.fix.test;

import kz.kase.fix.MDEntryType;

public class QuoteHolder {

    private double price;
    private long qty;
    private MDEntryType type;

    public QuoteHolder() {
    }

    public QuoteHolder(double price, long qty, MDEntryType type) {
        this.price = price;
        this.qty = qty;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public MDEntryType getType() {
        return type;
    }

    public void setType(MDEntryType type) {
        this.type = type;
    }
}
