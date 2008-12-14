package com.agical.jfunc;

import com.agical.jfunc.Functions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class Option<E> implements Iterable<E> {

    public Iterator<E> iterator() {
        return new OptionIterator<E>(this);
    }
	public abstract <TOut> TOut map(Fn1<E, TOut> someFn, Fn0<TOut> noneFn);

	static <T> Option<T> some(T content){
		return new Some<T>(content);
	}

	static <T> Option<T> none() {
		return new None<T>();
	}

    public <TOut> Option<TOut> bind(Fn1<E, Option<TOut>> binder) {
        return this.map(
        		binder, 
        		Functions.<Option<TOut>>constantly(Option.<TOut>none()));
    }

    public  E escape(Fn0<E> escapeValue) {
        return this.map(Functions.<E>identity(), escapeValue);
    }
    
    public Boolean isSome() {
    	return this.map(
    			Functions.<E, Boolean>constantly(true), 
    			Functions.<Boolean>constantly(false));
    }

    static final private class None<T> extends Option<T> {
		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return noneFn.apply();
		}
	}

	static final private class Some<T> extends Option<T> {
		private T content;

		public Some(T content) {
			this.content = content;
		}

		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return someFn.apply(this.content);
		}
	}
	
	private static class OptionIterator<E> implements Iterator<E> {
	    private final Option<E> option;
	    private boolean atBeginning = false;

	    public OptionIterator(Option<E> option) {
	        this.option = option;
	    }

	    public boolean hasNext() {
	    	return this.option.map(
	        		Functions.<E, Boolean>constantly(atBeginning), 
	        		Functions.<Boolean>constantly(false));
	    }

	    public E next() {
	        return this.option.map(
	        		Functions.<E>identity(), 
	        		new Fn0<E>() {
	                    public E apply() { throw new NoSuchElementException(); }
	        		});
	    }

	    public void remove() {
	        throw new UnsupportedOperationException("Options does not support remove.");
	    }
	}
}
