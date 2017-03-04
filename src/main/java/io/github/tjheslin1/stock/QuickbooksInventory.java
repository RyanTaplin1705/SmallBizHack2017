package io.github.tjheslin1.stock;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.intuit.ipp.data.ItemTypeEnum.INVENTORY;
import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

class QuickbooksInventory {

    static List<Item> getItems() throws IOException, FMSException {
        DataService dataService = dataService();

        Item item = GenerateQuery.createQueryEntity(Item.class);

        String query = select($(item)).where($(item.getType()).eq(INVENTORY)).generate();

        System.out.println("\n" + query + "\n");

        QueryResult queryResult = dataService.executeQuery(query);

        return queryResult.getEntities().stream()
                .map(o -> (Item) o)
                .collect(toList());
    }

    static void updateItemStock(String itemName) {
        DataService dataService = dataService();

        Item item = getItem(itemName);
        BigDecimal stockLevel = item.getQtyOnHand();

        BigDecimal updatedStock = stockLevel.subtract(BigDecimal.ONE);
        item.setQtyOnHand(updatedStock);

        try {
            dataService.update(item);
        } catch (FMSException e) {
            e.printStackTrace();
            throw new IllegalStateException(format("Unable to update item '%s'", item.getName()));
        }
    }

    private static Item getItem(String itemName) {
        DataService dataService = dataService();

        Item item = GenerateQuery.createQueryEntity(Item.class);

        String query = select($(item)).where($(item.getType()).eq(INVENTORY)).where($(item.getName()).eq(itemName)).generate();

        System.out.println("\n" + query + "\n");

        QueryResult queryResult = null;
        try {
            queryResult = dataService.executeQuery(query);
        } catch (FMSException e) {
            throw new IllegalStateException(format("Unable to execute query '%s'", query));
        }

        Item foundItem = (Item) queryResult.getEntities().get(0);
        System.out.println(foundItem.getName());

        return foundItem;
    }

    private static DataService dataService() {
        Context context;
        try {
            OAuthAuthorizer oAuthAuthorizer = new OAuthAuthorizer(
                    "qyprdUqAVqEdkSGT27rAQTiLastMk4",
                    "1aWKvxa0IHkrUCTrJaEsl2xZjEhvRjKkIru0lmYZ",
                    "lvprdrchtljYI8IZ0L1GYr6bSk5gZJHTAJCJ1RRsm68qVRvj",
                    "aklvdVxVn0NKxa127VskV7c7foShMIszzG34oNWZ");

            context = new Context(
                    oAuthAuthorizer,
                    "b29723abb9eedb4b0eb95c4b4fee36d39dd0",
                    ServiceType.QBO,
                    "123145730638514");
        } catch (Exception e) {
            throw new IllegalStateException("Unable to set up context using OAuth.");
        }

        return new DataService(context);
    }
}
