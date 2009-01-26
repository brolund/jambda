package com.agical.jambda;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tuples.Tuple2;

public class Parallel {
    
    /**
     * Transform a Jambda function to a <code>Callable&lt;R&gt;</code> to 
     * be used with the <code>java.util.concurrent</code> package.
     * @param <R> The generic type
     * @param fn Fn0&lt;R&gt;
     * @return Callable&lt;R&gt;
     */
    public static <R> Callable<R> callable(final Fn0<R> fn) {
        return new Callable<R>() {
            public R call() throws Exception {
                return fn.apply();
            }
        };
    }
    
    /**
     * Execute the provided functions in parallel in at most <code>nrOfThreads</code>
     * number of threads. The functions are all executed asap, so don't use this for lazy 
     * execution. The returned 
     * iterator will return the results in the same order they came in, as soon
     * as they are available.
     *  
     * @param <T> The returned type of the function
     * @param source The functions to execute
     * @param nrOfThreads The number of threads to use
     * @return An iterable of the return value for the functions
     */
    public static <T> Iterable<T> parallel(Iterable<Fn0<T>> source,
            Integer nrOfThreads) {
        return parallel(source, Executors.newFixedThreadPool(nrOfThreads));
    }
    
    public static <T> Iterable<T> parallel(Iterable<Fn0<T>> source,
            final ExecutorService executorService) {
        return Sequence.map(
                Sequence.foldLeft(
                    source,
                    new Fn2<Fn0<T>, ArrayList<Future<T>>, ArrayList<Future<T>>>() {
                        public ArrayList<Future<T>> apply(Fn0<T> fn0,
                                ArrayList<Future<T>> list) {
                            list.add(executorService.submit(callable(fn0)));
                            return list;
                        }
                    }, 
                    new ArrayList<Future<T>>()), 
                new Fn1<Future<T>, T>() {
                    public T apply(Future<T> future) {
                        return getFuture(future);
                    }
                });
    }
    
    public static <R1, R2> Tuple2<R1,R2> parallel(Tuple2<Fn0<R1>, Fn0<R2>> functions, ExecutorService executorService) {
        Future<R1> future1 = submit(executorService, functions.getFirst());
        Future<R2> future2 = submit(executorService, functions.getSecond());
        return Tuples.duo(getFuture(future1), getFuture(future2));
    }

    private static <R> Future<R> submit(ExecutorService executorService,
            Fn0<R> fn) {
        final Future<R> future = executorService.submit(callable(fn));
        return future;
    }
    
    private static <R> R getFuture(final Future<R> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}