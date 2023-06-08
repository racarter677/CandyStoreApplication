package com.techelevator.customer;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class BalanceTest {

    private Balance balance;

    @BeforeEach
    void setUp() {
        balance = new Balance();
    }

    @Test
    @DisplayName("1. Adding money to zero balance increases the balance")
    void add_money_to_zero_returns_correct_balance() {
        balance.setBalance(new BigDecimal("0.00"));
        balance.addToBalance(new BigDecimal("32.32"));
        assertEquals(new BigDecimal("32.32"), balance.getBalance());
    }

    @Test
    @DisplayName("2. Adding money to positive balance increases the balance")
    void add_money_returns_correct_balance() {
        balance.setBalance(new BigDecimal("32.32"));
        balance.addToBalance(new BigDecimal("32.32"));
        assertEquals(new BigDecimal("64.64"), balance.getBalance());
    }

    @Test
    @DisplayName("3. Adding more than $100 should return the existing balance")
    void adding_over_100_return_existing_balance() {
        balance.setBalance(new BigDecimal("0.00"));
        balance.addToBalance(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("0.00"), balance.getBalance());
        balance.addToBalance(new BigDecimal("100.01"));
        assertEquals(new BigDecimal("0.00"), balance.getBalance());
    }

    @Test
    @DisplayName("4. Adding money that takes balance over $1000 should return existing balance")
    void adding_to_total_over_1000_return_existing_balance() {
        balance.setBalance(new BigDecimal("950.00"));
        balance.addToBalance(new BigDecimal("60.00"));
        assertEquals(new BigDecimal("950.00"), balance.getBalance());
        balance.addToBalance(new BigDecimal("50.01"));
        assertEquals(new BigDecimal("950.00"), balance.getBalance());
    }

    @Test
    @DisplayName("5. Subtracting money from balance returns correct balance")
    void subtracting_from_total_returns_correct_balance() {
        balance.setBalance(new BigDecimal("100.00"));
        balance.subtractFromBalance(new BigDecimal("60.00"));
        assertEquals(new BigDecimal("40.00"), balance.getBalance());
        balance.subtractFromBalance(new BigDecimal("39.99"));
        assertEquals(new BigDecimal("0.01"), balance.getBalance());
        balance.subtractFromBalance(new BigDecimal("0.01"));
        assertEquals(new BigDecimal("0.00"), balance.getBalance());
    }

    @Test
    @DisplayName("6. Subtracting money from balance throws exception and returns existing balance")
    void subtracting_too_much() {
        balance.setBalance(new BigDecimal("100.00"));
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            balance.subtractFromBalance(new BigDecimal("200.00"));
        });
        assertEquals(new BigDecimal("100.00"), balance.getBalance());
    }

}