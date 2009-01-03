package com.agical.jambda;

import com.agical.jambda.Functions.*;
import static com.agical.jambda.Sequence.*;

public class Numeric {
	
	/**
	 * @return Addition function for Integers
	 */
	public static Fn2<Integer, Integer, Integer> plusInteger() {
		return new Fn2<Integer, Integer, Integer>() {
			public Integer apply(Integer arg1, Integer arg2) {
				return arg1 + arg2;
			}
		};
	}
	
	/**
	 * @return Addition function for Doubles
	 */
	public static Fn2<Double, Double, Double> plusDouble() {
		return new Fn2<Double, Double, Double>() {
			public Double apply(Double arg1, Double arg2) {
				return arg1 + arg2;
			}
		};
	}
	
	/**
	 * @return Multiplication function for Integers
	 */
	public static Fn2<Integer, Integer, Integer> multiplyInteger() {
		return new Fn2<Integer, Integer, Integer>() {
			public Integer apply(Integer arg1, Integer arg2) {
				return arg1 * arg2;
			}
		};
	}
	
	
	/**
	 * @return Multiplication function for Doubles
	 */
	public static Fn2<Double, Double, Double> multiplyDouble() {
		return new Fn2<Double, Double, Double>() {
			public Double apply(Double arg1, Double arg2) {
				return arg1 * arg2;
			}
		};
	}

		
	public static Integer sum(final Iterable<Integer> source) {
		return foldLeft(source, plusInteger(), 0);
	}
	
	public static Integer product(final Iterable<Integer> source) {
		return foldLeft(source, multiplyInteger(), 0);
	}
	

	public static Double sum(final Iterable<Double> source) {
		return foldLeft(source, plusDouble(), 0d);
	}
	
	public static Double product(final Iterable<Double> source) {
		return foldLeft(source, multiplyDouble(), 0d);
	}

}
