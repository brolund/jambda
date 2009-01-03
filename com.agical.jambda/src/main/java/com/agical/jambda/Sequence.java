package com.agical.jambda;

import static com.agical.jambda.Option.none;
import static com.agical.jambda.Option.some;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public abstract class Sequence<T> implements Iterable<T> {
	public abstract Option<T> head();
	public abstract Sequence<T> tail();

	public Iterator<T> iterator() {
		return new SequenceIterator(this);
	}
	
	public static <T> Iterable<T> createSequence(T ... elements) {
		return createSequence(elements, 0);
	}
	
	private static <T> Sequence<T> createSequence(T[] elements, int i){
		return (i < elements.length)
			? new Cell<T>(elements[i], createSequence(elements, i + 1))
			: new Empty<T>();
	}
	
	private static class Empty<TC> extends Sequence<TC> {
		public Option<TC> head() { return none(); }
		public Sequence<TC> tail() { return this; };
	}
	
	private static class Cell<TC> extends Sequence<TC> {
		public final Option<TC> head;
		public final Sequence<TC> tail;
		
		public Option<TC> head() { return this.head; }
		public Sequence<TC> tail() { return this.tail; };

		public Cell(TC head) {
			this.head = some(head);
			this.tail = new Empty<TC>();
		}
		
		public Cell(TC head, Sequence<TC> tail) {
			this.head = some(head);
			this.tail = tail;
		}
	}
	
	public class SequenceIterator extends UnmodifiableIterator<T> {
	    private Sequence<T> sequence;
	    
	    public SequenceIterator(Sequence<T> cons) {
	        this.sequence = cons;
	    }

	    public boolean hasNext() {
	    	return this.sequence.head().isSome();
	    }

	    public T next() {
	    	// El�nde...
	    	T element = this.sequence.head().escape(
	    			new Fn0<T>() { public T apply() { throw new NoSuchElementException(); } });
	    	this.sequence = this.sequence.tail();
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
	
    // Projects each element of a sequence into a new form.
	public static <TSource, TTarget> Iterable<TTarget> map(final Iterable<TSource> source, final Fn1<TSource, TTarget> selector) {
		return new Iterable<TTarget>() {
		     public Iterator<TTarget> iterator() {
		         final Iterator<TSource> sourceIterator = source.iterator();
                return new UnmodifiableIterator<TTarget>() {
                    public boolean hasNext() { return sourceIterator.hasNext(); }
                    public TTarget next() { return selector.apply(sourceIterator.next()); }
                };
            }
		};
	}
	
	public static <TSource, TTarget> Iterable<TTarget> mapFlat(Iterable<TSource> source, final Fn1<TSource, Iterable<TTarget>> selector) {
		return foldRight(
				source,
				new Fn2<TSource, Sequence<TTarget>, Sequence<TTarget>>() {
					public Sequence<TTarget> apply(TSource element, Sequence<TTarget> acc) {
						return foldRight(
								selector.apply(element),
								new Fn2<TTarget, Sequence<TTarget>, Sequence<TTarget>>() {
									public Sequence<TTarget> apply(TTarget element, Sequence<TTarget> acc2) {
										return new Cell<TTarget>(element, acc2);
									}
								},
								acc);
					}
				},
				new Empty<TTarget>());
	}
	
	/**
	 * Creates a sequence, its elements are calculated from the function and the elements of input sequences occuring 
	 * at the same position in both sequences.
	 */
	public static <TSource1, TSource2, TTarget> Iterable<TTarget> zipWith(Iterable<TSource1> s1, Iterable<TSource2> s2, 
			final Fn2<TSource1, TSource2, TTarget> selector) {
		return zipWith(s1.iterator(), s2.iterator(), selector);
	}
	
	private static <TSource1, TSource2, TTarget> Sequence<TTarget> zipWith(Iterator<TSource1> i1, Iterator<TSource2> i2, 
			final Fn2<TSource1, TSource2, TTarget> selector) {
		return (i1.hasNext() && i2.hasNext())
			? new Cell<TTarget>(selector.apply(i1.next(), i2.next()), zipWith(i1, i2, selector))
			: new Empty<TTarget>();
	}
	
	// Filters a sequence of values based on a predicate.
	public static <T> Iterable<T> filter(Iterable<T> source, final Fn1<T, Boolean> predicate) {
		return foldRight(
				source,
				new Fn2<T, Sequence<T>, Sequence<T>>() {
					public Sequence<T> apply(T element, Sequence<T> acc) {
						return (predicate.apply(element)) ? new Cell<T>(element, acc) : acc;
					}
				},
				new Empty<T>());
	}
	
	/**
	 * Returns elements from a sequence as long as a specified condition is true.
	 */
	public static <T> Iterable<T> takeWhile(Iterable<T> source, final Fn1<T, Boolean> predicate) {
        return takeWhile(source.iterator(), predicate);
    }
	
	private static <T> Sequence<T> takeWhile(Iterator<T> i, final Fn1<T, Boolean> predicate) {
	    if(i.hasNext()) {
	        T element = i.next();
	        
	        return predicate.apply(element)
	            ? new Cell<T>(element, takeWhile(i, predicate))
	            : new Empty<T>();
	    }
	    else
	        return new Empty<T>();
	}
	
	// Determines whether all elements of a sequence satisfy a condition.
	public static <T> Boolean all(Iterable<T> source, final Fn1<T, Boolean> predicate) {
		// Should exit the recursion when first element generates false...
		return foldLeft(
				source,
				new Fn2<T, Boolean, Boolean>() {
					public Boolean apply(T element, Boolean acc) {
						return (predicate.apply(element)) && acc;
					}
				},
				true);
	}
	
	// Determines whether any element of a sequence exists or satisfies a condition.
	public static <T> Boolean any(final Iterable<T> source, final Fn1<T, Boolean> predicate) {
		// Should exit the recursion when first element generates true...
		return foldLeft(
				source,
				new Fn2<T, Boolean, Boolean>() {
					public Boolean apply(T element, Boolean acc) {
						return (predicate.apply(element)) || acc;
					}
				},
				false);
	}
	
	// Returns the number of elements in a sequence.
	public static <T> Integer count(final Iterable<T> source) {
		return foldLeft(
				source,
				new Fn2<T, Integer, Integer>() {
					public Integer apply(T element, Integer acc) {
						return 1 + acc;
					}
				},
				0);
	}
	
	/**
	 * Returns a number that represents how many elements in the specified sequence satisfy a condition.
	 * @param <T> The type of the iterator
	 * @param source The sequence of elements to count
	 * @param predicate The predicate function that the elements must satisfy 
	 * @return The number of elements that satisfied the predicate function.
	 */
	public static <T> Integer count(final Iterable<T> source,  final Fn1<T, Boolean> predicate) {
		return foldLeft(
				source,
				new Fn2<T, Integer, Integer>() {
					public Integer apply(T element, Integer acc) {
						return acc + (predicate.apply(element) ? 1 : 0);
					}
				},
				0);
	}
	
	/**
	 * Returns the first element in a sequence that satisfies a specified condition.
	 * @param <T> The type of the iterator
	 * @param source The sequence to search
	 * @param predicate The predicate function that is applied to the elements.
	 * @return The found element (or none)
	 */
	public static <T> Option<T> find(final Iterable<T> source,  final Fn1<T, Boolean> predicate) {
		Iterator<T> iterator = source.iterator();
		while(iterator.hasNext()) {
			T element = iterator.next();
			if(predicate.apply(element))
				return some(element);
		}
		return none();
	}
	
	/**
	 * Performs the specified action on each element of the sequence
	 * @param <T> The type of the Iterator
	 * @param source The iterator of the elements that the action should be applied
	 * @param action The action to apply to each element of the iterator 
	 * @return Unit
	 */
	public static <T> Unit forEach(final Iterable<T> source,  final Fn1<T, Unit> action) {
		Iterator<T> iterator = source.iterator();
		while(iterator.hasNext()) 
			action.apply(iterator.next());
		return Unit.unit;
	}
	
	/**
	 * This method creates a never-ending iterator of type T.
	 * 
	 * @param <T> The type of the Iterator.
	 * @param incrementor The function that provides the next value, given the previous
	 * @param seed The first value of the range
	 * @return The iterator.
	 */
    public static <T> Iterable<T> range(final Fn1<T, T> incrementor, final T seed) {
        return range(incrementor, Functions.<T, Boolean>constantly(true), seed);
    }
    
    public static <T> Iterable<T> range(final Fn1<T, T> incrementor, final Fn1<T, Boolean> limiter, final T seed) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new UnmodifiableIterator<T>() {
                    T current = seed;
                    public boolean hasNext() {
                        return limiter.apply(current);
                    }

                    public T next() {
                        T curr = current;
                        current = limiter.apply(current)?incrementor.apply(current):current;
                        return curr;
                    }
                };
            }
        };
    }
    
    private static abstract class UnmodifiableIterator<E> implements Iterator<E> {
        public void remove() {
            throw new UnsupportedOperationException("Options does not support remove.");
        }
    }

}
