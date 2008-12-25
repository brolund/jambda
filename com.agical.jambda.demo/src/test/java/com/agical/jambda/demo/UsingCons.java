package com.agical.jambda.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.agical.jambda.Cons;

public class UsingCons {
    /*!
    The *Cons* is a construct for handling collections, e.g. 
       - Filtering to a new, smaller, collection
       - Mapping or transforming to a new collection of the same size
       - Aggregation of a collection to a single value
    */
    @Test
    public void mappingACollection() throws Exception {
        /*!
        Often when programming you have a collection of data (of the same type), and you 
        want to create a new collection of the same size, but with new data based on
        the data in the original collection.
        
        In this case we have a list of names, and we want to create a list of =User= objects: 
        */
        List<String> userNames = new ArrayList<String>();
        userNames.add("Daniel");
        userNames.add("Johan");
        userNames.add("Joakim");
        
        Iterable<User> users = Cons.map(userNames, DemoFunctions.userCreator());
        Iterator<User> iterator = users.iterator();
        assertEquals("Daniel", iterator.next().getName());
        assertEquals("Johan", iterator.next().getName());
        assertEquals("Joakim", iterator.next().getName());
    }



}
