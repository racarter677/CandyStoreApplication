package com.techelevator.customer;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal balance;
    private final BigDecimal MAX_ADDITION =  new BigDecimal(100.00);
    private final BigDecimal MAX_BALANCE = new BigDecimal(1000.00);

    public Balance() {
        balance = new BigDecimal(0);
    }

    public void addToBalance(double moneyToAdd) {
        BigDecimal moneyAdded = new BigDecimal(moneyToAdd);
        BigDecimal potentialNewBalance = balance.add(moneyAdded);
        if (moneyAdded.compareTo(MAX_ADDITION) > -1) {
            if (potentialNewBalance.compareTo(MAX_BALANCE) > -1) {
                balance = balance.add(new BigDecimal(moneyToAdd));
            } else {
                System.out.println("Adding %s ");
            }
        }
    }

    public void subtractFromBalance(double moneyToSubtract) {
        balance = balance.add(new BigDecimal(moneyToSubtract));
    }
}
