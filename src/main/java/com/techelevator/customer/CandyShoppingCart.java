package com.techelevator.customer;

import com.techelevator.items.Candy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CandyShoppingCart {

    private  List <Candy> shoppingCartList = new ArrayList<>();

    public void addCandyToShoppingCart(Candy candyToAdd) {
        shoppingCartList.add(candyToAdd);
    }

    public BigDecimal calculateTotalCostOfShoppingCart() {
        BigDecimal totalCost = new BigDecimal("0.00");
        for (Candy currentItem : shoppingCartList) {
            totalCost = totalCost.add(currentItem.getPrice());
        }
        return totalCost;
    }

    public List<Candy> getShoppingCartList() {
        return this.shoppingCartList;
    }
}
