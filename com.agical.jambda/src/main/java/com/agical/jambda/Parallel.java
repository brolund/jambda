package com.agical.jambda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Parallel {
    
    public static <T> Callable<T> callable(final Fn0<T> fn) {
        return new Callable<T>() {
            public T call() throws Exception {
                return fn.apply();
            }
        };
    }
    
    public static <T> Iterable<T> exec(Iterable<Fn0<T>> source,
            Integer nrOfThreads) {
        return exec(source, Executors.newFixedThreadPool(nrOfThreads));
    }
    
    public static <T> Iterable<T> exec(Iterable<Fn0<T>> source,
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
                    public T apply(Future<T> arg) {
                        try {
                            return arg.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}