package com.agical.jambda;

import static com.agical.jambda.Sequence.concat;
import static com.agical.jambda.Sequence.foldLeft;

import com.agical.jambda.Functions.Fn2;

public class Tree {

    public abstract static class AbstractNode<V> {
        Iterable<AbstractNode<V>> children = Sequence.<AbstractNode<V>>empty();
        
        public Iterable<AbstractNode<V>> add(AbstractNode<V>... newChildren) {
            return children = concat(children, newChildren);
        }
        public V traverse(final V visitor) {
            return foldLeft(children, new Fn2<AbstractNode<V>, V, V>() {
                public V apply(AbstractNode<V> node, V localVisitor) {
                    return node.traverse(localVisitor);
                }
            }, accept(visitor));
        }
        public abstract V accept(V visitor);
    }

    public static class Node<T, V> extends AbstractNode<V> {
        private final T data;
        private final Fn2<T, V, V> onVisit;
    
        public Node(T i, Fn2<T, V, V> onVisit) {
            this.data = i;
            this.onVisit = onVisit;
        }
        public V accept(V visitor) {
            return onVisit.apply(data, visitor);
        }
    }
    
}
