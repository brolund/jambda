package com.agical.jambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Strings {
	
	public static Fn2<String, String, String> concatenate = 
	    new Fn2<String, String, String>() {
		    public String apply(String arg1, String arg2) {
		        return arg1 + arg2;
			}
		};
		
	public static Iterable<Option<Character>> eachChar(final CharSequence string) {
	    
	    return new Iterable<Option<Character>>() {
            public Iterator<Option<Character>> iterator() {
                return new Iterator<Option<Character>>() {
                    int index = 0;
                    public boolean hasNext() {
                        return string.length()>index;
                    }
                    public Option<Character> next() {
                        return hasNext()?Option.some(new Character(string.charAt(index))):Option.<Character>none();
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
	    };
	}
    public static Iterable<Option<String>> eachLine(final String string) {
        return new Iterable<Option<String>>() {
            public Iterator<Option<String>> iterator() {
                return new Iterator<Option<String>>() {
                    int index = 0;
                    public boolean hasNext() {
                        return index>string.length();
                    }
                    public Option<String> next() {
                        int newIndex = string.indexOf("\n", index);
                        if(newIndex==-1) return Option.<String>none();
                        String result = string.substring(index,newIndex);
                        index = newIndex;
                        return Option.some(result);
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
