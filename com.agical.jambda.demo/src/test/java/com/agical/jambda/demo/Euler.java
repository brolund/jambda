package com.agical.jambda.demo;

import static com.agical.jambda.Numeric.*;
import static com.agical.jambda.Sequence.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.jambda.Functions.Fn1;

public class Euler {
    /*!!
    Project Euler in jambda.
    */
    
    @Test
    public void eulerProblem1() throws Exception {
        /*!
        The problem:
        If we list all the natural numbers below 10 that are multiples 
        of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.

        Find the sum of all the multiples of 3 or 5 below 1000.
        
        A solution in F#:
        >>>>
        List.fold_left (+) 0 (List.filter (fun n -> (n%3 = 0) || (n%5 = 0)) [0 .. 999])        
        <<<<
        
        In jambda it could be solved like this:
        */
        
        Integer sum = foldLeft( 
                filter(takeWhile(range(plus(integerType).apply(1), 0), smallerThan(integerType).rightCurry(1000)), 
                        new Fn1<Integer, Boolean>() {
                            public Boolean apply(Integer x) {
                                return x % 5 == 0 || x % 3 == 0;
                            }
                        }),
                plus(integerType),
                0);
        
        assertEquals(new Integer(233168), sum);
        /*!*/
    }
    
    @Test
    public void eulerProblem2() throws Exception {
        /*!
        The problem:
        Each new term in the Fibonacci sequence is generated by adding the previous 
        two terms. By starting with 1 and 2, the first 10 terms will be:

            1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...

        Find the sum of all the even-valued terms in the sequence which do not exceed four million.
        
        A sollution in Haskell:
        >>>>
        module P2 where
            problem2:: Int -> Int
            problem2 y =    sum [ x | x <- takeWhile (<= y) fibonacci, mod x 2 ==0]
                where
                fibonacci = 0 : 1 : zipWith (+) fibonacci (tail fibonacci)
        <<<<
        In jambda it could be solved like this:
        */
        Iterable<Integer> fibs = range(plus(integerType), 0, 1);
        
        Integer sum = 
            sum(integerType, filter(takeWhile(fibs, greaterThan(integerType).apply(4000000)), 
                       modulo(integerType).rightCurry(2).compose(equalTo(integerType).apply(0))));
        
        assertEquals(new Integer(4613732), sum);
        /*!*/
    }
}

