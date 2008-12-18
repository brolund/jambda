package com.agical.jfunc;

import com.agical.jfunc.Functions.Fn1;

public abstract class Union<TLeft, TRight> {
	public abstract <TOut> TOut map(Fn1<TLeft, TOut> leftFn,
			Fn1<TRight, TOut> rightFn);

	public static <TLeft, TRight> Union<TLeft, TRight> left(TLeft content) {
		return new Left<TLeft, TRight>(content);
	}

	public static <TLeft, TRight> Union<TLeft, TRight> right(TRight content) {
		return new Right<TLeft, TRight>(content);
	}

	static final private class Left<TLeft, TRight> extends Union<TLeft, TRight> {
		TLeft content;

		public Left(TLeft content) {
			this.content = content;
		}

		@Override
		public <TOut> TOut map(Fn1<TLeft, TOut> leftFn,
				Fn1<TRight, TOut> rightFn) {
			return leftFn.apply(this.content);
		}

		@Override
		public String toString() {
			return content.toString();
		}
	}

	static final private class Right<TLeft, TRight> extends
			Union<TLeft, TRight> {
		TRight content;

		public Right(TRight content) {
			this.content = content;
		}

		@Override
		public <TOut> TOut map(Fn1<TLeft, TOut> leftFn,
				Fn1<TRight, TOut> rightFn) {
			return rightFn.apply(this.content);
		}

		@Override
		public String toString() {
			return content.toString();
		}
	}
}