package com.techelevator.logs;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Log {

    List<String> logList = new ArrayList<>();
    private DateTimeFormatter dTFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM); // use MEDIUM LocalDateTime or FULL if using Zoned

    public void write(BigDecimal startBalance, BigDecimal endingBalance) {
        BigDecimal moneyReceived = endingBalance.subtract(startBalance);
    }

}
