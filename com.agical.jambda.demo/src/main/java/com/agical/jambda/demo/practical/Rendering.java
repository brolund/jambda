package com.agical.jambda.demo.practical;

import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Rendering {

    public static Fn2<String, String, String> stackAccumulator() {
        return new Fn2<String, String, String>() {
            public String apply(String string, String accumulator) {
                return accumulator + string + "\n<br/>\n";
            }
        };
    }

    public static Fn1<Link, String> linkToString() {
        return new Fn1<Link, String>() {
            public String apply(Link arg) {
                return "<a href=\"" + arg.getUrl() + "\">" + arg.getText() + "</a>";
            }
        };
    }

    public static Fn1<Article, String> articleToString() {
        return new Fn1<Article, String>() {
            public String apply(Article article) {
                return "<h1>" + article.getHeadLine() + "</h1>\n" +
                	   "<p><i><b>" + article.getIngress() + "</b></i></p>\n" +
                	   "<p>" + article.getBody() + "</p>\n";
            }
            
        };
    }

}
