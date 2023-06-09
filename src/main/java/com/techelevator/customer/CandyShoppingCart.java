package com.techelevator.customer;

import com.techelevator.items.Candy;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class CandyShoppingCart {

    //private  List <Candy> shoppingCartList = new ArrayList<>();
    private Map<String, Candy> shoppingCartList = new HashMap<>();
    private Map<String, Integer> shoppingCartWithQty = new LinkedHashMap<>();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal totalCost = new BigDecimal("0.00");

    public void addCandyToShoppingCart(Candy candyToAdd, int qty) {
        String candyID = candyToAdd.getID();
        shoppingCartList.put(candyID, candyToAdd);
        if (shoppingCartWithQty.containsKey(candyID)) {
            int currentQty = shoppingCartWithQty.get(candyID);
            shoppingCartWithQty.put(candyID, qty + currentQty);
        } else shoppingCartWithQty.put(candyID, qty);

    }

    public void receipt() {
        StringBuilder str = new StringBuilder(String.format("%-6s%-20s%-25s%-9s%-10s\n", "Qty", "Name", "Candy Type", "Price", "Total Cost"));
        BigDecimal totalCost = new BigDecimal("0.00");
        for (Map.Entry<String, Integer> iD : shoppingCartWithQty.entrySet()) {
            int qty = iD.getValue();
            Candy candy = shoppingCartList.get(iD.getKey());
            String name = candy.getName();
            String candyType = candy.getCandyType();
            BigDecimal price = candy.getPrice();
            BigDecimal priceSum = price.multiply(new BigDecimal(qty));
            totalCost = totalCost.add(priceSum);
            str.append(String.format("%-6s%-20s%-25s%-9s%10s\n", qty, name, candyType, currency.format(price), currency.format(priceSum)));
        }
        str.append(String.format("%61s%9s", "Grand Total:", currency.format(totalCost)));
        System.out.println(str);
    }

    public Map<String, Candy> getShoppingCartList() {
        return shoppingCartList;
    }

    public Map<String, Integer> getShoppingCartWithQty() {
        return shoppingCartWithQty;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
