package com.techelevator.items;

import java.math.BigDecimal;

public class Sours extends Candy{
    public Sours(String ID, String name, BigDecimal price, boolean wrapper) {
        super(ID, name, price, wrapper);
    }

    public String getCandyType() {
        return "Sour Flavored Candies";
    }

    @Override
    public String toString() {
        return "Sours{}" + getName() + getID();
    }

}
