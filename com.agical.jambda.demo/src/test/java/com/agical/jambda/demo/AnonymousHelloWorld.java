package com.agical.jambda.demo;

import com.agical.jambda.Functions.Fn0;

public final class AnonymousHelloWorld extends Fn0<String> {
    @Override
    public String apply() {
        return "Hello World!";
    }
}