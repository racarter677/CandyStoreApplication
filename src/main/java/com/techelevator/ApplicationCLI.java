package com.techelevator;

import com.techelevator.customer.Balance;
import com.techelevator.customer.CandyShoppingCart;
import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.Candy;
import com.techelevator.items.Inventory;
import com.techelevator.logs.Log;
import com.techelevator.logs.SalesReport;
import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

public class ApplicationCLI {

	private Menu menu = new Menu();
	private Inventory inventory;
	private Balance balance;
	private CandyShoppingCart candyShoppingCart;
	private Log log = new Log();

	// probably should leave this method alone... and go do stuff in the run method....
	public static void main(String[] args) {
		ApplicationCLI cli = new ApplicationCLI();
		cli.run();
	}

	/**
	 * This is the main method that controls the flow of the program.. Probably could look at the review code for ideas of what to put here...
	 */
	public void run() {
		try {
			inventory = new Inventory(new InventoryFileReader("inventory.csv").getInventory());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		balance = new Balance();
		menu.printGreeting();
		while(true) {
			candyShoppingCart = new CandyShoppingCart();
			String userInput = "";
			int userQty = 0;
			userInput = menu.getMainMenu();
			if(userInput.equals("1")) {
				menu.displayInventory(inventory);
			}
			else if(userInput.equals("2")) {
				while(true) {
					userInput = menu.displayMakeSale(balance);
					if(userInput.equals("1")) {
						menu.displayMessage("Welcome to Take Money");
						while (true) {
							menu.displayBalance(balance);
							String balanceToAdd = menu.showTakeMoney();
							if (balanceToAdd.equals("")) {
								break;
							}
							try {
								BigDecimal addingBalance = new BigDecimal(balanceToAdd);
								balance.addToBalance(addingBalance);
							}
							catch (NumberFormatException e) {
								menu.displayMessage("Please enter a valid input");
							}
						}
					} else if((userInput.equals("2") || userInput.equals("3")) && candyShoppingCart.getCandyShoppingCart().size() == 0 && balance.getBalance().equals(new BigDecimal("0.00"))) {
						menu.displayMessage("Please add money to balance first.");
					}
					else if (userInput.equals("2")) {
						menu.displayInventory(inventory);
						userInput = menu.selectProducts();
						if (!inventory.getInventoryMap().containsKey(userInput)) {
							menu.displayMessage("Invalid ID");
						}
						else {
							try {
								userQty = menu.qtyOfProduct();
							}
							catch (NumberFormatException e) {
								menu.displayMessage("Invalid Input");
							}
							if (userQty <= inventory.getCandyQuantity(userInput)) {
								BigDecimal startBalance = balance.getBalance();
								try {
									Candy candySelected = inventory.getInventoryMap().get(userInput);
									balance.subtractFromBalance(inventory
											.getInventoryMap()
											.get(userInput.toUpperCase())
											.getPrice()
											.multiply(BigDecimal.valueOf(userQty)));
									log.writeSub(candySelected, userQty, startBalance, balance.getBalance());
									inventory.removeFromInventory(userInput, userQty);
									candyShoppingCart.addCandyToShoppingCart(candySelected, userQty);
								}
								catch (IllegalArgumentException e) {
									menu.displayMessage("Not enough money");
								}
							} else {
								menu.displayMessage("Not enough stock for quantity selected");
							}
						}
					}
					else if (userInput.equals("3")) {
						menu.displayMessage(candyShoppingCart.receipt());
						menu.displayMessage(balance.giveChange());
						log.pushToLog();
						log.resetLog();
						candyShoppingCart = new CandyShoppingCart();
						break;
					}
					else {
						menu.displayMessage("Please enter a valid input (1, 2, 3)");
					}
				}
			}
			else if(userInput.equals("3")) {
				menu.displayExit();
				SalesReport salesRpt = new SalesReport(candyShoppingCart.getFullTotalCount(), candyShoppingCart.getFullTotalCost());
				break;
			}
			else {
				menu.displayMessage("Please enter a valid input (1, 2, 3)");
			}


		}

		// Good place to create references for UserInterface, Inventory class, and Register class.... (There should NEVER be more than one instance of these)


		//probably a great place to create a loop that manages the main menu and delegates all work to the other classes....
		// Hint: for the submenu, maybe a loop inside this main loop? If you break out of the sub-loop(sub-menu), you would reach the
		//outer loop....

	}

	//feel free to create private methods here if you are feeling up to it, so run() doesn't get so long...


}
