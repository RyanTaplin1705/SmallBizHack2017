package io.github.tjheslin1.stock;

import io.github.tjheslin1.http.HttpClient;
import io.github.tjheslin1.http.Request;
import io.github.tjheslin1.http.Response;

import java.io.IOException;

import static io.github.tjheslin1.http.Header.header;
import static io.github.tjheslin1.http.QueryParameter.queryParameter;
import static java.util.Arrays.asList;

public class StockQuery {

    private final HttpClient httpClient;

    public StockQuery(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void query(String name) throws IOException {
        Response response = httpClient.execute(new Request(
                "https://sandbox-quickbooks.api.intuit.com/v3/company/123145730638514/query",
                "GET",
                "",
                asList(queryParameter("query", "SELECT * FROM item WHERE type = 'Inventory' AND name = 'Blue Navy Shirt - Large'")),
                asList(
                        header("Accept", "application/json"),
                        header("Authorization",
                                "OAuth oauth_token=\"b29723abb9eedb4b0eb95c4b4fee36d39dd0\"," +
                                        "oauth_nonce=\"46906266-ecad-4330-b38c-74a23c1c611b\"," +
                                        "oauth_consumer_key=\"qyprdUqAVqEdkSGT27rAQTiLastMk4\"," +
                                        "oauth_signature_method=\"HMAC-SHA1\"," +
                                        "oauth_timestamp=\"1488639751\"," +
                                        "oauth_version=\"1.0\"," +
                                        "oauth_signature=\"rCAINJqfiyAiR1WlTm52Dc5DE84%3D\"")
                )));

        System.out.println(response.body);
    }
}
