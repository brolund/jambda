package com.agical.jambda;

import com.agical.jambda.Functions.*;

public class Booleans {
    
    public static Fn2<Boolean, Boolean, Boolean> and = 
        new Fn2<Boolean, Boolean, Boolean>() {
            public Boolean apply(Boolean arg1, Boolean arg2) {
                return arg1 && arg2;
            }
        };
        
    public static Fn2<Boolean, Boolean, Boolean> or = 
        new Fn2<Boolean, Boolean, Boolean>() {
            public Boolean apply(Boolean arg1, Boolean arg2) {
                return arg1 || arg2;
            }
        };

    public static Fn1<Boolean, Boolean> not = 
        new Fn1<Boolean, Boolean>() {
            public Boolean apply(Boolean arg) {
                return !arg;
            }
        };
}
