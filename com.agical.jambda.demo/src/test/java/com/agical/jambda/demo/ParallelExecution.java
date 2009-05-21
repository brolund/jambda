package com.agical.jambda.demo;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.agical.bumblebee.junit4.Storage;
import com.agical.jambda.Numeric;
import com.agical.jambda.Parallel;
import com.agical.jambda.Sequence;
import com.agical.jambda.Tuples;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tuples.Tuple2;
import com.sun.xml.internal.ws.message.ByteArrayAttachment;

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
    
    public static Fn1<Long,Long> delay() {
        return new Fn1<Long,Long>() {
            public Long apply(Long sleep) {
                try {
                    Thread.sleep(sleep);
                    return sleep;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ;
    }
    @Test
    public void differentReturnValues() throws Exception {
        /*!
        If you need to have different return types, use the functions taking a 
        =Tuple= of functions. 
        */
        Fn1<Integer,String> delay1 = new Fn1<Integer,String>() {
            public String apply(Integer sleep) {
                return "Slept " + delay().apply(sleep.longValue()) + "ms";
            }
        };
        Fn1<String,Long> delay2 = new Fn1<String,Long>() {
            public Long apply(String sleep) {
                return new Long(delay().apply(Long.parseLong(sleep)));
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        long start = System.currentTimeMillis();
        
        Tuple2<Fn0<String>, Fn0<Long>> functions = Tuples.duo(delay1.curry(100),delay2.curry("101"));
        Tuple2<String, Long> result = Parallel.parallel(functions, executor);
        
        long end = System.currentTimeMillis();
        
        assertEquals(result.getFirst(), "Slept 100ms");
        assertEquals(result.getSecond(), new Long(101));

        assertTrue("Should take at least 100ms", end-start>=100);
        assertTrue("Should take less than 200ms due to threading", end-start<200);
        /*!*/
    }
    
    @Test
    public void distributedExecution() throws Exception {
        /*
        A *pure function* is a function that have no side-effects, i.e. 
        it only read the parameters it gets and it produces a return value. 
        
        Pure functions can always be executed in parallel, even at different
        hardware nodes, without any concerns about data synchronization.
        
        Jambda is building up support for transparent distributed execution.
        */
        
        
        
        Fn0<Double> originalFunction = Numeric.multiply(Numeric.doubleType).apply(3D).curry(7D);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream (byteArrayOutputStream);
        objectOutputStream.writeObject ( originalFunction );
        
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        Fn0<Double> awakenedFunction = (Fn0<Double>) objectInputStream.readObject();

        assertEquals(originalFunction.apply(), awakenedFunction.apply());
        fail("Need to implement distributed execution");
    }
}
