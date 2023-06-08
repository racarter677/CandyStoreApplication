package com.techelevator;

import com.techelevator.customer.Balance;
import com.techelevator.customer.CandyShoppingCart;
import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.Candy;
import com.techelevator.items.Inventory;
import com.techelevator.view.Menu;

import java.io.FileNotFoundException;

public class ApplicationCLI {

	private Menu menu = new Menu();
	private Inventory inventory;
	private Balance balance;
	private CandyShoppingCart candyShoppingCart;

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
		candyShoppingCart = new CandyShoppingCart();
		balance = new Balance();

		while(true) {
			String userInput = "";
			userInput = menu.getMainMenu();
			if(userInput.equals("1")) {
				menu.displayInventory(inventory);
			}
			else if(userInput.equals("2")) {
				while(true) {
					userInput = menu.displayMakeSale();
					if(userInput.equals("1")) {
						balance.addToBalance(menu.showTakeMoney());
					}
					else if (userInput.equals("2")) {
						int userQty = 0;
						menu.displayInventory(inventory);
						userInput = menu.selectProducts();
						if (!inventory.getInventoryMap().containsKey(userInput)) {
							menu.displayMessage("Invalid ID");
						}
						else {
							userQty = menu.qtyOfProduct();
							if (userQty <= inventory.getCandyQuantity(userInput)) {
								inventory.removeFromInventory(userInput, userQty);
								candyShoppingCart.addCandyToShoppingCart(inventory.getInventoryMap().get(userInput));
								System.out.println(candyShoppingCart.getShoppingCartList());
							}
						}
					}
				}
			}
			else if(userInput.equals("3")) {
				menu.displayExit();
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
