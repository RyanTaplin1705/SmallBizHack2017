package io.github.tjheslin1.http;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class Request {

    public final String url;
    public final String method;
    public final String body;
    private List<QueryParameter> queryParameters;
    public List<Header> headers;

    public Request(String url, String method, String body, List<QueryParameter> queryParameters, List<Header> headers) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.queryParameters = queryParameters;
        this.headers = headers;
    }

    @Override
    public String toString() {
        return format("%s%n%s %s%s%n%s", headers,
                method, url,
                queryParameters(),
                body);
    }

    private String queryParameters() {
        if (queryParameters.size() > 0) {
            return "?" + queryParameters.stream().map(QueryParameter::toString).collect(joining("?"));
        } else {
            return "";
        }
    }
}
