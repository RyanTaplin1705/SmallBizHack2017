package io.github.tjheslin1.stock;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import io.github.tjheslin1.http.HttpClient;

import java.io.IOException;

import static com.intuit.ipp.data.ItemTypeEnum.INVENTORY;
import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

public class StockQuery {

    private final HttpClient httpClient;

    public StockQuery(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void query(String name) throws IOException, FMSException {
        OAuthAuthorizer oAuthAuthorizer = new OAuthAuthorizer(
                "qyprdUqAVqEdkSGT27rAQTiLastMk4",
                "1aWKvxa0IHkrUCTrJaEsl2xZjEhvRjKkIru0lmYZ",
                "lvprdrchtljYI8IZ0L1GYr6bSk5gZJHTAJCJ1RRsm68qVRvj",
                "aklvdVxVn0NKxa127VskV7c7foShMIszzG34oNWZ");

        Context context = new Context(
                oAuthAuthorizer,
                "b29723abb9eedb4b0eb95c4b4fee36d39dd0",
                ServiceType.QBO,
                "123145730638514");

        DataService dataService = new DataService(context);

        Item item = GenerateQuery.createQueryEntity(Item.class);

        String query = select($(item)).where($(item.getType()).eq(INVENTORY)).generate();

        System.out.println("\n" + query + "\n");

        QueryResult queryResult = dataService.executeQuery(query);

        for (IEntity itemResult : queryResult.getEntities()) {
            System.out.println(((Item) itemResult).getName());
        }
    }

//    public void query(String name) throws IOException {
//        Response response = httpClient.execute(new Request(
//                "https://sandbox-quickbooks.api.intuit.com/v3/company/123145730638514/query",
//                "GET",
//                "",
//                asList(queryParameter("query", "SELECT * FROM item WHERE type = 'Inventory' AND name = 'Blue Navy Shirt - Large'")),
//                asList(
//                        header("Accept", "application/json"),
//                        header("Authorization",
//                                "OAuth oauth_token=\"b29723abb9eedb4b0eb95c4b4fee36d39dd0\"," +
//                                        "oauth_nonce=\"46906266-ecad-4330-b38c-74a23c1c611b\"," +
//                                        "oauth_consumer_key=\"qyprdUqAVqEdkSGT27rAQTiLastMk4\"," +
//                                        "oauth_signature_method=\"HMAC-SHA1\"," +
//                                        "oauth_timestamp=\"1488639751\"," +
//                                        "oauth_version=\"1.0\"," +
//                                        "oauth_signature=\"rCAINJqfiyAiR1WlTm52Dc5DE84%3D\"")
//                )));
//
//        System.out.println(response.body);
//    }
}
