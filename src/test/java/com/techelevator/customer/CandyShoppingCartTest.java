package com.techelevator.customer;

import com.techelevator.items.*;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CandyShoppingCartTest {

    private CandyShoppingCart candyShoppingCart;

    @BeforeEach
    void setup() {

        candyShoppingCart = new CandyShoppingCart();

        candyShoppingCart.getShoppingCartList().add(new Chocolate("C1", "Hershey", new BigDecimal("2.00"), true));
        candyShoppingCart.getShoppingCartList().add(new HardCandy("H1", "Jolly Rancher", new BigDecimal("3.50"), false));
        candyShoppingCart.getShoppingCartList().add(new Sours("S1", "Sour Gummy", new BigDecimal("4.50"), true));
        candyShoppingCart.getShoppingCartList().add(new Licorice("L2", "Black Licorice", new BigDecimal("2.25"), false));

    }

    @Test
    @DisplayName("1. Total Cost of Shopping Cart Test")
    void total_cost_of_cart_test(){
        assertEquals(new BigDecimal(12.25), candyShoppingCart.calculateTotalCostOfShoppingCart());
    }



}