package com.techelevator.logs;

import com.techelevator.items.Candy;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Log {

    private static List<String> logList = new ArrayList<>();
    private final DateTimeFormatter dTFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();

    public void writeAdd(BigDecimal startBalance, BigDecimal endingBalance) { //
        final String LOG_MESSAGE = "MONEY RECEIVED";
        logList.add(String.format("%s %s %s %s",
                LocalDateTime.now().format(dTFormat),
                LOG_MESSAGE,
                currency.format(startBalance),
                currency.format(endingBalance)));
    }

    public void writeSub(Candy candy, int qty, BigDecimal startBalance, BigDecimal endingBalance) { //+7
        logList.add(String.format("%s %s %s %s %s %s",
                LocalDateTime.now().format(dTFormat),
                qty,
                candy.getName(),
                candy.getID(),
                currency.format(startBalance),
                currency.format(endingBalance)));
    }

    public void writeChange(BigDecimal change, BigDecimal balance) { //
        final String LOG_MESSAGE = "CHANGE GIVEN";
        logList.add(String.format("%s %s %s %s",
                LocalDateTime.now().format(dTFormat),
                LOG_MESSAGE,
                currency.format(change),
                currency.format(balance)));
    }

    public void pushToLog() {
        LogFileWriter logger = new LogFileWriter();
        logger.logFileWriter("log.txt");
        logger.logWriter(logList);
    }

}
