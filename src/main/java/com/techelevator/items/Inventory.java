package com.techelevator.items;

import com.techelevator.filereader.InventoryFileReader;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Inventory {

    private final int STARTING_QTY = 100;
    private Map<String, Candy> inventory;
    private Map<String, Integer> inventoryQuantities = new HashMap<>();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();

    public Inventory(Map<String, Candy> inventory) {
        this.inventory = inventory;
        for (Map.Entry<String, Candy> candy : inventory.entrySet()) {
            inventoryQuantities.put(candy.getKey(), STARTING_QTY);
        }
    }

    public void removeFromInventory(String iD, int quantityToRemove) {
        int quantityAfterRemoval = getCandyQuantity(iD) - quantityToRemove;
        if (quantityAfterRemoval >= 0) {
            inventoryQuantities.put(iD, quantityAfterRemoval);
        } else throw new IllegalArgumentException("That would reduce quantity below 0.");
    }

    public Map<String, Integer> getAllInventoryQuantities() {
        return inventoryQuantities;
    }

    public int getCandyQuantity(String iD) {
        int currentInventory = 0;
        try {
            currentInventory = inventoryQuantities.get(iD);
        } catch (Exception e) {
            throw new IllegalArgumentException("No candy found with ID: " + iD);
        }
        return currentInventory;
    }

    public Map<String, Candy> getInventoryMap() {
        return inventory;
    }

    @Override
    public String toString() {
        Map<String, Integer> sortedInv = new TreeMap<>(inventoryQuantities);
        StringBuilder str = new StringBuilder(String.format("%-6s%-20s%-10s%-11s%-10s\n", "Id", "Name", "Wrapper", "Qty", "Price"));
        for (Map.Entry<String, Integer> iD : sortedInv.entrySet()) {
            String qty = iD.getValue().toString();
            if (iD.getValue() == 0) qty = "SOLD OUT";
            Candy candy = inventory.get(iD.getKey());
            String name = candy.getName();
            String wrapper = candy.getWrapper();
            BigDecimal price = candy.getPrice();
            str.append(String.format("%-6s%-20s%-10s%-11s%-10s\n", iD.getKey(), name, wrapper, qty, currency.format(price)));
        }
        return str.toString();
    }

    // Take inventory map and attach inventory quantities to each candy

    // Subtract from inventory

    // Check if inventory is available

    //
}
