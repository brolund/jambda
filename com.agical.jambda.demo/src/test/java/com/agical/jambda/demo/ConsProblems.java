package com.agical.jambda.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.agical.jambda.Cons;


public class ConsProblems {
    @Test
    public void shouldWorkIfOriginalSolutionWasCorrect() throws Exception {
        /*!*/
        List<String> userNames = new ArrayList<String>();
        userNames.add("Daniel");
        userNames.add("Johan");
        userNames.add("Joakim");
        
        Iterable<User> users = Cons.map(userNames, DemoFunctions.userCreator());
        Iterator<User> iterator = users.iterator();
        assertEquals("Joakim", iterator.next().getName());
        assertEquals("Johan", iterator.next().getName());
        assertEquals("Daniel", iterator.next().getName());
        /*!*/
    }

    @Test
    public void shouldWorkIfOriginalSolutionWasIncorrect() throws Exception {
        /*!*/
        List<String> userNames = new ArrayList<String>();
        userNames.add("Daniel");
        userNames.add("Johan");
        userNames.add("Joakim");
        
        Iterable<User> users = Cons.map(userNames, DemoFunctions.userCreator());
        Iterator<User> iterator = users.iterator();
        assertEquals("Daniel", iterator.next().getName());
        assertEquals("Johan", iterator.next().getName());
        assertEquals("Joakim", iterator.next().getName());
        /*!*/
    }
}
