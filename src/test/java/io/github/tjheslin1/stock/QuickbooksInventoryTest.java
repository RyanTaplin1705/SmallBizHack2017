package io.github.tjheslin1.stock;

import org.junit.Ignore;
import org.junit.Test;

public class QuickbooksInventoryTest {

    @Ignore
    @Test
    public void query() throws Exception {
        QuickbooksInventory.getItems();
    }

    @Ignore
    @Test
    public void updateItem() throws Exception {
        QuickbooksInventory.updateItemStock("Blue Navy Shirt");
    }
}