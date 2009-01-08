package com.agical.jambda;

import static com.agical.jambda.Sequence.*;

import java.util.Arrays;

import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;
import com.agical.jambda.Tuples.Tuple2;

public class Tree {

    public abstract static class AbstractNode<V> {
        Option<Iterable<AbstractNode<V>>> children1 = Option.<Iterable<AbstractNode<V>>>none();
        protected final Fn1<V, V> beforeChildren;
        protected final Fn1<V, V> afterChildren;

        public AbstractNode() {
            beforeChildren = Functions.identity();
            afterChildren = Functions.identity();
        }
        public AbstractNode(Fn1<V, V> beforeChildren, Fn1<V, V> afterChildren) {
            this.beforeChildren = beforeChildren;
            this.afterChildren = afterChildren;
        }

        public AbstractNode<V> add(final AbstractNode<V> child) {
            children1=children1.map(new Fn1<Iterable<AbstractNode<V>>, Option<Iterable<AbstractNode<V>>>>() {
                public Option<Iterable<AbstractNode<V>>> apply(Iterable<AbstractNode<V>> current) {
                    return Option.some(concat(current, child));
                }}
                , new Fn0<Option<Iterable<AbstractNode<V>>>>() {
                    public Option<Iterable<AbstractNode<V>>> apply() {
                        return Option.some(Sequence.createSequence(child));
                    }
                    
                });
            return child;
        }
        
        /**
         * Call this method with your visitor of choice. The method will
         * call <code>accept(..)</code> and then <code>traverse(..)</code>
         * on all its children.
         * 
         * If you have provided <code>beforeChildren</code> and <code>afterChildren</code> functions,
         * those will be called accordingly.
         * 
         * @param visitor Your visitor of choice
         * @return
         */
        public V traverse(final V visitor) {
            V accept = accept(visitor);
            
            return children1.map(new Fn2<Iterable<AbstractNode<V>>, V, V>(){
                public V apply(Iterable<AbstractNode<V>> arg, V v) {
                    return afterChildren.apply(foldLeft(arg, new Fn2<AbstractNode<V>, V, V>() {
                        public V apply(AbstractNode<V> node, V localVisitor) {
                            return node.traverse(localVisitor);
                        }
                    }, beforeChildren.apply(v)));
                }
                
            }.rightCurry(accept), Functions.<V>constantly(accept));
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

        public Node(T data, Fn2<T, V, V> onVisit, Fn1<V, V> beforeChildren, Fn1<V, V> afterChildren) {
            super(beforeChildren, afterChildren);
            this.data = data;
            this.onVisit = onVisit;
        }

        /**
         * Use this constructor to create new nodes if you are not interested in 
         * 
         * @param data The data of the node
         * @param onVisit A function that executes on visits
         */
        public Node(T data, Fn2<T, V, V> onVisit) {
            super();
            this.data = data;
            this.onVisit = onVisit;
        }
        
        public V accept(V visitor) {
            return onVisit.apply(data, visitor);
        }

        /**
         * This method will add the provided node to the current and return the current.
         * @param <TC> The data type of the provided node
         * @param node The provided node to add
         * @return this current node
         */
        public <TC> Node<T,V> add(Node<TC,V> node) {
            return addAndGetTuple(node).getFirst();
        }
        /**
         * This method will add the provided node to the current and return the provided.
         * @param <TC> The data type of the provided node
         * @param node The provided node to add
         * @return the provided node
         */
        public <TC> Node<TC,V> addAndDescend(Node<TC,V> node) {
            return addAndGetTuple(node).getSecond();
        }
        /**
         * This method will create a new node, add it to this node and return this node. 
         * 
         * <code>beforeChildren</code> and <code>afterChildren</code> functions are inherited from the parent node.
         * 
         * @param <TC> The data type of the created node
         * @param childData The data of the created node
         * @param onVisit The visit function
         * @return This current node
         */
        public <TC> Node<T,V> add(TC childData,Fn2<TC, V, V> onVisit) {
            return add(create(childData, onVisit));
        }
        /**
         * This method will create a new node, add it to this node and return the new node
         * 
         * <code>beforeChildren</code> and <code>afterChildren</code> functions are inherited from the parent node.
         * 
         * @param <TC> The data type of the created node
         * @param childData The data of the created node
         * @param onVisit The visit function
         * @return the new node
         */
        public <TC> Node<TC,V> addAndDescend(TC childData,Fn2<TC, V, V> onVisit) {
            return addAndDescend(create(childData, onVisit));
        }

        private <TC> Tuple2<Node<T,V>, Node<TC,V>> addAndGetTuple(Node<TC,V> node) {
            super.add(node);
            return Tuples.duo(this,node);
        }

        /**
         * Create a new node using <code>beforeChildren</code> and <code>afterChildren</code> 
         * from the current node.
         * @param <TC>
         * @param data
         * @param onVisit
         * @return 
         */
        public <TC> Node<TC,V> create(TC data, Fn2<TC, V, V> onVisit) {
            return create(data, onVisit, beforeChildren, afterChildren);
        }

        /**
         * Create a new node from scratch.
         * @param <TC>
         * @param <V>
         * @param data
         * @param onVisit
         * @param beforeChildren
         * @param afterChildren
         * @return
         */
        public static <TC,V> Node<TC,V> create(TC data, Fn2<TC, V, V> onVisit, Fn1<V, V> beforeChildren, Fn1<V, V> afterChildren) {
            return new Node<TC, V>(data, onVisit, beforeChildren, afterChildren);
        }
    }
    
}
