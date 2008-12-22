package com.agical.jambda.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;

public class UsingFunctions {
    
    @Test
    public void theSimplestFunction() throws Exception {
        /*!
        The simplest function is =Fn0=, which maps to a specific type from nothing:
        */
        String greetingsPhrase = anonymousGreeting().apply();
        /*!
        The =greeting= variable is:
        >>>>
        #{greetingsPhrase}
        <<<<
        The implementation of the function is
        >>>>
        #{clazz.anonymousGreeting}
        <<<<
        This is a static method that resides in the current class, but in reality you will probably
        group these domain functions by their purpose. Also, static method can be 
        imported with a static import, hence the code won't need being referenced by class name.
        */
        assertEquals("Hello!", greetingsPhrase);
        Storage.store("greetingsPhrase", greetingsPhrase);
    }


    @Test
    public void theMappingFunction() throws Exception {
        /*!
        =Fn1= maps data from one type to another (or the same type).
        */
        String greetingsPhrase = personalGreeting().apply("Daniel");
        /*!
        The =greetingsPhrase= variable is:
        >>>>
        #{greetingsPhrase}
        <<<<
        The implementation of =Fn1= is
        >>>>
        #{clazz.personalGreeting}
        <<<<
        */
        assertEquals("Hello, Daniel!", greetingsPhrase);
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
        #{clazz.userCreator}
        <<<<
        >>>>
        #{clazz.typedPersonalGreeting}
        <<<<
        >>>>
        #{clazz.greetingToString}
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
    

    public static Fn1<String, User> userCreator() {
        return new Fn1<String, User>() {
            public User apply(String name) {
                return new User(name);
            }
        };
    }


    public static Fn0<String> anonymousGreeting() {
        return new Fn0<String>() {
            public String apply() {
                return "Hello!";
            }
        };
    }


    public static Fn1<Greeting, String> greetingToString() {
        return new Fn1<Greeting, String>() {
            public String apply(Greeting greeting) {
                return greeting.getGreeting();
            }
        };
    }


    public static Fn1<String, String> personalGreeting() {
        return new Fn1<String, String>() {
            public String apply(String name) {
                return "Hello, " + name + "!";
            }
        };
    }


    public static Fn1<User, Greeting> typedPersonalGreeting() {
        return new Fn1<User, Greeting>() {
            public Greeting apply(User user) {
                return new Greeting("Hello, " + user.getName() + "!");
            }
        };
    }

}
