package com.agical.jambda;

import static com.agical.jambda.Option.none;
import static com.agical.jambda.Option.some;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public abstract class Sequence {
    
    private static abstract class ImmutableIterator<E> implements Iterator<E> {
        public void remove() {
            throw new UnsupportedOperationException("ImmutableIterator does not support remove.");
        }
    }
    
    private static class EmptyIterator<T> extends ImmutableIterator<T> {
        public boolean hasNext() { return false; }
        public T next() { throw new NoSuchElementException(); }
    }
    
    public static <T> Iterable<T> Empty() {
        return new Iterable<T>() {
            public Iterator<T> iterator() { return new EmptyIterator<T>(); }
        };
    }

    public static <T> Iterable<T> createSequence(T ... elements) {
		return Arrays.asList(elements);
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
		         return new ImmutableIterator<TTarget>() {
		             public boolean hasNext() { return sourceIterator.hasNext(); }
		             public TTarget next() { return selector.apply(sourceIterator.next()); }
		         };
		     }
		};
	}
	
	public static <TSource, TTarget> Iterable<TTarget> mapFlat(final Iterable<TSource> source, final Fn1<TSource, Iterable<TTarget>> selector) {
	    return new Iterable<TTarget>() {
            public Iterator<TTarget> iterator() {
                final Iterator<TSource> sourceIterator = source.iterator();
                return new ImmutableIterator<TTarget>() {
                    private Iterator<TTarget> currentIterator = new EmptyIterator<TTarget>();
                    private Option<TTarget> current = this.getNext();
                    
                    public boolean hasNext() { return current.isSome(); }
                    
                    public TTarget next() { 
                        TTarget curr = this.current.escape(new Fn0<TTarget>() { 
                            public TTarget apply() { throw new NoSuchElementException(); } 
                        });
                        this.current = this.getNext();
                        return curr;
                    }
                    
                    // Need to be rewritten... but seems to work.
                    private Option<TTarget> getNext() {
                        if(!currentIterator.hasNext())
                            currentIterator = sourceIterator.hasNext()
                                ? selector.apply(sourceIterator.next()).iterator()
                                : new EmptyIterator<TTarget>();
                        if(currentIterator.hasNext())
                            return some(currentIterator.next());
                        else 
                            return none(); 
                    }
                };
            }
       };
	}
	
	/**
	 * Creates a sequence, its elements are calculated from the function and the elements of input sequences occuring 
	 * at the same position in both sequences.
	 */
	public static <TSource1, TSource2, TTarget> Iterable<TTarget> zipWith(final Iterable<TSource1> s1, final Iterable<TSource2> s2, 
			final Fn2<TSource1, TSource2, TTarget> selector) {
	    return new Iterable<TTarget>() {
            public Iterator<TTarget> iterator() {
                final Iterator<TSource1> i1 = s1.iterator();
                final Iterator<TSource2> i2 = s2.iterator();
                return new ImmutableIterator<TTarget>() {
                    public boolean hasNext() { return i1.hasNext() && i2.hasNext(); }
                    public TTarget next() { return selector.apply(i1.next(), i2.next()); }
                };
            }
       };
	}
	
	/**
	 * Filters a sequence of values based on a predicate.
	 */
	public static <T> Iterable<T> filter(final Iterable<T> source, final Fn1<T, Boolean> predicate) {
	    return new Iterable<T>() {
            public Iterator<T> iterator() {
                final Iterator<T> sourceIterator = source.iterator();
                return new ImmutableIterator<T>() {
                    private Option<T> current = this.getNext();
                    
                    public boolean hasNext() { return this.current.isSome(); }
                    
                    public T next() { 
                        T curr = this.current.escape(new Fn0<T>() { 
                            public T apply() { throw new NoSuchElementException(); } 
                        });
                        this.current = this.getNext();
                        return curr;
                    }
                    
                    private Option<T> getNext() {
                        while (sourceIterator.hasNext()) {
                            T element = sourceIterator.next();
                            if(predicate.apply(element))
                                return some(element);
                        }
                        return none();    
                    }
                };
            }
       };
	}
	
	/**
	 * Returns elements from a sequence as long as a specified condition is true.
	 */
	public static <T> Iterable<T> takeWhile(final Iterable<T> source, final Fn1<T, Boolean> predicate) {
	    return new Iterable<T>() {
            public Iterator<T> iterator() {
                final Iterator<T> sourceIterator = source.iterator();
                return new ImmutableIterator<T>() {
                    private Option<T> current = this.nextApplicable();
                    
                    public boolean hasNext() { return this.current.isSome(); }
                    
                    public T next() { 
                        T curr = this.current.escape(new Fn0<T>() { 
                            public T apply() { throw new NoSuchElementException(); } 
                        });
                        this.current = this.nextApplicable();
                        return curr;
                    }
                    
                    private Option<T> nextApplicable() {
                        if (sourceIterator.hasNext()) {
                            T element = sourceIterator.next();
                            if(predicate.apply(element))
                                return some(element);
                            else
                                return none();    
                        }
                        return none();    
                    }
                };
            }
       };
    }
	
	// Determines whether all elements of a sequence satisfy a condition.
	public static <T> Boolean all(Iterable<T> source, final Fn1<T, Boolean> predicate) {
	    Iterator<T> iterator = source.iterator();
        while(iterator.hasNext())
            if(!predicate.apply(iterator.next()))
                return false;
        return true;
	}
	
	// Determines whether any element of a sequence exists or satisfies a condition.
	public static <T> Boolean any(final Iterable<T> source, final Fn1<T, Boolean> predicate) {
	    Iterator<T> iterator = source.iterator();
	    while(iterator.hasNext())
	        if(predicate.apply(iterator.next()))
	            return true;
        return false;
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
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new ImmutableIterator<T>() {
                    T current = seed;
                    public boolean hasNext() { return true; }

                    public T next() {
                        T curr = current;
                        current = incrementor.apply(current);
                        return curr;
                    }
                };
            }
        };
    }
    
    public static <T> Iterable<T> range(final Fn1<T, T> incrementor, final Fn1<T, Boolean> limiter, final T seed) {
        return takeWhile(range(incrementor, seed), limiter);
    }
 }
