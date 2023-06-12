package com.techelevator.logs;

import com.techelevator.items.*;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class LogTest {

    private Log log;

    @BeforeEach
    void setUp() {
        log = new Log();
        log.resetLog();
    }

    @Test
    @DisplayName("1. Adding to the balance returns the correct log")
    void write_add_verification() {
        log.writeAdd(new BigDecimal("0.00"), new BigDecimal("100.00"));
        assertEquals("MONEY RECEIVED $0.00 $100.00", log.getLogList().get(0).substring(23));
        log.writeAdd(new BigDecimal("65.56"), new BigDecimal("115.56"));
        assertEquals("MONEY RECEIVED $65.56 $115.56", log.getLogList().get(1).substring(23));
    }

    @Test
    @DisplayName("2. Selling item returns correct log")
    void write_sub_verification() {
        log.writeSub(new Chocolate("C1", "Snuckers Bar", new BigDecimal("1.35"), true),
                20, new BigDecimal("100.00"), new BigDecimal("73.00"));
        assertEquals("20 Snuckers Bar C1 $100.00 $73.00", log.getLogList().get(0).substring(23));
    }

    @Test
    @DisplayName("3. Giving change returns correct log")
    void write_change_verification() {
        log.writeChange(new BigDecimal("23.45"), new BigDecimal("0.00"));
        assertEquals("CHANGE GIVEN $23.45 $0.00", log.getLogList().get(0).substring(23));
    }
}