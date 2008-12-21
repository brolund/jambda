package com.agical.jambda.demo;

import com.agical.jambda.Functions.Fn1;

public final class PersonalGreeting extends Fn1<String, String> {
    @Override
    public String apply(String name) {
        return "Hello, " + name + "!";
    }
}