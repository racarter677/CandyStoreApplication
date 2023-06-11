package com.techelevator.items;

import java.math.BigDecimal;

public class HardCandy extends Candy{

    public HardCandy(String ID, String name, BigDecimal price, boolean wrapper) {
        super(ID, name, price, wrapper);
    }

    public HardCandy(String ID, String name, BigDecimal price, boolean wrapper, boolean isCurrentInventory) {
        super(ID, name, price, wrapper, isCurrentInventory);
    }

    public String getCandyType() {
        return "Hard Tack Confectionery";
    }

    @Override
    public String toString() {
        return  "Hard Candy{}" + getName() + getID();
    }

}
