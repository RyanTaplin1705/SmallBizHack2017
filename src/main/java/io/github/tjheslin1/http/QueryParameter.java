package io.github.tjheslin1.http;

public class QueryParameter {

    public final String key;
    public final String value;

    private QueryParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static QueryParameter queryParameter(String key, String value) {
        return new QueryParameter(key, value);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
