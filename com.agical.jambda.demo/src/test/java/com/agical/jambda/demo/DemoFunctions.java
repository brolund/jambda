package com.agical.jambda.demo;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;


public class DemoFunctions {

    public static Fn0<String> anonymousGreeting() {
        return new Fn0<String>() {
            public String apply() {
                return "Hello!";
            }
        };
    }
    

    public static Fn1<Greeting, String> greetingToString() {
        return new Fn1<Greeting, String>() {
            public String apply(Greeting greeting) {
                return greeting.getGreeting();
            }
        };
    }

    public static Fn1<String, String> personalGreeting() {
        return new Fn1<String, String>() {
            public String apply(String name) {
                return "Hello, " + name + "!";
            }
        };
    }

    public static Fn1<User, Greeting> typedPersonalGreeting() {
        return new Fn1<User, Greeting>() {
            public Greeting apply(User user) {
                return new Greeting("Hello, " + user.getName() + "!");
            }
        };
    }

    public static Fn0<Greeting> typedAnonymousGreeting() {
        return new Fn0<Greeting>() {
            public Greeting apply() {
                return new Greeting("Hello!");
            }
        };
    }

    public static Fn1<String, User> userCreator() {
        return new Fn1<String, User>() {
            public User apply(String name) {
                return new User(name);
            }
        };
    }
    
}
