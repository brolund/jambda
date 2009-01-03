package com.agical.jambda.demo;

import static com.agical.bumblebee.junit4.Storage.store;
import static com.agical.jambda.demo.DemoFunctions.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class UsingSequence {
    /*!
    The *Sequence* is a construct for handling collections, e.g. 
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
        
        Iterable<User> users = Sequence.map(userNames, userCreator());
        Iterator<User> iterator = users.iterator();
        assertEquals("Daniel", iterator.next().getName());
        assertEquals("Johan", iterator.next().getName());
        assertEquals("Joakim", iterator.next().getName());
        /*!
        In this case we are returned an =Iterable<User>= since the function provided to the =map= 
        function created a =User= object for each =String= object in the provided =List<String>=.  
        */
    }
    
    @Test
    public void theFoldFunctions() throws Exception {
        /*!
        The =map= function uses one of the *fold* functions: =foldLeft= or =foldRight=.
        The fold functions can be used to either map a collection to another, like we saw in the =map= example,
        or it can be used to aggregate or accumulate data, which we will show now.
        
        Let us start with a normal list containing some names:
        */
        List<String> userNames = new ArrayList<String>();
        userNames.add("Daniel");
        userNames.add("Johan");
        userNames.add("Joakim");
        /*!
        Now we will use an aggregating function to create a string:
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').aggregateStrings}
        <<<<
        This function simply appends the =string= parameter to the =accumulation= parameter and puts
        parenthesis around.  
        
        Then we use the =foldLeft= and the =foldRight= respectively to apply the function to the list of names
        */
        String foldLeft = Sequence.foldLeft(userNames, aggregateStrings(), "");
        String foldRight = Sequence.foldRight(userNames, aggregateStrings(), "");
        /*!
        The results are 
        >>>>
        foldLeft: #{foldLeft}
        foldRight: #{foldRight}
        <<<<
        The result depends on how the fold was performed, and on how the function makes the aggregation.
        */
        
        store("foldLeft", foldLeft);
        store("foldRight", foldRight);
        assertEquals("(((Daniel)Johan)Joakim)", foldLeft);
        assertEquals("(((Joakim)Johan)Daniel)", foldRight);
    }

    @Test
    public void filteringACollection() throws Exception {
        /*!
        Other times you have a collection of data and you want to filter out some of the elements.
        
        We have the same list of names, but we only want the names that start with *J*: 
        */
        List<String> userNames = new ArrayList<String>();
        userNames.add("Daniel");
        userNames.add("Johan");
        userNames.add("Joakim");
        
        Iterable<String> users = Sequence.filter(userNames, acceptStringsStartingWithJ());
        Iterator<String> iterator = users.iterator();
        assertEquals("Johan", iterator.next());
        assertEquals("Joakim", iterator.next());
        assertFalse(iterator.hasNext());
        /*!
        The filtered iterator does not contain the name *Daniel*.
        
        The function used to filter looks like this:
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').acceptStringsStartingWithJ}
        <<<<
        
        */
    }
    
    @Test
    public void creatingNewSequences() throws Exception {
        /*!
        =Sequence= can be used as a *sequence factory* to create new 
        sequences. Here, we create a never-ending sequence of integers:
        */
        Fn1<Integer, Integer> incrementor = new Fn1<Integer, Integer>() {
            public Integer apply(Integer previous) {
                return previous+1;
            }
        };
        
        Iterator<Integer> range = Sequence.range(incrementor, 0);
        
        assertEquals(new Integer(0), range.next());
        assertEquals(new Integer(1), range.next());
        assertEquals(new Integer(2), range.next());
        /*!
        Note that =range(...)= takes a *generic* incrementor that calculates the 
        next value in the sequence, hence your range can contain objects of any 
        type. The seed is the first value for the range and must be of the same 
        generic type as the incrementor. 
        */
        
    }
    
}