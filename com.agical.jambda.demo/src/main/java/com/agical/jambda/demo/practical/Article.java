package com.agical.jambda.demo.practical;

public class Article {
    private final String headLine;
    private final String ingress;
    private final String body;
    
    public Article(String headLine, String ingress, String body) {
        this.headLine = headLine;
        this.ingress = ingress;
        this.body = body;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getIngress() {
        return ingress;
    }

    public String getBody() {
        return body;
    }
}
