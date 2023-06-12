package com.techelevator.customer;

import com.techelevator.items.*;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CandyShoppingCartTest {

    private CandyShoppingCart candyShoppingCart;
    Candy hershey;
    Candy jollyRancher;
    Candy blackLicorice;
    Candy patchKids;

    @BeforeEach
    void setup() {
        candyShoppingCart = new CandyShoppingCart();
        hershey = new Chocolate("C1", "Hershey", new BigDecimal("2.50"), false);
        jollyRancher = new HardCandy("H1", "Jolly Rancher", new BigDecimal("4.75"), true);
        blackLicorice = new Licorice("L1", "Black Licorice", new BigDecimal("5.25"), false);
        patchKids = new Sours("S1", "Patch Kids", new BigDecimal("1.50"), false);
        candyShoppingCart.addCandyToShoppingCart(hershey, 10);
        candyShoppingCart.addCandyToShoppingCart(jollyRancher, 10);
        candyShoppingCart.addCandyToShoppingCart(blackLicorice, 10);
        candyShoppingCart.addCandyToShoppingCart(patchKids, 10);
    }

    @Test
    @DisplayName("1. Total Cost of Shopping Cart Test")
    void total_cost_of_cart_test(){
        candyShoppingCart.receipt();
        assertEquals(0, new BigDecimal("140").compareTo(candyShoppingCart.getTotalCost()));
    }

    @Test
    @DisplayName("2. Buying the same candy twice adds qty correctly")
    void buying_same_candy_twice() {
        candyShoppingCart.addCandyToShoppingCart(hershey, 10);
        assertEquals(20, candyShoppingCart.getCandyShoppingCart().get(hershey));
        candyShoppingCart.receipt();
    }



}