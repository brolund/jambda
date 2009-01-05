package com.agical.jambda.demo;


import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.agical.jambda.Sequence;
import com.agical.jambda.Strings;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tree.AbstractNode;
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
        Fn1<Visitor, Visitor> before = new Fn1<Visitor, Visitor>() {
            public Visitor apply(Visitor visitor) {
                return visitor.beforeChildren();
            }
        };
        Fn1<Visitor, Visitor> after = new Fn1<Visitor, Visitor>() {
            public Visitor apply(Visitor visitor) {
                return visitor.afterChildren();
            }
        };
        
        /*!
        These functions should be kept in conjunction with the specific visitor.
         
        Now lets create the nodes: 
        */
        Node<Integer,Visitor> node1 = new Node<Integer,Visitor>(1, onVisitInteger, before, after);
        Node<Integer,Visitor> node2 = new Node<Integer,Visitor>(2, onVisitInteger, before, after);
        Node<String,Visitor> nodeDaniel = new Node<String,Visitor>("Daniel", onVisitString, before, after);
        Node<String,Visitor> nodeJohan = new Node<String,Visitor>("Johan", onVisitString, before, after);
        /*!
        ...and now we build the tree: 
        */
        node1.add(nodeDaniel);
        node1.add(node2);
        nodeDaniel.add(nodeJohan);
        /*!
        Finally, we create a visitor and traverse the created tree: 
        */
        final StringBuffer result = new StringBuffer();
        node1.traverse(new Visitor(){
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
        /*!*/
    }
    
    public interface Visitor {
        public Visitor beforeChildren();
        public Visitor visitString(String s);
        public Visitor visitInteger(Integer i);
        public Visitor afterChildren();
    }
}
