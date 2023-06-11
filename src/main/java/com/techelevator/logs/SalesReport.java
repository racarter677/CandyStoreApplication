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
    private static Map<String, Candy> candySaleTotals = new HashMap<>();
    private static BigDecimal fullTotalCost;
    private static int fullTotalQty;

    private static InventoryFileReader readReport = new InventoryFileReader("TotalSales.rpt");

    public SalesReport(int totalQty, BigDecimal totalCost) {
        candySaleTotals = readReport.pullExistingSalesReport();
        fullTotalQty = readReport.getPreviousTotalCandy() + totalQty;
        fullTotalCost = readReport.getPreviousTotalSales().add(totalCost);
        addSale();
    }

    public void addSale() {
        List<String> salesReportList = new ArrayList<>();
        percentage.setMinimumFractionDigits(2);
        percentage.setMaximumFractionDigits(2);
        for (Map.Entry<String, Candy> candyEntry : candySaleTotals.entrySet()) {
            Candy candy = candyEntry.getValue();
            String ID = candy.getID();
            String name = candy.getName();
            int qty = candy.getTotalSold();
            if (qty > 0) {
                BigDecimal cost = candy.getTotalRevenue();
                String qtyPercentage = percentage.format(new BigDecimal(qty).divide(new BigDecimal(fullTotalQty), 4, RoundingMode.HALF_UP));
                String costPercentage = percentage.format(cost.divide(fullTotalCost, 4, RoundingMode.HALF_UP));
                salesReportList.add(String.format("%-3s| %-20s|%5s |%10s |%8s |%8s\n", ID, name, qty, currency.format(cost), qtyPercentage, costPercentage));
            }
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

    }

}
