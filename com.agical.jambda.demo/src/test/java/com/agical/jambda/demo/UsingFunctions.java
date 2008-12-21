package com.agical.jambda.demo;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;

public class UsingFunctions {
    
    @Test
    public void theSimplestFunction() throws Exception {
        /*!
        The simpest function is =Fn0=, which maps to a specific type from nothing:
        */
        Fn0<String> helloWorldFunction = new AnonymousHelloWorld();
        String greeting = helloWorldFunction.apply();
        /*!
        The =greeting= variable is:
        >>>>
        #{greeting}
        <<<<
        The implementation of =Fn0= is
        >>>>
        #{clazz('com.agical.jambda.demo.AnonymousHelloWorld')}
        <<<<
        */
        
        Storage.store("greeting", greeting);
    }
    

    @Test
    public void theMappingFunction() throws Exception {
        /*!
        =Fn1= maps data from one type to another (or the same type).
        */
        
        Fn1<String, String> personalGreeting = new PersonalGreeting();
        String greeting = personalGreeting.apply("Daniel");
        /*!
        The =greeting= variable is:
        >>>>
        #{greeting}
        <<<<
        The implementation of =Fn1= is
        >>>>
        #{clazz('com.agical.jambda.demo.PersonalGreeting')}
        <<<<
        */
        
        Storage.store("greeting", greeting);
    }
    

}
