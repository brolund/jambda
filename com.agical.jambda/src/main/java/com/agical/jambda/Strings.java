package com.agical.jambda;

import com.agical.jambda.Functions.*;

public class Strings {
	
	public static Fn2<String, String, String> concatenate = 
	    new Fn2<String, String, String>() {
		    public String apply(String arg1, String arg2) {
		        return arg1 + arg2;
			}
		};
}
