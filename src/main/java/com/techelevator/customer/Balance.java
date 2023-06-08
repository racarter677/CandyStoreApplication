package com.techelevator.customer;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Balance {

    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal balance;
    private final BigDecimal MAX_ADDITION =  new BigDecimal("100.00");
    private final BigDecimal MAX_BALANCE = new BigDecimal("1000.00");

    public Balance() {
        balance = new BigDecimal("0.00");
    }

    public void addToBalance(BigDecimal moneyToAdd) {
        BigDecimal potentialNewBalance = balance.add(moneyToAdd);
        if (moneyToAdd.compareTo(MAX_ADDITION) <= 0) {
            if (potentialNewBalance.compareTo(MAX_BALANCE) <= 0) {
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

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
