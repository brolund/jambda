package com.agical.jambda.demo;

import java.util.Date;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Functions.Fn0;

public class UsingFunctions {
    /*!!
    Functions are like mappings from zero or more types to a single output type. 
    */

    @Test
    public void theSimplestFunction() throws Exception {
        /*!
        The simpest function is =Fn0=, which maps to a specific type from nothing.
        */
        Fn0<String> helloWorldFunction = new Fn0<String>() {
            @Override
            public String apply() {
                return "Hello World at " + new Date();
            }
        };
        String greeting = helloWorldFunction.apply();
        /*!
        The =greeting= variable is:
        >>>>
        #{greeting}
        <<<<
        */
        
        Storage.store("greeting", greeting);
    }
    
}
