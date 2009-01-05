package com.agical.jambda;

import static com.agical.jambda.Sequence.concatenate;
import static com.agical.jambda.Sequence.foldLeft;

import com.agical.jambda.Functions.Fn2;

public class Tree {

    public abstract static class Node<V> {
        Iterable<Node<V>> children = Sequence.<Node<V>>Empty();
        
        public Node<V> add(Node<V> child) {
            children = concatenate(children, child);
            return this;
        }
        public V traverse(final V visitor) {
            accept(visitor);
            return foldLeft(children, new Fn2<Node<V>, V, V>() {
                public V apply(Node<V> node, V localVisitor) {
                    return node.traverse(localVisitor);
                }
            }, visitor);
        }
        public abstract V accept(V visitor);
    }

    public static class NodeInstance<T, V> extends Node<V> {
        private final T data;
        private final Fn2<T, V, V> onVisit;
    
        public NodeInstance(T i, Fn2<T, V, V> onVisit) {
            this.data = i;
            this.onVisit = onVisit;
        }
        public V accept(V visitor) {
            return onVisit.apply(data, visitor);
        }
    }
    
}
