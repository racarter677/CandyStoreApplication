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
    private static int previousTotalCandy = 0;
    private static BigDecimal previousTotalSales = new BigDecimal("0.00");

    public InventoryFileReader(String inventoryFilePath) {
        this.filePath = inventoryFilePath;
    }
    public Map<String, Candy> getInventory() throws FileNotFoundException {

        File inventoryCSV = new File(filePath);
        //need to build a scanner that uses inventoryCSV as an input
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
                    currentCandy = new Chocolate(candyID, name, price, isWrapped, true);
                } else if(typeOfCandy.equalsIgnoreCase("sr")){
                    currentCandy = new Sours(candyID, name, price, isWrapped, true);
                } else if(typeOfCandy.equalsIgnoreCase("hc")){
                    currentCandy = new HardCandy(candyID, name, price, isWrapped, true);
                }else if(typeOfCandy.equalsIgnoreCase("li")){
                    currentCandy = new Licorice(candyID, name, price, isWrapped, true);
                }

                inventory.put(candyID,currentCandy);
            }
        }
        return inventory;
    }

    public Map<String, Candy> pullExistingSalesReport() {
        File salesReport= new File("TotalSales.rpt");
//        Map<String, Candy> candySaleTotals = new HashMap<>(inventory);
        try (Scanner inventoryScanner = new Scanner(salesReport)) {
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                Candy currentCandy = null;
                int qty;
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
                    BigDecimal totalRevenue = new BigDecimal(currentLineToArray[3].trim().substring(1));
                    BigDecimal price = totalRevenue.divide(new BigDecimal(qty), 2, RoundingMode.HALF_UP);
                    boolean isWrapped = true;
                    String iDSubstring = candyID.substring(0, 2);
                    boolean isAlreadyPresent = inventory.containsKey(iDSubstring);
                    boolean namesMatch = false;
                    if(isAlreadyPresent) {
                        namesMatch = inventory.get(iDSubstring).getName().equals(name);
                    }
                    if (isAlreadyPresent && namesMatch) {
                        currentCandy = inventory.get(iDSubstring);
                        currentCandy.setTotalSold(qty);
                        currentCandy.setTotalRevenue(totalRevenue);
                    } else if (isAlreadyPresent) {
                        inventory.get(iDSubstring).increaseInstancesOfID();
                        candyID = iDSubstring + (char) ('a' + inventory.get(iDSubstring).getInstancesOfID());
                    }
                    if (!isAlreadyPresent || !namesMatch) {
                        if (candyID.startsWith("C")) {
                            currentCandy = new Chocolate(candyID, name, price, isWrapped, false);
                        } else if (candyID.startsWith("S")) {
                            currentCandy = new Sours(candyID, name, price, isWrapped, false);
                        } else if (candyID.startsWith("H")) {
                            currentCandy = new HardCandy(candyID, name, price, isWrapped, false);
                        } else if (candyID.startsWith("L")) {
                            currentCandy = new Licorice(candyID, name, price, isWrapped, false);
                        }
                        if (currentCandy != null) {
                            currentCandy.setTotalSold(qty);
                            currentCandy.setTotalRevenue(totalRevenue);
                            inventory.put(candyID, currentCandy);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
        }
        return inventory;
    }

    public int getPreviousTotalCandy() {
        return previousTotalCandy;
    }

    public BigDecimal getPreviousTotalSales() {
        return previousTotalSales;
    }
}
