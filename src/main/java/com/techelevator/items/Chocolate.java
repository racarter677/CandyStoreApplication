package com.techelevator.items;

import java.math.BigDecimal;

public class Chocolate extends Candy {

    public Chocolate(String ID, String name, BigDecimal price, boolean wrapper) {
        super(ID, name, price, wrapper);

    }


    @Override
    public String toString() {
        return "Chocolate{}" + getName() + getID();
    }
}
