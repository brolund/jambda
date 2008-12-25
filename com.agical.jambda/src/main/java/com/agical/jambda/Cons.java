package com.agical.jambda;

import static com.agical.jambda.Option.none;
import static com.agical.jambda.Option.some;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Cons<T> implements Iterable<T> {
	public final Option<T> head;
	public final Cons<T> tail;

	public Cons(T head) {
		this.head = some(head);
		this.tail = new Cons<T>();
	}
	
	public Cons(T head, Cons<T> tail) {
		this.head = some(head);
		this.tail = tail;
	}
	
	private Cons() {
		this.head = none();
		this.tail = this;
	}
	
	
	public Iterator<T> iterator() {
		return new ConsIterator(this);
	}
	
	public static class Empty<TC> extends Cons<TC> {
		public Option<TC> head() { return none(); }
		public Cons<TC> tail() { return this; };
	}
	
	public class ConsIterator implements Iterator<T> {
	    private Cons<T> cons;
	    
	    public ConsIterator(Cons<T> cons) {
	        this.cons = cons;
	    }

	    public boolean hasNext() {
	    	return this.cons.head.isSome();
	    }

	    public T next() {
	    	// El�nde...
	    	T element = this.cons.head.escape(
	    			new Fn0<T>() { public T apply() { throw new NoSuchElementException(); } });
	    	this.cons = this.cons.tail;
	    	return element;
	    }

	    public void remove() {
	        throw new UnsupportedOperationException("Options does not support remove.");
	    }
	}
	
	public static <TSource, TTarget> TTarget foldLeft(Iterable<TSource> source, Fn2<TSource, TTarget, TTarget> fn, TTarget accumulator) {
		// Recursion without "Tail Call Optimizing" is not a good idea.
		for(TSource element:source)
			accumulator = fn.apply(element, accumulator);
		return accumulator;
	}

	public static <TSource, TTarget> TTarget foldRight(Iterable<TSource> source, Fn2<TSource, TTarget, TTarget> fn, TTarget accumulator) {
	    // Elände, elände. Borde inte detta vara foldLeft? /Daniel
	    LinkedList<TSource> list = new LinkedList<TSource>();
        for(TSource element:source)
            list.addFirst(element);
        for(TSource element:list)
            accumulator = fn.apply(element, accumulator);
        return accumulator;
    }
	
	public static <TSource, TTarget> Iterable<TTarget> map(Iterable<TSource> source, final Fn1<TSource, TTarget> selector) {
		return foldRight(
				source,
				new Fn2<TSource, Cons<TTarget>, Cons<TTarget>>() {
					public Cons<TTarget> apply(TSource element, Cons<TTarget> acc) {
						return new Cons<TTarget>(selector.apply(element), acc);
					}
				},
				new Empty<TTarget>());
	}
	
	public static <T> Iterable<T> filter(Iterable<T> source, final Fn1<T, Boolean> predicate) {
		return foldLeft(
				source,
				new Fn2<T, Cons<T>, Cons<T>>() {
					public Cons<T> apply(T element, Cons<T> acc) {
						return (predicate.apply(element)) ? new Cons<T>(element, acc) : acc;
					}
				},
				new Empty<T>());
	}
}
