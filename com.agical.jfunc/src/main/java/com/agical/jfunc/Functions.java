package com.agical.jfunc;

public class Functions {

    public static abstract class Fn0<R> {
        public abstract R apply();
    }

    public static abstract class Fn1<T, R> {
        public abstract R apply(T arg);
    }

    public static abstract class Fn2<T1, T2, R> {
        public abstract R apply(T1 arg1, T2 arg2);

        public Fn1<T2, R> apply(final T1 arg1) {
            final Fn2<T1, T2, R> origFn = this;
            return new Fn1<T2, R>() {
                public R apply(T2 arg2) {
                    return origFn.apply(arg1, arg2);
                }
            };
        }
    }

    public static abstract class Fn3<T1, T2, T3, R> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3);

        public Fn1<T3, R> apply(final T1 arg1, final T2 arg2) {
            final Fn3<T1, T2, T3, R> origFn = this;
            return new Fn1<T3, R>() {
                public R apply(T3 arg3) {
                    return origFn.apply(arg1, arg2, arg3);
                }
            };
        }

        public Fn2<T2, T3, R> Apply(final T1 arg1) {
            final Fn3<T1, T2, T3, R> origFn = this;
            return new Fn2<T2, T3, R>() {
                public R apply(T2 arg2, T3 arg3) {
                    return origFn.apply(arg1, arg2, arg3);
                }
            };
        }
    }

    public static abstract class Fn4<T1, T2, T3, T4, R> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4);

        public Fn1<T4, R> apply(final T1 arg1, final T2 arg2, final T3 arg3) {
            final Fn4<T1, T2, T3, T4, R> origFn = this;
            return new Fn1<T4, R>() {
                public R apply(T4 arg4) {
                    return origFn.apply(arg1, arg2, arg3, arg4);
                }
            };
        }

        public Fn2<T3, T4, R> apply(final T1 arg1, final T2 arg2) {
            final Fn4<T1, T2, T3, T4, R> origFn = this;
            return new Fn2<T3, T4, R>() {
                public R apply(T3 arg3, T4 arg4) {
                    return origFn.apply(arg1, arg2, arg3, arg4);
                }
            };
        }

        public Fn3<T2, T3, T4, R> apply(final T1 arg1) {
            final Fn4<T1, T2, T3, T4, R> origFn = this;
            return new Fn3<T2, T3, T4, R>() {
                public R apply(T2 arg2, T3 arg3, T4 arg4) {
                    return origFn.apply(arg1, arg2, arg3, arg4);
                }
            };
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
        return new Fn1<TX, TZ>() {
            public TZ apply(TX x) {
                return g.apply(f.apply(x));
            }
        };
    }
}