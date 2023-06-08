package com.techelevator.filereader;

import com.techelevator.items.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
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

    public InventoryFileReader(String inventoryFilePath){
        this.filePath = inventoryFilePath;
    }
    public Map<String, Candy> getInventory() throws FileNotFoundException {
        Map<String,Candy> inventory = new HashMap<>();

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

//    public static void main(String[] args) {
//        InventoryFileReader inv = new InventoryFileReader("inventory.csv");
//        try {
//            Map<String, Candy> candyMap = new TreeMap<>(inv.getInventory());
//            for (Map.Entry<String, Candy> candy : candyMap.entrySet()) {
//                System.out.println(candy);
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }



}
