package com.techelevator.logs;

import com.techelevator.items.Candy;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Log {

    private static List<String> logList = new ArrayList<>();
    private DateTimeFormatter dTFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT); // use MEDIUM LocalDateTime or FULL if using Zoned
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();

    public void writeAdd(BigDecimal startBalance, BigDecimal endingBalance) {
        final String MONEY_RECEIVED = "MONEY RECEIVED";
        logList.add(String.format("%-24s%-17s%-9s%-9s",
                LocalDateTime.now().format(dTFormat),
                MONEY_RECEIVED,
                currency.format(startBalance),
                currency.format(endingBalance)));
    }

    public void writeSub(Candy candy, int qty, BigDecimal startBalance, BigDecimal endingBalance) {
        logList.add(String.format("%-24s%-4s%-15s%-3s%-9s%-9s",
                LocalDateTime.now().format(dTFormat),
                qty,
                candy.getName(),
                candy.getID(),
                currency.format(startBalance),
                currency.format(endingBalance)));
    }

}
