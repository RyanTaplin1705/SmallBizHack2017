package io.github.tjheslin1.stock;

import com.intuit.ipp.data.Item;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class QuickbooksInventoryTest {

    @Ignore
    @Test
    public void queryAllItems() throws Exception {
        List<Item> items = QuickbooksInventory.getItems();

        items.forEach(item -> System.out.println(item.getName() + ": " + item.getQtyOnHand()));
    }

    @Ignore
    @Test
    public void queryItem() throws Exception {
        Item item = QuickbooksInventory.getItem("White Oxford Shirt");

        System.out.println(item.getName() + ", " + item.getQtyOnHand());
    }

    @Ignore
    @Test
    public void updateItem() throws Exception {
        QuickbooksInventory.updateItemStock("Blue Navy Shirt");
    }
}