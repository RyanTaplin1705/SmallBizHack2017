package io.github.tjheslin1.stock;

import io.github.tjheslin1.http.ApacheHttpClient;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class StockQueryTest {

    @Ignore
    @Test
    public void query() throws Exception {
        StockQuery stockQuery = new StockQuery(new ApacheHttpClient());

        try {
            stockQuery.query("Blue Navy Shirt - Large");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}