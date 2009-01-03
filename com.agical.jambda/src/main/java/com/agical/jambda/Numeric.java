package com.agical.jambda;

import com.agical.jambda.Functions.*;
import static com.agical.jambda.Sequence.*;

public class Numeric {
	
	public static class Integers {
		/**
		 * @return Addition function for Integers
		 */
		public static Fn2<Integer, Integer, Integer> plus =
			new Fn2<Integer, Integer, Integer>() {
				public Integer apply(Integer arg1, Integer arg2) {
					return arg1 + arg2;
				}
			};
		
		/**
		 * @return Multiplication function for Integers
		 */
		public static Fn2<Integer, Integer, Integer> multiply =
			new Fn2<Integer, Integer, Integer>() {
				public Integer apply(Integer arg1, Integer arg2) {
					return arg1 * arg2;
				}
			};
			
		public static Fn2<Integer, Integer, Integer> modulo =
            new Fn2<Integer, Integer, Integer>() {
                public Integer apply(Integer arg1, Integer arg2) {
                    return arg1 % arg2;
                }
            };
			
        public static Fn2<Integer, Integer, Boolean> equals =
            new Fn2<Integer, Integer, Boolean>() {
                 public Boolean apply(Integer arg1, Integer arg2) {
                    return arg1 == arg2;
                }
            };
                
        public static Fn2<Integer, Integer, Boolean> greaterThen =
            new Fn2<Integer, Integer, Boolean>() {
                 public Boolean apply(Integer arg1, Integer arg2) {
                    return arg1 > arg2;
                }
            };

        
        public static Fn2<Integer, Integer, Boolean> smallerThen =
            new Fn2<Integer, Integer, Boolean>() {
                 public Boolean apply(Integer arg1, Integer arg2) {
                    return arg1 < arg2;
                }
            };
            
        public static Integer sum(final Iterable<Integer> source) {
			return foldLeft(source, plus, 0);
		}
		
		public static Integer product(final Iterable<Integer> source) {
			return foldLeft(source, multiply, 1);
		}
		
		public static Integer average(final Iterable<Integer> source) {
			return foldLeft(source, plus, 0) / count(source);
		}

	}
	
	public static class Doubles {
		/**
		 * @return Addition function for Doubles
		 */
		public static Fn2<Double, Double, Double> plus =
			new Fn2<Double, Double, Double>() {
				public Double apply(Double arg1, Double arg2) {
					return arg1 + arg2;
				}
			};
			
		/**
		 * @return Multiplication function for Doubles
		 */
		public static Fn2<Double, Double, Double> multiply =
			new Fn2<Double, Double, Double>() {
				public Double apply(Double arg1, Double arg2) {
					return arg1 * arg2;
				}
			};
		
        public static Fn2<Double, Double, Boolean> greaterThen =
            new Fn2<Double, Double, Boolean>() {
                public Boolean apply(Double arg1, Double arg2) {
                    return arg1 > arg2;
                }
            };

        
        public static Fn2<Double, Double, Boolean> smallerThen =
            new Fn2<Double, Double, Boolean>() {
                public Boolean apply(Double arg1, Double arg2) {
                    return arg1 < arg2;
                }
            };
           
		public static Double sum(final Iterable<Double> source) {
			return foldLeft(source, plus, 0d);
		}
		
		public static Double product(final Iterable<Double> source) {
			return foldLeft(source, multiply, 1d);
		}
		
		public static Double average(final Iterable<Double> source) {
			return foldLeft(source, plus, 0d) / count(source);
		}
	}
}
