package com.techelevator.customer;

import com.techelevator.logs.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Balance {

    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private BigDecimal balance;
    private final BigDecimal MAX_ADDITION =  new BigDecimal("500.00"); // Change back to 100 before submitting
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

    public String giveChange(BigDecimal totalCost) {
        // Create new StringBuilder to add all strings
        StringBuilder str = new StringBuilder();
        // The change is just the current balance
        BigDecimal change = balance;
        // Create array containing denomination values
        BigDecimal[] denominationValues = {
                new BigDecimal("20.00"),
                new BigDecimal("10.00"),
                new BigDecimal("5.00"),
                new BigDecimal("1.00"),
                new BigDecimal("0.25"),
                new BigDecimal("0.10"),
                new BigDecimal("0.05")};
        // Create array containing denomination names (without their pluralized form)
        String[] denominationNames = {"Twent", "Ten", "Five", "One", "Quarter", "Dime", "Nickel"};
        // Start the String off with the Change amount
        str.append(String.format("Change returned: %s\n", currency.format(change)));
        // Create a variable to cycle through the remaining change
        BigDecimal changeTab = change;
        // Create a list that holds the denomination name and quantity
        List<String> changeList = new ArrayList<>();
        // Loop through all items in the denominations
        for (int i = 0; i < denominationNames.length; i++) {
            // Create an array that holds the quotient and remainder of the change
            BigDecimal[] quotientAndRemainder = changeTab.divideAndRemainder(denominationValues[i]);
            BigDecimal quotient = quotientAndRemainder[0];
            BigDecimal remainder = quotientAndRemainder[1];
            // Create new StringBuilder that holds the suffix
            StringBuilder suffix = new StringBuilder();
            // If the quotient does not equal 0
            if (!quotient.equals(new BigDecimal("0"))) {
                if (i == 0) {
                    if (quotient.compareTo(new BigDecimal("2")) >= 0) suffix.append("ies");
                    else suffix.append("y");
                } else if (quotient.compareTo(new BigDecimal("2")) >= 0) suffix.append("s");
                // Add denomination to String list
                changeList.add(String.format("(%s) %s%s", quotient, denominationNames[i], suffix));
            }
            // Update the changeTab to what's left after taking that denomination out
            changeTab = remainder;
        }
        // Loop through the list of denominations and add them to the final string
        for (int i = 0; i < changeList.size(); i++) {
            str.append(changeList.get(i));
            // Check to see if it's the last item in the list
            if (i != changeList.size() - 1) str.append(", ");
            else str.append("\n");
        }
        balance = new BigDecimal("0");
        log.writeChange(change, balance);
        return str.toString();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
