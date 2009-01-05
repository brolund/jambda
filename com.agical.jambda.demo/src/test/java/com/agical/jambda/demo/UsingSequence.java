package com.agical.jambda.demo;

import static com.agical.bumblebee.junit4.Storage.store;
import static com.agical.jambda.demo.DemoFunctions.acceptStringsStartingWithJ;
import static com.agical.jambda.demo.DemoFunctions.aggregateStrings;
import static com.agical.jambda.demo.DemoFunctions.userCreator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn1;
import static com.agical.jambda.Numeric.*;

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
        
        In this case we have a sequence of names, and we want to create a list of =User= objects: 
        */
        Iterable<String> userNames = Sequence.createSequence("Daniel", "Johan", "Joakim");
        
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
    public void mappingACollectionToMultpleResultsPerElement() throws Exception {
        /*!
        To be written ...
        */
        Iterable<Integer> ordinals = Sequence.createSequence(1, 2);
        
        Iterable<User> users = 
        	Sequence.mapFlat(
        			ordinals, 
        			new Fn1<Integer, Iterable<User>>() {
        				public Iterable<User> apply(Integer i) {
        					return Sequence.map(Sequence.createSequence("Daniel" + i, "Johan" + i, "Joakim" + i), userCreator());
        				}
        			});
        Iterator<User> iterator = users.iterator();
        assertEquals("Daniel1", iterator.next().getName());
        assertEquals("Johan1", iterator.next().getName());
        assertEquals("Joakim1", iterator.next().getName());
        assertEquals("Daniel2", iterator.next().getName());
        assertEquals("Johan2", iterator.next().getName());
        assertEquals("Joakim2", iterator.next().getName());
        /*!
        To be written
        */
    }
    
    @Test
    public void theFoldFunctions() throws Exception {
        /*!
        The =map= function uses one of the *fold* functions: =foldLeft= or =foldRight=.
        The fold functions can be used to either map a collection to another, like we saw in the =map= example,
        or it can be used to aggregate or accumulate data, which we will show now.
        
        Let us start with a sequence containing some names:
        */
    	Iterable<String> userNames = Sequence.createSequence("Daniel", "Johan", "Joakim");
    	
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
        
        We have the same sequence of names, but we only want the names that start with *J*: 
        */
        Iterable<String> userNames = Sequence.createSequence("Daniel", "Johan", "Joakim");
        
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
        
        Iterable<Integer> range = Sequence.range(incrementor, 0);
        Iterator<Integer> rangeIterator = range.iterator();
        assertEquals(new Integer(0), rangeIterator.next());
        assertEquals(new Integer(1), rangeIterator.next());
        assertEquals(new Integer(2), rangeIterator.next());
        /*!
        Note that =range(...)= takes a *generic* incrementor that calculates the 
        next value in the sequence, hence your range can contain objects of any 
        type. The seed is the first value for the range and must be of the same 
        generic type as the incrementor. 
        
        If you want a limited range, provide a limiter:
        */
        Fn1<Integer, Boolean> limiter = new Fn1<Integer, Boolean>() {
            public Boolean apply(Integer number) {
                int limit = 2;
                return number<limit;
            }
        };
        Iterable<Integer> limitedRange = Sequence.range(incrementor, limiter, 0);
        Iterator<Integer> limitedRangeIterator = limitedRange.iterator();
        
        assertEquals(new Integer(0), limitedRangeIterator.next());
        assertEquals(new Integer(1), limitedRangeIterator.next());
        assertFalse(limitedRangeIterator.hasNext());
        
        /*!
         
        */
    }
    
    @Test
    public void limitACollection() throws Exception {
        Iterable<Integer> naturalNumbers = Sequence.range(plus(integerType).apply(1), 0);
        
        Iterable<Integer> numbersUpTo5 = Sequence.takeWhile(naturalNumbers, smallerThan(integerType).rightCurry(6));
        Iterator<Integer> iterator = numbersUpTo5.iterator();
        assertEquals(new Integer(0), iterator.next());
        assertEquals(new Integer(1), iterator.next());
        assertEquals(new Integer(2), iterator.next());
        assertEquals(new Integer(3), iterator.next());
        assertEquals(new Integer(4), iterator.next());
        assertEquals(new Integer(5), iterator.next());
        assertFalse(iterator.hasNext());
    }

    
}
