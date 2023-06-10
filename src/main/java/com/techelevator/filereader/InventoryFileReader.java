package com.techelevator.filereader;

import com.techelevator.items.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class would be a GREAT place to read in a file and return a data structure matching the one in
 * your Inventory class. (You probably saw something similiar in some review code we did....)
 */
public class InventoryFileReader {

    private final String filePath;
    private static Map<String,Candy> inventory = new HashMap<>();
    private int previousTotalCandy;
    private BigDecimal previousTotalSales;

    public InventoryFileReader(String inventoryFilePath){
        this.filePath = inventoryFilePath;
    }
    public Map<String, Candy> getInventory() throws FileNotFoundException {
//        Map<String,Candy> inventory = new HashMap<>();

        //build File object to rep abstract File in system
        File inventoryCSV = new File(filePath);
        //need to build a scanner that uses inventoryCSV as an input
        //also need to manage that scanner, so I will use a try-with-resource
        try(Scanner inventoryScanner = new Scanner(inventoryCSV)){
            while(inventoryScanner.hasNextLine()){
                String currentLineFromInventoryScanner = inventoryScanner.nextLine();
                //build an array from the current string splitting on |
                String[] currentLineToArray = currentLineFromInventoryScanner.split("\\|");
                Candy currentCandy = null;
                String typeOfCandy = currentLineToArray[0];
                String candyID = currentLineToArray[1];
                String name = currentLineToArray[2];
                BigDecimal price = new BigDecimal(currentLineToArray[3]);
                boolean isWrapped = currentLineToArray[4].equalsIgnoreCase("t");
                if(typeOfCandy.equalsIgnoreCase("ch")){
                    currentCandy = new Chocolate(candyID, name, price, isWrapped);
                } else if(typeOfCandy.equalsIgnoreCase("sr")){
                    currentCandy = new Sours(candyID, name, price, isWrapped);
                } else if(typeOfCandy.equalsIgnoreCase("hc")){
                    currentCandy = new HardCandy(candyID, name, price, isWrapped);
                }else if(typeOfCandy.equalsIgnoreCase("li")){
                    currentCandy = new Licorice(candyID, name, price, isWrapped);
                }

                inventory.put(candyID,currentCandy);
            }
        }

        return inventory;
    }

    public Map<Candy, Integer> pullExistingSalesReport() throws FileNotFoundException {
        Map<Candy, Integer> candySaleTotals = new HashMap<>();
        File salesReport= new File(filePath);
        try(Scanner inventoryScanner = new Scanner(salesReport)){
            while(inventoryScanner.hasNextLine()){
                String currentLine = inventoryScanner.nextLine();
                Candy currentCandy = null;
                int qty = 0;
                if (currentLine.startsWith("*") || currentLine.startsWith("I")) continue;
                else if (currentLine.equals("")) continue;
                else if (currentLine.startsWith("Total Number")) {
                    previousTotalCandy = Integer.parseInt(currentLine.split(":")[1].trim());
                } else if (currentLine.startsWith("Total Sales")) {
                    previousTotalSales = new BigDecimal(currentLine.split(":")[1].trim().substring(1));
                } else {
                    //build an array from the current string splitting on |
                    String[] currentLineToArray = currentLine.split("\\|");
                    String candyID = currentLineToArray[0].trim();
                    String name = currentLineToArray[1].trim();
                    qty = Integer.parseInt(currentLineToArray[2].trim());
                    BigDecimal price = new BigDecimal(currentLineToArray[3].trim().substring(1))
                            .divide(new BigDecimal(qty), 2, RoundingMode.HALF_UP);
                    boolean isWrapped = true;
                    if (inventory.containsKey(candyID)) currentCandy = inventory.get(candyID);
                    else if (candyID.startsWith("C")) {
                        currentCandy = new Chocolate(candyID, name, price, isWrapped);
                    } else if (candyID.startsWith("S")) {
                        currentCandy = new Sours(candyID, name, price, isWrapped);
                    } else if (candyID.startsWith("H")) {
                        currentCandy = new HardCandy(candyID, name, price, isWrapped);
                    } else if (candyID.startsWith("L")) {
                        currentCandy = new Licorice(candyID, name, price, isWrapped);
                    }
                    candySaleTotals.put(currentCandy,qty);
                }
            }
        }
        return candySaleTotals;
    }

    public int getPreviousTotalCandy() {
        return previousTotalCandy;
    }

    public BigDecimal getPreviousTotalSales() {
        return previousTotalSales;
    }
}
