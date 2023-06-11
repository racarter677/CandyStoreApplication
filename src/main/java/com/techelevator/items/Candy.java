package com.techelevator.items;

import java.math.BigDecimal;

public abstract class Candy {
    private String ID;
    private String name;
    private BigDecimal price;
    private boolean wrapper;
    private int inventoryCount;
    private int totalSold;
    private BigDecimal totalRevenue = new BigDecimal("0.00");
    private final int STARTING_QTY = 100;
    private boolean isActiveInventory;

    public Candy(String ID, String name, BigDecimal price, boolean wrapper) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.wrapper = wrapper;
        inventoryCount = STARTING_QTY;
    }

    public Candy(String ID, String name, BigDecimal price, boolean wrapper, boolean isActiveInventory) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.wrapper = wrapper;
        this.isActiveInventory = isActiveInventory;
        inventoryCount = STARTING_QTY;
    }

    public String getWrapper() {
        if (wrapper) return "Y";
        else return "N";
    }

    public String getID() {
        return ID;
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

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold += totalSold;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public int getTotalSold() {
        return totalSold + (STARTING_QTY - inventoryCount);
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = this.totalRevenue.add(totalRevenue);
    }

    public BigDecimal getTotalRevenue() {
        return price.multiply(new BigDecimal(STARTING_QTY - inventoryCount)).add(totalRevenue);
    }

    public boolean isActiveInventory() {
        return isActiveInventory;
    }



    public abstract String getCandyType();

}
