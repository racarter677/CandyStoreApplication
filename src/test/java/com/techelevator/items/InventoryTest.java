package com.techelevator.items;

import com.techelevator.customer.Balance;
import com.techelevator.filereader.InventoryFileReader;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class InventoryTest {

    private Inventory inventory;
    private static Map<String, Candy> candyInventory = new HashMap<>();

    @BeforeAll
    static void buildInventory() {
        InventoryFileReader buildInventory = new InventoryFileReader("inventory.csv");
        try {
            candyInventory = buildInventory.getInventory();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        inventory = new Inventory(candyInventory);
    }

    @Test
    @DisplayName("1. Verify all candy in inventory set to 100 at start")
    void inventory_at_start_should_be_100() {
        for (Map.Entry<String, Integer> candy : inventory.getAllInventoryQuantities().entrySet()) {
            assertEquals(100, candy.getValue());
        }
    }

    @Test
    @DisplayName("2. Removing inventory should update total inventory")
    void removing_inventory_updates_total() {
        inventory.removeFromInventory("H3", 25);
        assertEquals(100-25, inventory.getCandyQuantity("H3"));
        inventory.removeFromInventory("H3", 75);
        assertEquals(0, inventory.getCandyQuantity("H3"));
    }

    @Test
    @DisplayName("3. Searching for inventory that doesn't exist throws exception")
    void inventory_doesnt_exit_throws_exception() {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            inventory.getCandyQuantity("H55A3");
        });
    }

    @Test
    @DisplayName("4. Removing too much inventory throws exception")
    void removing_too_much_throws_exception() {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            inventory.removeFromInventory("H3", 125);
        });
        assertEquals(100, inventory.getCandyQuantity("H3"));
    }



}