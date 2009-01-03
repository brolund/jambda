package com.agical.jambda;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;

public abstract class Option<T> extends AbstractCollection<T> {
	public abstract <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn);

	public static <T> Option<T> some(T content) {
		return new Some<T>(content);
	}

	public static <T> Option<T> none() {
		return new None<T>();
	}

    public <TOut> Option<TOut> bind(Fn1<T, Option<TOut>> binder) {
        return this.map(
        		binder, 
        		Functions.<Option<TOut>>constantly(Option.<TOut>none()));
    }

    public  T escape(Fn0<T> escapeValue) {
        return this.map(Functions.<T>identity(), escapeValue);
    }
    
    public Boolean isSome() {
    	return this.map(
    			Functions.<T, Boolean>constantly(true), 
    			Functions.<Boolean>constantly(false));
    }
    
	static final private class None<T> extends Option<T> {
		private static final int SIZE = 0;

		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return noneFn.apply();
		}
		
		@Override
		public Iterator<T> iterator() {
			return new IteratorAdapter(SIZE) {
				@Override
				public boolean hasNext() {
					return false;
				}
			};
		}

		@Override
		public int size() {
			return SIZE;
		}
	}

	static final private class Some<T> extends Option<T> {
		private static final int SIZE = 1;
		private final T content;

		public Some(T content) {
		    if(content==null) throw new NullPointerException("The some() method must have a non-null argument.");
			this.content = content;
		}

		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return someFn.apply(this.content);
		}
		
		@Override
		public int size() {
			return SIZE;
		}

		@Override
		public Iterator<T> iterator() {
			return new IteratorAdapter(SIZE) {
				@Override
				public T next() {
					super.advance();
					return content;
				}
			};
		}
	}
	
	abstract class IteratorAdapter implements Iterator<T> {
	    private int size;

	    public IteratorAdapter(int size) {
	    	this.size = size;
	    }

	    public T next() {
	    	advance();
	    	return null;
	    }

	    public void advance() {
	    	if (size <= 0) throw new NoSuchElementException();
	    	--size;
	    }

	    public boolean hasNext() {
	    	return size > 0;
	    }

	    public void remove() {
	    	throw new UnsupportedOperationException();
	    }
	}

}



