package com.techelevator.logs;

import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.Candy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

public class SalesReport {

    private final NumberFormat percentage = NumberFormat.getPercentInstance(); //Locale.ENGLISH
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private final NumberFormat numbers = NumberFormat.getInstance();
    private static Map<Candy, Integer> candySaleTotals = new HashMap<>();
    private static BigDecimal fullTotalCost = BigDecimal.ZERO;
    private static int fullTotalQty = 0;
    File report = new File("TotalSales.rpt");

    private static InventoryFileReader readReport = new InventoryFileReader("TotalSales.rpt");

    public SalesReport() {
        if (report.exists()) {
            try {
                candySaleTotals = readReport.pullExistingSalesReport();
                fullTotalQty = readReport.getPreviousTotalCandy();
                fullTotalCost = readReport.getPreviousTotalSales();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addSale(Map<Candy, Integer> candyShoppingCart, int totalQty, BigDecimal totalCost) {
        List<String> salesReportList = new ArrayList<>();
        fullTotalCost = fullTotalCost.add(totalCost);
        fullTotalQty += totalQty;
        percentage.setMinimumFractionDigits(2);
        percentage.setMaximumFractionDigits(2);
        for (Map.Entry<Candy, Integer> candy : candyShoppingCart.entrySet()) {
            int qty = candy.getValue();
            if (candySaleTotals.containsKey(candy.getKey())) {
                qty += candySaleTotals.get(candy.getKey());
                candySaleTotals.put(candy.getKey(), qty);
            } else candySaleTotals.put(candy.getKey(), qty);
        }
        for (Map.Entry<Candy, Integer> candy : candySaleTotals.entrySet()) {
            String ID = candy.getKey().getID();
            String name = candy.getKey().getName();
            int qty = candy.getValue();
            BigDecimal cost = candy.getKey().getPrice().multiply(new BigDecimal(qty));
            String qtyPercentage = percentage.format(new BigDecimal(qty).divide(new BigDecimal(fullTotalQty), 4, RoundingMode.HALF_UP));
            String costPercentage = percentage.format(cost.divide(fullTotalCost, 4, RoundingMode.HALF_UP));
            salesReportList.add(String.format("%-3s| %-20s|%5s |%10s |%8s |%8s\n", ID, name, qty, currency.format(cost), qtyPercentage, costPercentage));
        }
        salesReportList.sort(null);
        salesReportList.add(0, "***************************************************************\n");
        salesReportList.add(1, "******************* Candy Store Total Sales *******************\n");
        salesReportList.add(2, "***************************************************************\n");
        salesReportList.add(3, String.format("%-3s| %-20s|%5s |%10s |%8s |%8s\n", "ID", "Candy", "Qty", "Cost", "QTY %", "Cost %"));
        salesReportList.add(String.format("\nTotal Number of Candy Sold: %s\n", numbers.format(fullTotalQty)));
        salesReportList.add(String.format("Total Sales: %s", currency.format(fullTotalCost)));
        LogFileWriter makeReport = new LogFileWriter();
        makeReport.writeReport("TotalSales.rpt", salesReportList);
//        writeReport(salesReportList);
    }

//    public void writeReport(List<String> list) {
//        File reportFile = new File("TotalSales.rpt");
//        try (PrintWriter writer = new PrintWriter(reportFile)){
//            for (String str : list) {
//                writer.print(str);
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
