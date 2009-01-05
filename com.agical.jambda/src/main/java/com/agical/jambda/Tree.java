package com.agical.jambda;

import static com.agical.jambda.Sequence.concat;
import static com.agical.jambda.Sequence.foldLeft;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;

public class Tree {

    public abstract static class AbstractNode<V> {
        Option<Iterable<AbstractNode<V>>> children1 = Option.<Iterable<AbstractNode<V>>>none();
        
        public AbstractNode() {
            
        }

        public Iterable<AbstractNode<V>> add(final AbstractNode<V>... newChildren) {
            children1=children1.map(new Fn1<Iterable<AbstractNode<V>>, Option<Iterable<AbstractNode<V>>>>() {
                public Option<Iterable<AbstractNode<V>>> apply(Iterable<AbstractNode<V>> current) {
                    return Option.some(concat(current, newChildren));
                }}
                , new Fn0<Option<Iterable<AbstractNode<V>>>>() {
                    public Option<Iterable<AbstractNode<V>>> apply() {
                        return Option.some(Sequence.createSequence(newChildren));
                    }
                    
                });
            return children1.map(
                    Functions.<Iterable<AbstractNode<V>>>identity(), 
                    Functions.<Iterable<AbstractNode<V>>>constantly(Sequence.<AbstractNode<V>>empty()));
        }
        
        /**
         * Call this method with your visitor of choice. The method will
         * call <code>accept(..)</code> and then <code>traverse(..)</code>
         * on all its children.
         * 
         * @param visitor Your visitor of choice
         * @return
         */
        public V traverse(final V visitor) {
            V accept = accept(visitor);
            
            V map = children1.map(new Fn2<Iterable<AbstractNode<V>>, V, V>(){
                public V apply(Iterable<AbstractNode<V>> arg, V v) {
                    V foldLeft = foldLeft(arg, new Fn2<AbstractNode<V>, V, V>() {
                        public V apply(AbstractNode<V> node, V localVisitor) {
                            return node.traverse(localVisitor);
                        }
                    }, v);
                    return foldLeft;
                }
                
            }.rightCurry(accept), Functions.<V>constantly(accept));
            
            
            return map;
        }
        public abstract V accept(V visitor);
    }

    /**
     * Use this node to build trees.
     *  
     * @author daniel
     *
     * @param <T> The data type the node holds
     * @param <V> The visitor type to use for traversal. Must be the same for all nodes in the tree.
     */
    public static class Node<T, V> extends AbstractNode<V> {
        private final T data;
        private final Fn2<T, V, V> onVisit;
    
        /**
         * Use this constructor to create new nodes
         * 
         * @param data The data of the node
         * @param onVisit A function that executes on visits
         */
        public Node(T data, Fn2<T, V, V> onVisit) {
            this.data = data;
            this.onVisit = onVisit;
        }
        
        public V accept(V visitor) {
            return onVisit.apply(data, visitor);
        }
    }
    
}
