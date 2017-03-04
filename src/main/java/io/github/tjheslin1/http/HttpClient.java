package io.github.tjheslin1.http;

import java.io.IOException;

public interface HttpClient {
    Response execute(Request request) throws IOException;
}