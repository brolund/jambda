package com.agical.jfunc;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.agical.jfunc.Functions.Fn0;
import com.agical.jfunc.Functions.Fn1;

public abstract class Option<T> extends AbstractCollection<T> {
	public abstract <R> Option<R> map(Fn1<T, R> something);

	public static <T> Option<T> some(T content) {
		return new Some<T>(content);
	}

	public static <T> Option<T> none() {
		return new None<T>();
	}

	static final private class None<T> extends Option<T> {
		private static final int SIZE = 0;

		public <R> Option<R> map(Fn1<T, R> something) {
			return none();
		}

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
		}
	}

	static final private class Some<T> extends Option<T> {
		private static final int SIZE = 1;
		T content;

		public Some(T content) {
			this.content = content;
		}

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
		}
	}
}

abstract class IteratorAdapter<T> implements Iterator<T> {
	private int size;

	public IteratorAdapter(int size) {
		this.size = size;
	}
	
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

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}