package com.agical.jambda;

import static com.agical.jambda.Option.none;
import static com.agical.jambda.Option.some;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Sequence<T> implements Iterable<T> {
	public final Option<T> head;
	public final Sequence<T> tail;

	public Sequence(T head) {
		this.head = some(head);
		this.tail = new Sequence<T>();
	}
	
	public Sequence(T head, Sequence<T> tail) {
		this.head = some(head);
		this.tail = tail;
	}
	
	private Sequence() {
		this.head = none();
		this.tail = this;
	}
	
	
	public Iterator<T> iterator() {
		return new ConsIterator(this);
	}
	
	public static class Empty<TC> extends Sequence<TC> {
		public Option<TC> head() { return none(); }
		public Sequence<TC> tail() { return this; };
	}
	
	public class ConsIterator extends UnmodifiableIterator<T> {
	    private Sequence<T> sequence;
	    
	    public ConsIterator(Sequence<T> cons) {
	        this.sequence = cons;
	    }

	    public boolean hasNext() {
	    	return this.sequence.head.isSome();
	    }

	    public T next() {
	    	// El�nde...
	    	T element = this.sequence.head.escape(
	    			new Fn0<T>() { public T apply() { throw new NoSuchElementException(); } });
	    	this.sequence = this.sequence.tail;
	    	return element;
	    }
	}
	
	public static <TSource, TTarget> TTarget foldLeft(Iterable<TSource> source, Fn2<TSource, TTarget, TTarget> fn, TTarget accumulator) {
		return foldLeft(source.iterator(), fn, accumulator); 
	}

	public static <TSource, TTarget> TTarget foldLeft(Iterator<TSource> source, Fn2<TSource, TTarget, TTarget> fn, TTarget accumulator) {
	    // Recursion without "Tail Call Optimizing" is not a good idea.
        while(source.hasNext())
            accumulator = fn.apply(source.next(), accumulator);
        return accumulator;
    }

	public static <TSource, TTarget> TTarget foldRight(Iterable<TSource> source, Fn2<TSource, TTarget, TTarget> fn, TTarget accumulator) {
        return foldRight(source.iterator(), fn, accumulator);
    }

    public static <TTarget, TSource> TTarget foldRight(Iterator<TSource> iterator, Fn2<TSource, TTarget, TTarget> fn,TTarget accumulator) {
        return iterator.hasNext()
                    ? fn.apply(iterator.next(), foldRight(iterator, fn, accumulator))
                    : accumulator;
    }
	
	public static <TSource, TTarget> Iterable<TTarget> map(Iterable<TSource> source, final Fn1<TSource, TTarget> selector) {
		return foldRight(
				source,
				new Fn2<TSource, Sequence<TTarget>, Sequence<TTarget>>() {
					public Sequence<TTarget> apply(TSource element, Sequence<TTarget> acc) {
						return new Sequence<TTarget>(selector.apply(element), acc);
					}
				},
				new Empty<TTarget>());
	}
	
	public static <T> Iterable<T> filter(Iterable<T> source, final Fn1<T, Boolean> predicate) {
		return foldRight(
				source,
				new Fn2<T, Sequence<T>, Sequence<T>>() {
					public Sequence<T> apply(T element, Sequence<T> acc) {
						return (predicate.apply(element)) ? new Sequence<T>(element, acc) : acc;
					}
				},
				new Empty<T>());
	}

    public static <T> Iterator<T> range(final Fn1<T, T> incrementor, final T seed) {
        return new UnmodifiableIterator<T>() {
            T current = seed;
            public boolean hasNext() {
                return true;
            }

            public T next() {
                T curr = current;
                current = incrementor.apply(curr);
                return curr;
            }
        };
    }
    
    private static abstract class UnmodifiableIterator<E> implements Iterator<E> {
        public void remove() {
            throw new UnsupportedOperationException("Options does not support remove.");
        }
    }
}
