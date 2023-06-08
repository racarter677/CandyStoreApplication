package com.techelevator.items;

import java.math.BigDecimal;

public class HardCandy extends Candy{

    public HardCandy(String ID, String name, BigDecimal price, boolean wrapper) {
        super(ID, name, price, wrapper);
    }

    @Override
    public String getCandyType() {
        return "Hard Candy";
    }

    @Override
    public String toString() {
        return  "Hard Candy{}" + getName() + getID();
    }

}
