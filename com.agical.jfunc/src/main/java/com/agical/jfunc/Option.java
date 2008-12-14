package com.agical.jfunc;

<<<<<<< local
import com.agical.jfunc.Functions.*;
=======
import java.util.AbstractCollection;
>>>>>>> other
import java.util.Iterator;
import java.util.NoSuchElementException;

<<<<<<< local
=======
import com.agical.jfunc.Functions.Fn0;
import com.agical.jfunc.Functions.Fn1;
>>>>>>> other

<<<<<<< local
public abstract class Option<E> implements Iterable<E> {
=======
public abstract class Option<T> extends AbstractCollection<T> {
	public abstract <R> Option<R> map(Fn1<T, R> something);
>>>>>>> other

<<<<<<< local
    public Iterator<E> iterator() {
        return new OptionIterator<E>(this);
    }
	public abstract <TOut> TOut map(Fn1<E, TOut> someFn, Fn0<TOut> noneFn);

	static <T> Option<T> some(T content){
=======
	public static <T> Option<T> some(T content) {
>>>>>>> other
		return new Some<T>(content);
	}

<<<<<<< local
	static <T> Option<T> none() {
=======
	public static <T> Option<T> none() {
>>>>>>> other
		return new None<T>();
	}

<<<<<<< local
    public <TOut> Option<TOut> bind(Fn1<E, Option<TOut>> binder) {
        return this.map(
        		binder, 
        		Functions.<Option<TOut>>constantly(Option.<TOut>none()));
    }
=======
	static final private class None<T> extends Option<T> {
		private static final int SIZE = 0;
>>>>>>> other

<<<<<<< local
    public  E escape(Fn0<E> escapeValue) {
        return this.map(Functions.<E>identity(), escapeValue);
    }
    
    public Boolean isSome() {
    	return this.map(
    			Functions.<E, Boolean>constantly(true), 
    			Functions.<Boolean>constantly(false));
    }
=======
		public <R> Option<R> map(Fn1<T, R> something) {
			return none();
		}
>>>>>>> other

<<<<<<< local
    static final private class None<T> extends Option<T> {
		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return noneFn.apply();
=======
		@Override
		public Iterator<T> iterator() {
			return new IteratorAdapter<T>(SIZE) {
				@Override
				public boolean hasNext() {
					return false;
				}
			};
		}

		@Override
		public int size() {
			return SIZE;
>>>>>>> other
		}
	}

	static final private class Some<T> extends Option<T> {
<<<<<<< local
		private T content;
=======
		private static final int SIZE = 1;
		T content;
>>>>>>> other

		public Some(T content) {
			this.content = content;
		}

<<<<<<< local
		public <TOut> TOut map(Fn1<T, TOut> someFn, Fn0<TOut> noneFn) {
			return someFn.apply(this.content);
=======
		public <R> Option<R> map(Fn1<T, R> something) {
			return some(something.apply(content));
		}
		
		@Override
		public int size() {
			return SIZE;
		}

		
		@Override
		public Iterator<T> iterator() {
			return new IteratorAdapter<T>(SIZE) {
				@Override
				public T next() {
					super.advance();
					return content;
				}
			};
>>>>>>> other
		}
	}
<<<<<<< local
=======
}

abstract class IteratorAdapter<T> implements Iterator<T> {
	private int size;

	public IteratorAdapter(int size) {
		this.size = size;
	}
>>>>>>> other
	
<<<<<<< local
	private static class OptionIterator<E> implements Iterator<E> {
	    private final Option<E> option;
	    private boolean atBeginning = false;
=======
	@Override
	public T next() {
		advance();
		return null;
	}
	
	public void advance() {
		if (size <= 0) throw new NoSuchElementException();
		--size;
	}
	
	@Override
	public boolean hasNext() {
		return size > 0;
	}
>>>>>>> other

<<<<<<< local
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
=======
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
>>>>>>> other
	}
<<<<<<< local
}
=======
}>>>>>>> other
