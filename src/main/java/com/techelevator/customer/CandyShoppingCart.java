package com.techelevator.customer;

import com.techelevator.items.Candy;
import com.techelevator.logs.SalesReport;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class CandyShoppingCart {

    private Map<Candy, Integer> candyShoppingCart = new LinkedHashMap<>();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal totalCost = BigDecimal.ZERO;
    private static BigDecimal fullTotalCost = new BigDecimal("0.00");
    private static int fullTotalCount;

    public void addCandyToShoppingCart(Candy candyToAdd, int qty) {
        if (candyShoppingCart.containsKey(candyToAdd)) {
            int currentQty = candyShoppingCart.get(candyToAdd);
            candyShoppingCart.put(candyToAdd, qty + currentQty);
        } else candyShoppingCart.put(candyToAdd, qty);

    }

    // Change a return of String instead of printing out receipt later, then pass receipt through menu messaging in main method
    public String receipt() {
        StringBuilder str = new StringBuilder(String.format("%-6s%-20s%-25s%-9s%-10s\n", "Qty", "Name", "Candy Type", "Price", "Total Cost"));
        for (Map.Entry<Candy, Integer> candyEntry : candyShoppingCart.entrySet()) {
            int qty = candyEntry.getValue();
            Candy candy = candyEntry.getKey();
            String name = candy.getName();
            String candyType = candy.getCandyType();
            BigDecimal price = candy.getPrice();
            BigDecimal priceSum = price.multiply(new BigDecimal(qty));
            totalCost = totalCost.add(priceSum);
            str.append(String.format("%-6s%-20s%-25s%-9s%10s\n", qty, name, candyType, currency.format(price), currency.format(priceSum)));
            fullTotalCount += qty;
        }
        fullTotalCost = fullTotalCost.add(totalCost);
        str.append(String.format("%61s%9s", "Grand Total:", currency.format(totalCost)));
        return str.toString();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public Map<Candy, Integer> getCandyShoppingCart() {
        return candyShoppingCart;
    }

    public BigDecimal getFullTotalCost() {
        return fullTotalCost;
    }

    public int getFullTotalCount() {
        return fullTotalCount;
    }
}
