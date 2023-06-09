package com.techelevator.view;

import com.techelevator.customer.Balance;
import com.techelevator.items.Candy;
import com.techelevator.items.Inventory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Menu {

    private Scanner userInput = new Scanner(System.in);
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();

    public void printGreeting(){
        System.out.println("Welcome");
    }

    public String getMainMenu() {
        System.out.println("What would you like to do?");
        System.out.println();
        System.out.println("(1) Show Inventory");
        System.out.println("(2) Make Sale");
        System.out.println("(3) Quit");
        System.out.println();
        System.out.println("Please make a selection: ");

        return userInput.nextLine();
    }

    public void displayInventory(Inventory inv) {
        System.out.println(inv);
    }

    public String displayMakeSale(Balance balance) {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("(1) Take Money");
        System.out.println("(2) Select Products");
        System.out.println("(3) Complete Sale");
        System.out.println();
        displayBalance(balance);
        System.out.println("Please make a selection: ");
        return userInput.nextLine();
    }

    public BigDecimal showTakeMoney() {
        System.out.println();
        System.out.println("Please enter the amount you would like to add to Customer Balance");
        System.out.println("(Amount must be in whole dollars and no single deposit may exceed $100. MAX balance is  $1000");
        System.out.println("(If you would like to exit Take Money enter 0)");
        System.out.println("Enter amount here: ");
        String amountToAddString = userInput.nextLine();
        return new BigDecimal(amountToAddString);
    }

    public String selectProducts() {
        System.out.println();
        System.out.println("Enter ID of desired product: ");
        return userInput.nextLine();
    }

    public int qtyOfProduct() {
        System.out.println();
        System.out.println("Enter amount of desired product you would like to purchase: ");
        String amountOfProductString = userInput.nextLine();
        return Integer.parseInt(amountOfProductString);
    }

    public void displayBalance(Balance balance) {
        System.out.println("Current customer balance: " + balance.getBalance());
    }

    public void displayMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    public void displayExit() {
        System.out.println();
        System.out.println("Thank You for shopping!");
    }

}
