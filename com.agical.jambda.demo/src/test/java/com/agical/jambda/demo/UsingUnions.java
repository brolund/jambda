package com.agical.jambda.demo;

import org.junit.Test;

import com.agical.jambda.Union;

import static com.agical.jambda.demo.DemoFunctions.*;
import static org.junit.Assert.assertEquals;

public class UsingUnions {
    /*!!
    A *Union* is used when there are two possible execution paths with different in-data. There is a
    *left* Union and a *right* Union representing the two paths.

    The difference from the *Option* is that the Option only has data to act upon in the *some*-case, whereas 
    in the *none*-case it only has a return value.
    */
    
    @Test
    public void leftAndRight() throws Exception {
        /*!
        Unions can be used to handle e.g exceptions transparently. Here we create a Union for 
        the normal case:
        */
        Union<User, Exception> userRetrievalWentWell = Union.left(new User("Daniel"));
        Greeting normalGreeting = userRetrievalWentWell.map(typedPersonalGreeting(), exceptionGreeting());
        assertEquals("Hello, Daniel!", normalGreeting.getGreeting());
        /*!
        In the case we couldn't retrieve the user we have to create another greeting. This is done by passing exactly 
        the same functions, but the Union knows which function to apply depending on whether it was the left or the right 
        Union that was created.  
        */
        String message = "Couldn't retrieve user";
        Union<User, Exception> oopsExceptionWasThrown = Union.right(new Exception(message));
        Greeting exceptionGreeting = oopsExceptionWasThrown.map(typedPersonalGreeting(), exceptionGreeting());
        assertEquals("There was an Exception: " + message, exceptionGreeting.getGreeting());
        /*!
        This is of course not the only application of Unions, but it shows a powerful application.
        */
       

        
        
    }
    
}
