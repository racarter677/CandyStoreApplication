package com.techelevator.items;

import java.math.BigDecimal;

public class Licorice extends Candy{

    public Licorice(String ID, String name, BigDecimal price, boolean wrapper) {
        super(ID, name, price, wrapper);
    }

    public String getCandyType() {
        return "Licorice and Jellies";
    }

    @Override
    public String toString() {
        return "Licorice{}" + getName() + getID();
    }

}
