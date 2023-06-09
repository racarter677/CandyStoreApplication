package com.techelevator.customer;

import com.techelevator.logs.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

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
        StringBuilder str = new StringBuilder();
        BigDecimal change = balance.subtract(totalCost);
        BigDecimal[] denominationValues = {
                new BigDecimal("20.00"),
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                new BigDecimal("1.00"),
                new BigDecimal("0.25"),
                new BigDecimal("0.10"),
                new BigDecimal("0.05")};
        String[] denominationNames = {"Twent", "Ten", "Five", "One", "Quarter", "Dime", "Nickel"};
        BigDecimal[] changeIn20 = change.divideAndRemainder(new BigDecimal("20.00"));
        str.append(String.format("Change returned: %s\n", currency.format(change)));
        BigDecimal changeTab = change;
        for (int i = 0; i < denominationNames.length; i++) {
            BigDecimal[] quotientAndRemainder = changeTab.divideAndRemainder(denominationValues[i]);
            BigDecimal quotient = quotientAndRemainder[0];
            BigDecimal remainder = quotientAndRemainder[1];
            StringBuilder suffix = new StringBuilder();
            if (!quotient.equals(new BigDecimal("0"))) {
                if (i == 0) {
                    if (quotient.compareTo(new BigDecimal("2")) >= 0) str.append("ies");
                    else str.append("y");
                } else if (quotient.compareTo(new BigDecimal("2")) >= 0) suffix.append("s");
                //if ()
                System.out.printf("(%s) %s%s, ", quotient, denominationNames[i], suffix);
            }
        }
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
