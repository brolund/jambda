package com.agical.jambda.demo;


import org.junit.Test;

import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tree.NodeInstance;


public class MakingAGenericTree {
    
    @Test
    public void AGenericTree() throws Exception {
        /*!
        This is how to make a generic tree. The only thing is that the type of the 
        visitor must be decided when creating the nodes.
        */
        Visitor visitor = new Visitor();
        Fn2<Integer, Visitor, Visitor> onVisitInteger = new Fn2<Integer, Visitor, Visitor>() {
            public Visitor apply(Integer arg1, Visitor arg2) {
                return arg2.visitInteger(arg1);
            }
        };
        Fn2<String, Visitor, Visitor> onVisitString = new Fn2<String, Visitor, Visitor>() {
            public Visitor apply(String arg1, Visitor arg2) {
                return arg2.visitString(arg1);
            }
        };
        NodeInstance<Integer,Visitor> ni1 = new NodeInstance<Integer,Visitor>(1, onVisitInteger);
        NodeInstance<Integer,Visitor> ni2 = new NodeInstance<Integer,Visitor>(2, onVisitInteger);
        NodeInstance<String,Visitor> ns1 = new NodeInstance<String,Visitor>("Daniel", onVisitString);
        NodeInstance<String,Visitor> ns2 = new NodeInstance<String,Visitor>("Johan", onVisitString);
        ni1.add(ns1);
        ni1.add(ni2);
        ni1.add(ns2);
        ni1.traverse(visitor);
        
        ni1.traverse(new Visitor() {
            public Visitor visitString(String s) {
                System.out.println("Number 2: " + s);
                return this;
            }
            public Visitor visitInteger(Integer i) {
                System.out.println("Number 2: " + i);
                return this;
            }
        });
        /*!*/
    }
    
    public class Visitor {
        public Visitor visitString(String s) {
            System.out.println(s);
            return this;
        }
        public Visitor visitInteger(Integer i) {
            System.out.println(i);
            return this;
        }
        
    }
}
