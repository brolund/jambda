package com.agical.jambda.demo.practical;

import static com.agical.jambda.Numeric.*;
import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Numeric;
import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn2;

public class CreatingATemplateEngine {

    @Test
    public void passingTheRenderingFunctionToTheRenderer() throws Exception {
        /*!
        Often when implementing templating you wish to render things differently depending
        on the context.
        
        Functions can be a way to provide that variation.
        
        In this case we want to render in HTML a list of links as a main menu.
        */
        Iterable<Link> links = createLinks();
        
        String result = Sequence.foldLeft(
                Sequence.map(links, Rendering.linkToString()), 
                Rendering.stackAccumulator(), 
                "");
        /*!
        =createLinks()= creates a bunch of links for us.
        The =result= is:
        >>>>
        #{result}
        <<<<
        The link transformer looks like this:
        >>>>
        #{clazz('com.agical.jambda.demo.practical.Rendering').linkToString}
        <<<<
        The stack accumulator looks like this:
        >>>>
        #{clazz('com.agical.jambda.demo.practical.Rendering').stackAccumulator}
        <<<<
        */
        Storage.store("result", result);
    }

    @Test
    public void forAnArticle() throws Exception {
        /*!
        In the same way, we can present articles:
        */
        Iterable<Article> articles = createArticles();
        String result = Sequence.foldLeft(
                Sequence.map(articles, Rendering.articleToString()), 
                Rendering.stackAccumulator(), 
                "");
        /*!
        The =result= is:
        >>>>
        #{result}
        <<<<
        */
        Storage.store("result", result);
        
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipisicing elit, \n" +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ", loreMipsum(1));
    }
    
    @Test
    public void addingItAllToAPage() throws Exception {
        /*!
        
        */
        
    }
    
    private Iterable<Article> createArticles() {
        return Sequence.createSequence(
                new Article("FP takes the lead", "Functional programming ...", loreMipsum(3)),
                new Article("Java and functional programming", "Java isn't a bad option...", loreMipsum(2)),
                new Article("Agical does it again", "Jambda takes the FP paradigm to Java...", loreMipsum(4))
                );
    }

    private String loreMipsum(int nrOfSentences) {
        Iterable<Integer> numbersUpToNrOfSentences = Sequence.takeWhile(Numeric.naturalNumbers, smallerThan(integerType).rightCurry(nrOfSentences));

        return Sequence.foldLeft(numbersUpToNrOfSentences, new Fn2<Integer,String,String>() {
            public String apply(Integer dummy, String accumulator) {
                return "Lorem ipsum dolor sit amet, consectetur adipisicing elit, \n" +
                       "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + 
                	   accumulator;
            }
        }, "");
    }

    private Iterable<Link> createLinks() throws Exception {
        return Sequence.createSequence(
        		new Link("Agical", new URL("http://www.agical.com")),
        		new Link("Agile Sweden", new URL("http://www.agilesweden.se")),
        		new Link("Agile alliance", new URL("http://www.agilealliance.org")));
    }
}
