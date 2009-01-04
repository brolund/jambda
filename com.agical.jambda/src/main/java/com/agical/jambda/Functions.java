package com.agical.jambda;

import com.agical.jambda.Tuples.*;

public class Functions {

    public static abstract class Fn0<R> {
        public abstract R apply();
    }

    public static abstract class Fn1<T, R> extends Fn0<Fn1<T, R>> {
        public abstract R apply(T arg);
        
        public Fn1<T, R> apply() {
			return this;
		}
        
        public Fn0<R> curry(final T arg) {
            final Fn1<T, R> origFn = this;
            return new Fn0<R>() {
                public R apply() {
                    return origFn.apply(arg);
                }
            };
        }

		public <R2> Fn1<T, R2> compose(final Fn1<R, R2> g) {
        	final Fn1<T, R> f = this;
        	return new Fn1<T, R2>() {
                public R2 apply(T x) {
                    return g.apply(f.apply(x));
                }
            };
        }
    }

    public static abstract class Fn2<T1, T2, R> extends Fn1<T1, Fn1<T2, R>>{
        public abstract R apply(T1 arg1, T2 arg2);

        public Fn1<T2, R> apply(final T1 arg1) {
            final Fn2<T1, T2, R> origFn = this;
            return new Fn1<T2, R>() {
                public R apply(T2 arg2) {
                    return origFn.apply(arg1, arg2);
                }
            };
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2) {
            final Fn2<T1, T2, R> origFn = this;
            return new Fn1<T1, R>() {
                public R apply(T1 arg1) {
                    return origFn.apply(arg1, arg2);
                }
            };
        }
        
        public R apply(Tuple2<T1, T2> arg) {
        	return this.apply(arg.getFirst(), arg.getSecond());
        }
    }

    public static abstract class Fn3<T1, T2, T3, R> extends Fn2<T1, T2, Fn1<T3, R>>{
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3);

        public Fn1<T3, R> apply(final T1 arg1, final T2 arg2) {
            final Fn3<T1, T2, T3, R> origFn = this;
            return new Fn1<T3, R>() {
                public R apply(T3 arg3) {
                    return origFn.apply(arg1, arg2, arg3);
                }
            };
        }
        
        public R apply(Tuple3<T1, T2, T3> arg) {
        	return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird());
        }
    }

    public static abstract class Fn4<T1, T2, T3, T4, R> extends Fn3<T1, T2, T3, Fn1<T4, R>>{
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4);

        public Fn1<T4, R> apply(final T1 arg1, final T2 arg2, final T3 arg3) {
            final Fn4<T1, T2, T3, T4, R> origFn = this;
            return new Fn1<T4, R>() {
                public R apply(T4 arg4) {
                    return origFn.apply(arg1, arg2, arg3, arg4);
                }
            };
        }   
        
        public R apply(Tuple4<T1, T2, T3, T4> arg) {
        	return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(), arg.getForth());
        }
    }
    
    public static abstract class Fn5<T1, T2, T3, T4, T5, R> extends Fn4<T1, T2, T3, T4, Fn1<T5, R>>{
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);

        public Fn1<T5, R> apply(final T1 arg1, final T2 arg2, final T3 arg3, final T4 arg4) {
            final Fn5<T1, T2, T3, T4, T5, R> origFn = this;
            return new Fn1<T5, R>() {
                public R apply(T5 arg5) {
                    return origFn.apply(arg1, arg2, arg3, arg4, arg5);
                }
            };
        }
        
        public R apply(Tuple5<T1, T2, T3, T4, T5> arg) {
        	return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(), arg.getForth(), arg.getFifth());
        }
    }
    
    public static abstract class Fn6<T1, T2, T3, T4, T5, T6, R> extends Fn5<T1, T2, T3, T4, T5, Fn1<T6, R>>{
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);

        public Fn1<T6, R> apply(final T1 arg1, final T2 arg2, final T3 arg3, final T4 arg4, final T5 arg5) {
            final Fn6<T1, T2, T3, T4, T5, T6, R> origFn = this;
            return new Fn1<T6, R>() {
                public R apply(T6 arg6) {
                    return origFn.apply(arg1, arg2, arg3, arg4, arg5, arg6);
                }
            };
        }        

        public R apply(Tuple6<T1, T2, T3, T4, T5, T6> arg) {
        	return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(), arg.getForth(), arg.getFifth(), arg.getSixth());
        }
    }

    /// --------------
    
    public static <R> Fn0<R> constantly(final R result) {
        return new Fn0<R>() {
            public R apply() {
                return result;
            }
        };
    }
    
    public static <T, R> Fn1<T, R> constantly(final R result) {
        return new Fn1<T, R>() {
            public R apply(T arg) {
                return result;
            }
        };
    }

    public static <T> Fn1<T, T> identity() {
        return new Fn1<T, T>() {
            public T apply(T arg) {
                return arg;
            }
        };
    }

    // compose(f, g) = (\ x. g(f x))
    public static <TX, TY, TZ> Fn1<TX, TZ> compose(final Fn1<TX, TY> f, final Fn1<TY, TZ> g) {
        return f.compose(g);
    }
 }