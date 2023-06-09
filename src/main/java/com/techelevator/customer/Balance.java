package com.techelevator.customer;

import com.techelevator.logs.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Balance {

    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal balance;
    private final BigDecimal MAX_ADDITION =  new BigDecimal("100.00");
    private final BigDecimal MAX_BALANCE = new BigDecimal("1000.00");
    private Log log = new Log();

    public Balance() {
        balance = new BigDecimal("0.00");
    }

    public void addToBalance(BigDecimal moneyToAdd) {
        BigDecimal potentialNewBalance = balance.add(moneyToAdd);
        if (moneyToAdd.compareTo(MAX_ADDITION) <= 0) {
            if (potentialNewBalance.compareTo(MAX_BALANCE) <= 0) {
                log.writeAdd(balance, potentialNewBalance);
                balance = balance.add(moneyToAdd);
            } else {
                BigDecimal overBalance = potentialNewBalance.subtract(MAX_BALANCE);
                System.out.printf(
                        "Adding %s to your balance of %s would result in %s over the balance limit of %s.\n",
                        currency.format(moneyToAdd),
                        currency.format(balance),
                        currency.format(overBalance),
                        currency.format(MAX_BALANCE));
            }
        } else {
            System.out.printf(
                    "%s cannot be added because there is a limit of %s per transaction.\n",
                    currency.format(moneyToAdd),
                    currency.format(MAX_ADDITION));
        }
    }
    public void subtractFromBalance(BigDecimal moneyToSubtract) {
        BigDecimal potentialNewBalance = balance.subtract(moneyToSubtract);
        if (potentialNewBalance.compareTo(new BigDecimal("0.00")) > -1) {
            balance = balance.subtract(moneyToSubtract);
        } else throw new IllegalArgumentException();
    }

    public void giveChange(BigDecimal totalCost) {
        BigDecimal change = balance.subtract(totalCost);
        BigDecimal[] denominations =
                new BigDecimal[]{
                        new BigDecimal("20.00"),
                        new BigDecimal("10.00"),
                        new BigDecimal("5.00"),
                        new BigDecimal("1.00"),
                        new BigDecimal("0.25"),
                        new BigDecimal("0.10"),
                        new BigDecimal("0.05"),};
        BigDecimal[] changeIn20 = change.divideAndRemainder(new BigDecimal("20.00"));
        System.out.printf("Change returned: %s\n", currency.format(change));
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
