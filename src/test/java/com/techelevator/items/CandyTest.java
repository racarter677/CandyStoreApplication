package com.techelevator.items;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class CandyTest {

    private Candy candy;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("1. getWrapper Test")
    void getWrapper_expected_Y() {
        candy = new Chocolate("C1", "Jolly Rancher", new BigDecimal("3.99"), true);
        assertEquals("Y", candy.getWrapper());
    }

    @Test
    @DisplayName("2. getWrapper Test2")
    void getWrapper_expected_N() {
        candy = new Chocolate("C1", "Jolly Rancher", new BigDecimal("3.99"), false);
        assertEquals("N", candy.getWrapper());
    }

}