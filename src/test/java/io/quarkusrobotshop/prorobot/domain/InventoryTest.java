package io.quarkusrobotshop.prorobot.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkusrobotshop.prorobot.domain.Inventory;
import io.quarkusrobotshop.prorobot.domain.Item;
import io.quarkusrobotshop.prorobot.domain.exceptions.EightySixException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryTest {

    @Inject
    Inventory inventory;

    @Test @Order(1)
    public void testStockIsPopulated() {

        Map<Item, Integer> inStock = inventory.getStock();
        assertNotNull(inStock);
        inStock.forEach((k,v) -> {
            System.out.println(k + " " + v);
        });
    }

    @Test @Order(2)
    public void testEightySixCroissants() {

        Integer itemCount = inventory.getItemCount(Item.FAC94S3);
        for (int i = 0; i < itemCount; i++) {
            try {
                inventory.decrementItem(Item.CP0FB2_BLACK);
            } catch (Exception e) {
                assertEquals(EightySixException.class, e.getClass());
                assertEquals(itemCount, Integer.valueOf(i));
            }
        }
    }
}
