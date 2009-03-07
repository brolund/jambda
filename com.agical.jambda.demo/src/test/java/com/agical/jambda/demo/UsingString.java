package com.agical.jambda.demo;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import com.agical.jambda.Functions;
import com.agical.jambda.Option;
import com.agical.jambda.Strings;

public class UsingString {
    /*!!
    */
    
    @Test
    @Ignore
    public void leftAndRight() throws Exception {
        /*!
        */
        assertEquals("hej", Option.<String>none().escape(Functions.<String>constantly("hej")));
        Iterable<Option<String>> eachLine = Strings.eachLine("first line\nsecond line\nthird line");
        Iterator<Option<String>> iterator = eachLine.iterator();
        assertEquals("first line", nextValue(iterator));
        assertEquals("second line", nextValue(iterator));
        assertEquals("third line", nextValue(iterator));
        assertFalse(iterator.next().isSome());
        /*!
        */
    }

    private String nextValue(Iterator<Option<String>> iterator) {
        return iterator.next().escape(Functions.<String>constantly("wrong"));
    }
    
}
