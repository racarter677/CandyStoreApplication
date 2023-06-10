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
    @DisplayName("3. Adding $100 increases the balance")
    void add_100_returns_correct_balance() {
        balance.setBalance(new BigDecimal("32.32"));
        balance.addToBalance(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("132.32"), balance.getBalance());
    }

    @Disabled
    @Test
    @DisplayName("4. Adding more than $100 should return the existing balance")
    void adding_over_100_return_existing_balance() {
        balance.setBalance(new BigDecimal("0.00"));
        balance.addToBalance(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("0.00"), balance.getBalance());
        balance.addToBalance(new BigDecimal("100.01"));
        assertEquals(new BigDecimal("0.00"), balance.getBalance());
    }

    @Test
    @DisplayName("5. Adding money that takes balance over $1000 should return existing balance")
    void adding_to_total_over_1000_return_existing_balance() {
        balance.setBalance(new BigDecimal("950.00"));
        balance.addToBalance(new BigDecimal("60.00"));
        assertEquals(new BigDecimal("950.00"), balance.getBalance());
        balance.addToBalance(new BigDecimal("50.01"));
        assertEquals(new BigDecimal("950.00"), balance.getBalance());
    }

    @Test
    @DisplayName("6. Subtracting money from balance returns correct balance")
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
    @DisplayName("7. Subtracting money from balance throws exception and returns existing balance")
    void subtracting_too_much() {
        balance.setBalance(new BigDecimal("100.00"));
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            balance.subtractFromBalance(new BigDecimal("200.00"));
        });
        assertEquals(new BigDecimal("100.00"), balance.getBalance());
    }

    @Test
    @DisplayName("8. Change denominations are correct")
    void change_denomination_check() {
        balance.setBalance(new BigDecimal("77.80"));
        //System.out.println(balance.giveChange());
        assertEquals("Change returned: $77.80\n" +
                "(3) Twenties, (1) Ten, (1) Five, (2) Ones, (3) Quarters, (1) Nickel\n", balance.giveChange());
        balance.setBalance(new BigDecimal("50.20"));
        assertEquals("Change returned: $50.20\n" +
                "(2) Twenties, (1) Ten, (2) Dimes\n", balance.giveChange());
        balance.setBalance(new BigDecimal("32.30"));
        assertEquals("Change returned: $32.30\n" +
                "(1) Twenty, (1) Ten, (2) Ones, (1) Quarter, (1) Nickel\n", balance.giveChange());
        balance.setBalance(new BigDecimal("14.05"));
        assertEquals("Change returned: $14.05\n" +
                "(1) Ten, (4) Ones, (1) Nickel\n", balance.giveChange());
    }

}