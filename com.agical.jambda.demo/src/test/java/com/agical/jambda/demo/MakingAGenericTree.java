package com.agical.jambda.demo;


import static org.junit.Assert.*;

import org.junit.Test;

import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tree.Node;


public class MakingAGenericTree {
    
    @Test
    public void ATypesafeGenericTree() throws Exception {
        /*!
        This is how to make a generic tree. The only thing is that the type of the 
        visitor must be decided when creating the nodes, and you need to provide the
        node constructor a function that is called on visits. 
        
        Optionally, as below, you can also provide functions to be called before and after 
        traversal of children if you need tree structure information.
        
        Lets create the functions:
        */
        Fn2<Integer, Visitor, Visitor> onVisitInteger = new Fn2<Integer, Visitor, Visitor>() {
            public Visitor apply(Integer integer, Visitor visitor) {
                return visitor.visitInteger(integer);
            }
        };
        Fn2<String, Visitor, Visitor> onVisitString = new Fn2<String, Visitor, Visitor>() {
            public Visitor apply(String string, Visitor visitor) {
                return visitor.visitString(string);
            }
        };
        Fn1<Visitor, Visitor> beforeChildren = new Fn1<Visitor, Visitor>() {
            public Visitor apply(Visitor visitor) {
                return visitor.beforeChildren();
            }
        };
        Fn1<Visitor, Visitor> afterChildren = new Fn1<Visitor, Visitor>() {
            public Visitor apply(Visitor visitor) {
                return visitor.afterChildren();
            }
        };
        
        /*!
        These functions should be kept in conjunction with the specific visitor.
         
        Now lets build the tree. 
        */
        Node<Integer,Visitor> root = Node.create(1, onVisitInteger, beforeChildren, afterChildren);

        root.add(
                root.create("Daniel", onVisitString)
                    .add("Johan", onVisitString))
            .add(2, onVisitInteger);
        /*!
        Finally, we create a visitor and traverse the created tree: 
        */
        final StringBuffer result = new StringBuffer();
        root.traverse(new Visitor(){
            int level = 1;
            public Visitor visitInteger(Integer i) {
                append(i);
                return this;
            }
            // This is not kosher...
            private void append(Object object) {
                result.append("Level " + level + ": " + object);
            }
            public Visitor visitString(String s) {
                append(s);
                return this;
            }
            public Visitor beforeChildren() {
                level++;
                return this;
            }
            public Visitor afterChildren() {
                level--;
                return this;
            }
        });
        assertEquals(
                "Level 1: 1" +
                "Level 2: Daniel" +
                "Level 3: Johan" +
                "Level 2: 2", result.toString());
        /*!
        */
    }
    
    public interface Visitor {
        public Visitor beforeChildren();
        public Visitor visitString(String s);
        public Visitor visitInteger(Integer i);
        public Visitor afterChildren();
    }
}
