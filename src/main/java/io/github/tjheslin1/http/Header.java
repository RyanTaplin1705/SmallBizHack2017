package io.github.tjheslin1.http;

import static java.lang.String.format;

public class Header {

    public final String key;
    public final String value;

    private Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Header header(String key, String value) {
        return new Header(key, value);
    }

    @Override
    public String toString() {
        return format("%s: %s", key, value);
    }
}
