package com.agical.jambda.demo.practical;

import java.net.URL;

public class Link {
    private String text;
    private URL url;
    public Link(String text, URL url) {
        this.text = text;
        this.url = url;
    }
    public String getText() {
        return text;
    }
    public URL getUrl() {
        return url;
    }
}
