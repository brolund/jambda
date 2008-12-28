package com.agical.jambda.demo;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class DemoFunctions {
    
    // These are functions due to problems parsing the file with QDox (used in Bumblebee)
    
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
    
    public static Fn1<Exception, Greeting> exceptionGreeting() {
        return new Fn1<Exception, Greeting>() {
            public Greeting apply(Exception exception) {
                return new Greeting("There was an Exception: "
                        + exception.getMessage());
            }
        };
    }
    
    public static Fn1<String, Boolean> acceptStringsStartingWithJ() {
        return new Fn1<String, Boolean>() {
            public Boolean apply(String string) {
                return string.indexOf("J")==0;
            }
        };
    }

    public static Fn2<String, String, String> aggregateStrings() {
        return new Fn2<String, String, String>() {
            public String apply(String string, String accumulation) {
                return "(" + accumulation + string + ")";
            }
        };
    }
    
}
