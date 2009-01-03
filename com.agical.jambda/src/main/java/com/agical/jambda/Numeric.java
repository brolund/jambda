package com.agical.jambda;

import com.agical.jambda.Functions.*;
import static com.agical.jambda.Sequence.*;

public class Numeric {
	
	public static class Integer {
		/**
		 * @return Addition function for Integers
		 */
		public static Fn2<java.lang.Integer, java.lang.Integer, java.lang.Integer> plus =
			new Fn2<java.lang.Integer, java.lang.Integer, java.lang.Integer>() {
				public java.lang.Integer apply(java.lang.Integer arg1, java.lang.Integer arg2) {
					return arg1 + arg2;
				}
			};
		
		/**
		 * @return Multiplication function for Integers
		 */
		public static Fn2<java.lang.Integer, java.lang.Integer, java.lang.Integer> multiply =
			new Fn2<java.lang.Integer, java.lang.Integer, java.lang.Integer>() {
				public java.lang.Integer apply(java.lang.Integer arg1, java.lang.Integer arg2) {
					return arg1 * arg2;
				}
			};
		
		public static java.lang.Integer sum(final Iterable<java.lang.Integer> source) {
			return foldLeft(source, plus, 0);
		}
		
		public static java.lang.Integer product(final Iterable<java.lang.Integer> source) {
			return foldLeft(source, multiply, 1);
		}
		
		public static java.lang.Integer average(final Iterable<java.lang.Integer> source) {
			return foldLeft(source, plus, 0) / count(source);
		}

	}
	
	public static class Double {
		/**
		 * @return Addition function for Doubles
		 */
		public static Fn2<java.lang.Double, java.lang.Double, java.lang.Double> plus =
			new Fn2<java.lang.Double, java.lang.Double, java.lang.Double>() {
				public java.lang.Double apply(java.lang.Double arg1, java.lang.Double arg2) {
					return arg1 + arg2;
				}
			};
		
		/**
		 * @return Multiplication function for Doubles
		 */
		public static Fn2<java.lang.Double, java.lang.Double, java.lang.Double> multiply =
			new Fn2<java.lang.Double, java.lang.Double, java.lang.Double>() {
				public java.lang.Double apply(java.lang.Double arg1, java.lang.Double arg2) {
					return arg1 * arg2;
				}
			};
		
	
		public static java.lang.Double sum(final Iterable<java.lang.Double> source) {
			return foldLeft(source, plus, 0d);
		}
		
		public static java.lang.Double product(final Iterable<java.lang.Double> source) {
			return foldLeft(source, multiply, 1d);
		}
		
		public static java.lang.Double average(final Iterable<java.lang.Double> source) {
			return foldLeft(source, plus, 0d) / count(source);
		}
	}
}
