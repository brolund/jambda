package com.agical.jambda.demo;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Parallel;
import com.agical.jambda.Sequence;
import com.agical.jambda.Unit;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class ParallelExecution {
    
    @Test(timeout=500)
    public void executeFunctionWithTheSameReturnType() throws Exception {
        /*!
        Jambda can help you execute functions in parallel. In this case we create a 
        function that we later curry with a number for the output.
        */
        Fn1<Integer,String> fn = new Fn1<Integer, String>() {
            public String apply(Integer nr) {
                try {
                    long slept = (long) (Math.random()*200);
                    Thread.sleep(slept);
                    return "Nr " + nr + " slept for " + slept + "ms";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Iterable<String> results = 
            Parallel.parallel(Sequence.createSequence(
                    fn.curry(1),
                    fn.curry(2),
                    fn.curry(3),
                    fn.curry(4),
                    fn.curry(5),
                    fn.curry(6)), 3);
        /*!
        
        The result is
        >>>>
        #{result}
        <<<<
        
        */
        String string = Sequence.foldLeft(results, new Fn2<String, String, String>() {
            public String apply(String arg, String accumulator) {
                return accumulator+"\n"+arg;
            }
        }, "");
        Storage.store("result", string); 
    }
    
}
