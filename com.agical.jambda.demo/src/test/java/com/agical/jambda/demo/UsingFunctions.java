package com.agical.jambda.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import static com.agical.jambda.demo.DemoFunctions.*;

public class UsingFunctions {
    /*!!
    DemoFunctions is the building blocks of functional programming. They are used in
       - Function composition
       - Options 
       - Unions
    */
    
    @Test
    public void theSimplestFunction() throws Exception {
        /*!
        The simplest function is =Fn0=, which maps to a specific type, in this 
        case =String=, from nothing:
        */
        Fn0<String> anonymousGreeting = new Fn0<String>() {
            public String apply() {
                return "Hello!";
            }
        };
        String greetingsPhrase = anonymousGreeting.apply();
        /*!
        The result is:
        >>>>
        #{greetingsPhrase}
        <<<<
        This is an anonymous inner class, but in reality you will probably
        extract and group these domain functions by their purpose, e.g. as constants. 
        Also, constants and static method can be imported with a static import, hence the code won't 
        need being referenced by class name.
        */
        assertEquals("Hello!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
    }


    @Test
    public void theMappingFunction() throws Exception {
        /*!
        =Fn1= maps data from one type to another, in this case =Integer= to =String=.
        */
        String greetingsPhrase = personalGreeting().apply(new Integer(1));
        /*!
        The =greetingsPhrase= variable is:
        >>>>
        #{greetingsPhrase}
        <<<<
        The implementation of =personalGreeting()= is
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').personalGreeting}
        <<<<
        */
        assertEquals("Hello, nr 1!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
    }
    
    @Test
    public void usingTypedArguments() throws Exception {
        /*!
        Although you could create functions like the one above, it makes much more sense to 
        type the arguments. The resulting types can then in turn be used by other functions, 
        and the readability of the code base increases.
        
        In this case we want to transform a =String= (the user name) into a =User=, then the
        =User= to a =Greeting=, and for documentation 
        purposes here we also want to turn the =Greeting= into a =String=. Each such mapping 
        gets its own reusable mapping function:
        */
        String greetingsPhrase = greetingToString().apply(
                typedPersonalGreeting().apply(
                                    userCreator().apply("Daniel")));
        /*!
        The =greetingsPhrase= variable is (as before):
        >>>>
        #{greetingsPhrase}
        <<<<
        The implementations used are:
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').userCreator}
        <<<<
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').typedPersonalGreeting}
        <<<<
        >>>>
        #{clazz('com.agical.jambda.demo.DemoFunctions').greetingToString}
        <<<<
        The instantiable classes =Greeting= and =User= are just *simple java beans*, or 
        *structs* as a functional programmer would put it. 
        */
        assertEquals("Hello, Daniel!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
    }

    @Test
    public void usingCompositionToBuildComplexFunctions() throws Exception {
        /*!
        Instead of assembling the functions at the same time as they are executed, 
        composition can be used.
        
        In the last example, we went through =String-User-Greeting-String=. We can use
        the =compose= method and create a function to use later on:
        */
        Fn1<String, String> greetPerson = 
            userCreator().compose(typedPersonalGreeting().compose(greetingToString()));
        
        String greetingsPhrase = greetPerson.apply("Daniel");
        /*!
        This composition can of course be stored as any other domain function in some 
        module (class) in your code base.
        
        The =greetingsPhrase= variable is (as before):
        >>>>
        #{greetingsPhrase}
        <<<<
        */
        assertEquals("Hello, Daniel!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
    }        
    @Test
    public void compositionIsCommutative() throws Exception {
        /*!
        The interesting thing about composition is that it is commutative, i.e. the function above
        could also be created using
        */
        Fn1<String, String> greetPerson = 
            userCreator().compose(typedPersonalGreeting()).compose(greetingToString());
        String greetingsPhrase = greetPerson.apply("Johan");
        /*!
        (note the placement of the parenthesis)
        
        The =greetingsPhrase= variable is:
        >>>>
        #{greetingsPhrase}
        <<<<
        */
        assertEquals("Hello, Johan!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
        
    }

}
