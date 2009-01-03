package com.agical.jambda;

import com.agical.jambda.Functions.Fn2;

public class String {
	
	public static Fn2<java.lang.String, java.lang.String, java.lang.String> concatenate
		= new Fn2<java.lang.String, java.lang.String, java.lang.String>() {
			public java.lang.String apply(java.lang.String arg1, java.lang.String arg2) {
				return arg1 + arg2;
			}
		};
}
