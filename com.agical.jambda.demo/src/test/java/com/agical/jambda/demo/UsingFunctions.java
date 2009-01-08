package com.agical.jambda.demo;

import static com.agical.jambda.demo.DemoFunctions.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Functions;
import com.agical.jambda.Numeric;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Functions.Fn3;
import com.agical.jambda.Functions.Fn4;
import com.agical.jambda.Functions.Fn5;
import com.agical.jambda.Functions.Fn6;
import com.agical.jambda.Numeric.IntegerType;

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

    @Test
    public void currying() throws Exception {
        /*!
        *Currying* in functional programming is just what it sounds like; 
        you take a function an you spice it with one or more of the values it requires,
        and it produces a new function. 
        
        The simplest example in Jambda is this:
        */
        Fn0<User> spicedUp = userCreator().curry("Daniel");
        /*!
        The =userCreator= is a function that takes a String and produces a
        User with that name. By *currying* it with the name, a new  
        function is created, and that function will create and return a User when 
        being called:
        */
        User user = spicedUp.apply();
        assertEquals("Daniel", user.getName());
        /*!*/
    }
    
    @Test
    public void curryingFunctionsWithSeveralParameters() throws Exception {
        /*!
        For functions that take more parameters you have to curry from the
        left or from the right. 
        
        In this case we use the =plus= function that takes two integers and adds 
        them to produce another integer. 
        */
        Fn2<Integer, Integer, Integer> plus = Numeric.plus(IntegerType.integerType);
        /*!
        Here we do it both left- and right curry style:
        */
        Fn1<Integer, Integer> rightCurried = plus.rightCurry(2);
        Fn1<Integer, Integer> leftCurried = plus.apply(3);
        /*!
        After this we got a function that waits for the second argument to complete the addition.
        */
        Integer rightCurryResult = rightCurried.apply(3);
        Integer leftCurryResult = leftCurried.apply(2);
        assertEquals(new Integer(5), rightCurryResult);
        assertEquals(new Integer(5), leftCurryResult);
        /*!
        */
    }

    @Test
    public void moreAboutCurryingInJambda() throws Exception {
        /*!
        All functions of N arguments (N=[2,6]) have an apply (*leftCurry*) 
        that takes the left-most M (M=[1,N-1]) arguments and returns a 
        function of N-M arguments.
        */
        Functions.Fn6<Byte, Short, Integer, Long, Float, Double, String> fn6 = 
            new Fn6<Byte, Short, Integer, Long, Float, Double, String>() {
                public String apply(Byte b, Short s, Integer i,Long l, Float f, Double d) {
                    return b+s+i+l+f+d+"";
                }
          
            };
        /*!
        This is an example where, for N is 6 and M is 1, we curry down to the result: 
        */
        Fn5<Short, Integer, Long, Float, Double, String> fn5 = fn6.apply((byte)1);
        Fn4<Integer,Long,Float,Double,String> fn4 = fn5.apply((short)2);
        Fn3<Long,Float,Double,String> fn3 = fn4.apply(4);
        Fn2<Float, Double, String> fn2 = fn3.apply(8L);
        Fn1<Double,String> fn1 = fn2.apply(16F);
        String result = fn1.apply(32D);
        
        assertEquals("63.0", result);
        /*!
        There is also a =rightCurry= method that does the same from the right. 
        */
        Fn5<Byte, Short, Integer, Long, Float, String> fn5_right = fn6.rightCurry(64D);
        Fn4<Byte, Short, Integer, Long, String> fn4_right = fn5_right.rightCurry(32F);
        Fn3<Byte, Short, Integer, String> fn3_right = fn4_right.rightCurry(16L);
        Fn2<Byte, Short, String> fn2_right = fn3_right.rightCurry(8);
        Fn1<Byte, String> fn1_right = fn2_right.rightCurry((short)4);
        Fn0<String> fn0_right = fn1_right.curry((byte)2);
        
        String resultOfRightCurry = fn0_right.apply();
        assertEquals("126.0", resultOfRightCurry);
        /*!
        Several arguments can be passed, both from the left and from the right: 
        */
        Fn1<Long, String> fn1_leftAndRight = fn6.apply((byte)1, (short)2, 4).rightCurry(100F, 1000D);
        String resultOfLeftAndRightCurry = fn1_leftAndRight.apply(10000L);
        assertEquals("11107.0", resultOfLeftAndRightCurry);
        /*!*/
    }
}
