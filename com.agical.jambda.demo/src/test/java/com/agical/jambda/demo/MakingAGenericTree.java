package com.agical.jambda.demo;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tree.Node;


public class MakingAGenericTree {
    
    @Test
    public void ATypesafeGenericTree() throws Exception {
        /*!
        This is how to make a generic tree. The only thing is that the type of the 
        visitor must be decided when creating the nodes, and you need to provide the
        node constructor a function that is called on visits. 
        
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
        /*!
        Now lets create the nodes: 
        */
        Node<Integer,Visitor> node1 = new Node<Integer,Visitor>(1, onVisitInteger);
        Node<Integer,Visitor> node2 = new Node<Integer,Visitor>(2, onVisitInteger);
        Node<String,Visitor> nodeDaniel = new Node<String,Visitor>("Daniel", onVisitString);
        Node<String,Visitor> nodeJohan = new Node<String,Visitor>("Johan", onVisitString);
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
            public Visitor visitInteger(Integer i) {
                result.append(i).append(" ");
                return this;
            }
            public Visitor visitString(String s) {
                result.append(s).append(" ");
                return this;
            }
        });
        assertEquals("1 Daniel Johan 2 ", result.toString());
        /*!*/
    }
    
    public interface Visitor {
        public Visitor visitString(String s);
        public Visitor visitInteger(Integer i);
        
    }
}
