package com.techelevator.items;

import java.math.BigDecimal;

public abstract class Candy {
    private String ID;
    private String name;
    private BigDecimal price;
    private boolean wrapper;

    public Candy(String ID, String name, BigDecimal price, boolean wrapper) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.wrapper = wrapper;
    }

    public String getWrapper() {
        if (isWrapper()) {
            return "Y";
        }
        else if (!isWrapper()) {
            return "N";
        }
        else {
            return "unknown";
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isWrapper() {
        return wrapper;
    }

    public void setWrapper(boolean wrapper) {
        this.wrapper = wrapper;
    }

    public abstract String getCandyType();

}
