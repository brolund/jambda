package com.agical.jambda.demo.practical;

import java.net.URL;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class CreatingATemplateEngine {

    @Test
    public void passingTheRenderingFunctionToTheRenderer() throws Exception {
        /*!
        Often when implementing templating you wish to render things differently depending
        on the context.
        
        Functions can be a way to provide that variation.
        
        In this case we want to render a list of links as a main menu.
        */
    	Iterable<Link> links = createLinks();
        
        Fn2<String, String, String> stackAccumulator = new Fn2<String, String, String>() {
            public String apply(String string, String accumulator) {
                return accumulator + string + "\n<br/>\n";
            }
        };
        
        String result = Sequence.foldLeft(Sequence.map(links, linkToString()), stackAccumulator, "");
        /*!
        =createLinks()= creates a bunch of links for us.
        The =result= is:
        >>>>
        #{result}
        <<<<
        */
        Storage.store("result", result);
    }

    private static Fn1<Link, String> linkToString() {
        return new Fn1<Link, String>() {
            public String apply(Link arg) {
                return "<a href=\"" + arg.getUrl() + "\">" + arg.getText() + "</a>";
            }
        };
    }

    private Iterable<Link> createLinks() throws Exception {
        return Sequence.createSequence(
        		new Link("Agical", new URL("http://www.agical.com")),
        		new Link("Agile Sweden", new URL("http://www.agilesweden.se")),
        		new Link("Agile alliance", new URL("http://www.agilealliance.org")));
    }
}
