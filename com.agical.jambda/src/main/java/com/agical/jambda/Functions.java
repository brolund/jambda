package com.agical.jambda;


import com.agical.jambda.Tuples.Tuple2;
import com.agical.jambda.Tuples.Tuple3;
import com.agical.jambda.Tuples.Tuple4;
import com.agical.jambda.Tuples.Tuple5;
import com.agical.jambda.Tuples.Tuple6;

public class Functions {
    
    public static abstract class Fn0<R> {
        public abstract R apply();
    }
    
    public static abstract class Fn1<T, R> {
        public abstract R apply(T arg);
        
        public Fn1<T, R> apply() {
            return this;
        }
        
        public Fn0<R> curry(final T arg) {
            return new Fn0<R>() {
                public R apply() {
                    return Fn1.this.apply(arg);
                }
            };
        }
        
        public <R2> Fn1<T, R2> compose(final Fn1<R, R2> g) {
            return new Fn1<T, R2>() {
                public R2 apply(T x) {
                    return execute(g.curry(Fn1.this.apply(x)));
                }
            };
        }
        
        public <R2> R2 execute(final Fn0<R2> f) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                try {
                    System.out.println(Class.forName(
                            stackTraceElement.getClassName()).getName()
                            + ":" + stackTraceElement.getMethodName());
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return f.apply();
        }
    }
    
    public static abstract class Fn2<T1, T2, R> extends Fn1<T1, Fn1<T2, R>> {
        public abstract R apply(T1 arg1, T2 arg2);
        
        public Fn1<T2, R> apply(final T1 arg1) {
            return new Fn1<T2, R>() {
                public R apply(T2 arg2) {
                    return Fn2.this.apply(arg1, arg2);
                }
            };
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2) {
            return new Fn1<T1, R>() {
                public R apply(T1 arg1) {
                    return Fn2.this.apply(arg1, arg2);
                }
            };
        }
        
        public R apply(Tuple2<T1, T2> arg) {
            return this.apply(arg.getFirst(), arg.getSecond());
        }
    }
    
    public static abstract class Fn3<T1, T2, T3, R> extends
            Fn1<T1, Fn2<T2, T3, R>> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3);
        
        public Fn1<T3, R> apply(final T1 arg1, final T2 arg2) {
            return apply(arg1).apply(arg2);
        }
        
        public Fn2<T2, T3, R> apply(final T1 arg1) {
            return new Fn2<T2, T3, R>() {
                public R apply(T2 arg2, T3 arg3) {
                    return Fn3.this.apply(arg1, arg2, arg3);
                }
            };
        }
        
        public Fn2<T1, T2, R> rightCurry(final T3 arg3) {
            return new Fn2<T1, T2, R>() {
                public R apply(T1 arg1, T2 arg2) {
                    return Fn3.this.apply(arg1, arg2, arg3);
                }
            };
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2, final T3 arg3) {
            return rightCurry(arg3).rightCurry(arg2);
        }
        
        public R apply(Tuple3<T1, T2, T3> arg) {
            return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird());
        }
    }
    
    public static abstract class Fn4<T1, T2, T3, T4, R> extends
            Fn1<T1, Fn3<T2, T3, T4, R>> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
        
        public Fn1<T4, R> apply(final T1 arg1, final T2 arg2, final T3 arg3) {
            return this.apply(arg1, arg2).apply(arg3);
        }
        
        public Fn2<T3, T4, R> apply(final T1 arg1, final T2 arg2) {
            return this.apply(arg1).apply(arg2);
        }
        
        public Fn3<T2, T3, T4, R> apply(final T1 arg1) {
            return new Fn3<T2, T3, T4, R>() {
                public R apply(T2 arg2, T3 arg3, T4 arg4) {
                    return Fn4.this.apply(arg1, arg2, arg3, arg4);
                }
            };
        }
        
        public Fn3<T1, T2, T3, R> rightCurry(final T4 arg4) {
            return new Fn3<T1, T2, T3, R>() {
                public R apply(T1 arg1, T2 arg2, T3 arg3) {
                    return Fn4.this.apply(arg1, arg2, arg3, arg4);
                }
            };
        }
        
        public Fn2<T1, T2, R> rightCurry(final T3 arg3, final T4 arg4) {
            return rightCurry(arg4).rightCurry(arg3);
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2, final T3 arg3, final T4 arg4) {
            return rightCurry(arg4).rightCurry(arg3).rightCurry(arg2);
        }
        
        public R apply(Tuple4<T1, T2, T3, T4> arg) {
            return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(),
                    arg.getForth());
        }
    }
    
    public static abstract class Fn5<T1, T2, T3, T4, T5, R> extends
            Fn1<T1, Fn4<T2, T3, T4, T5, R>> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
        
        public Fn1<T5, R> apply(final T1 arg1, final T2 arg2, final T3 arg3,
                final T4 arg4) {
            return this.apply(arg1).apply(arg2).apply(arg3).apply(arg4);
        }
        
        public Fn2<T4, T5, R> apply(final T1 arg1, final T2 arg2, final T3 arg3) {
            return this.apply(arg1).apply(arg2).apply(arg3);
        }
        
        public Fn3<T3, T4, T5, R> apply(final T1 arg1, final T2 arg2) {
            return this.apply(arg1).apply(arg2);
        }
        
        public Fn4<T2, T3, T4, T5, R> apply(final T1 arg1) {
            return new Fn4<T2, T3, T4, T5, R>() {
                public R apply(T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
                    return Fn5.this.apply(arg1, arg2, arg3, arg4, arg5);
                }
            };
        }
        
        public Fn4<T1, T2, T3, T4, R> rightCurry(final T5 arg5) {
            return new Fn4<T1, T2, T3, T4, R>() {
                public R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
                    return Fn5.this.apply(arg1, arg2, arg3, arg4, arg5);
                }
            };
        }
        
        public Fn3<T1, T2, T3, R> rightCurry(final T4 arg4, final T5 arg5) {
            return rightCurry(arg5).rightCurry(arg4);
        }
        
        public Fn2<T1, T2, R> rightCurry(final T3 arg3, final T4 arg4,
                final T5 arg5) {
            return rightCurry(arg5).rightCurry(arg4).rightCurry(arg3);
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2, final T3 arg3,
                final T4 arg4, final T5 arg5) {
            return rightCurry(arg5).rightCurry(arg4).rightCurry(arg3)
                    .rightCurry(arg2);
        }
        
        public R apply(Tuple5<T1, T2, T3, T4, T5> arg) {
            return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(),
                    arg.getForth(), arg.getFifth());
        }
    }
    
    public static abstract class Fn6<T1, T2, T3, T4, T5, T6, R> extends
            Fn1<T1, Fn5<T2, T3, T4, T5, T6, R>> {
        public abstract R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5,
                T6 arg6);
        
        public Fn1<T6, R> apply(final T1 arg1, final T2 arg2, final T3 arg3,
                final T4 arg4, final T5 arg5) {
            return this.apply(arg1).apply(arg2).apply(arg3).apply(arg4).apply(
                    arg5);
        }
        
        public Fn2<T5, T6, R> apply(final T1 arg1, final T2 arg2,
                final T3 arg3, final T4 arg4) {
            return this.apply(arg1).apply(arg2).apply(arg3).apply(arg4);
        }
        
        public Fn3<T4, T5, T6, R> apply(final T1 arg1, final T2 arg2,
                final T3 arg3) {
            return this.apply(arg1).apply(arg2).apply(arg3);
        }
        
        public Fn4<T3, T4, T5, T6, R> apply(final T1 arg1, final T2 arg2) {
            return this.apply(arg1).apply(arg2);
        }
        
        public Fn5<T2, T3, T4, T5, T6, R> apply(final T1 arg1) {
            return new Fn5<T2, T3, T4, T5, T6, R>() {
                public R apply(T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6) {
                    return Fn6.this.apply(arg1, arg2, arg3, arg4, arg5, arg6);
                }
            };
        }
        
        public R apply(Tuple6<T1, T2, T3, T4, T5, T6> arg) {
            return this.apply(arg.getFirst(), arg.getSecond(), arg.getThird(),
                    arg.getForth(), arg.getFifth(), arg.getSixth());
        }
        
        public Fn5<T1, T2, T3, T4, T5, R> rightCurry(final T6 arg6) {
            return new Fn5<T1, T2, T3, T4, T5, R>() {
                public R apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
                    return Fn6.this.apply(arg1, arg2, arg3, arg4, arg5, arg6);
                }
            };
        }
        
        public Fn4<T1, T2, T3, T4, R> rightCurry(final T5 arg5, final T6 arg6) {
            return rightCurry(arg6).rightCurry(arg5);
        }
        
        public Fn3<T1, T2, T3, R> rightCurry(final T4 arg4, final T5 arg5,
                final T6 arg6) {
            return rightCurry(arg6).rightCurry(arg5).rightCurry(arg4);
        }
        
        public Fn2<T1, T2, R> rightCurry(final T3 arg3, final T4 arg4,
                final T5 arg5, final T6 arg6) {
            return rightCurry(arg6).rightCurry(arg5).rightCurry(arg4)
                    .rightCurry(arg3);
        }
        
        public Fn1<T1, R> rightCurry(final T2 arg2, final T3 arg3,
                final T4 arg4, final T5 arg5, final T6 arg6) {
            return rightCurry(arg6).rightCurry(arg5).rightCurry(arg4)
                    .rightCurry(arg3).rightCurry(arg2);
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
    public static <TX, TY, TZ> Fn1<TX, TZ> compose(final Fn1<TX, TY> f,
            final Fn1<TY, TZ> g) {
        return f.compose(g);
    }
    
    /* 
    
    // Implementation of the Y-combinator 
    // - unless we accept references to 'this' from within an apply-method... :)
    private abstract static class Recursive<A, R> extends Fn1<Recursive<A, R>, Fn1<A, R>> { }
    
    public static <A, R> Fn1<A, R> yCombinator(final Fn1<Fn1<A, R>, Fn1<A, R>> f) {
        Recursive<A, R> rec = new Recursive<A, R>() {
            public Fn1<A, R> apply(final Recursive<A, R> r) {
                return new Fn1<A, R>() {
                    public R apply(A a) {
                        return f.apply(r.apply(r)).apply(a);
                    }
                };
            }
        };
        return rec.apply(rec);
    }
    */
}