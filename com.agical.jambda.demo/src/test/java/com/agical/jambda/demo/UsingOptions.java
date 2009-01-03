package com.agical.jambda.demo;

import static com.agical.jambda.demo.DemoFunctions.typedAnonymousGreeting;
import static com.agical.jambda.demo.DemoFunctions.typedPersonalGreeting;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.jambda.Option;

public class UsingOptions {
    /*!!
    *Options* are used to make the decision on data existence. Functions are passed to an Option 
    to handle the cases *some data exist*, i.e. *some*, and *no data exists*, i.e. *none*.
    
    The decision on what execution path to go is made when the Option is created, 
    i.e. which is at the earliest possible moment. This is a way of building in 
    the *null-pattern* in FP.    
    */
    @Test
    public void theSomeOption() throws Exception {
        /*!
        In this case we create an option *with* data by calling the *Option factory method* =some=
        with the data to be provided, in this case a =User=. 
        */
        Option<User> user = Option.some(new User("Daniel"));
        Greeting greeting = user.map(typedPersonalGreeting(), typedAnonymousGreeting);
        assertEquals("Hello, Daniel!", greeting.getGreeting());
        /*!
        The =map= method takes two functions:
           1. The function that will map from the data type to the output type (the first argument)
           1. The function that will provide the output for the case when no data existed (the second argument)
        In this case, there is data available, and the first function will be called.
        */
    }
    
    @Test
    public void theNoneOption() throws Exception {
        /*!
        Here we create an option *without* data by calling the factory method =none=.
        Since it represents no data available, no data need to be provided. 
        */
        Option<User> option = Option.none();
        Greeting greeting = option.map(typedPersonalGreeting(), typedAnonymousGreeting);
        assertEquals("Hello!", greeting.getGreeting());
        /*!
        In this case, the second function will be used to create the =Greeting=.
        */
        
    }

}
