package com.agical.jfunc.scala



object JavaIterators {
  implicit def IterableToScalaIterable[A](iterable:java.lang.Iterable[A]):Iterable[A] = new Iterable[A] {
    def elements:Iterator[A] = iterable.iterator 
  }
  
  implicit def IteratoreToScalaIterator[A](iterator:java.util.Iterator[A]):Iterator[A] = new Iterator[A] {
    def hasNext = iterator.hasNext
    def next = iterator.next
  }
  
  implicit def ScalaIteratorToIterator[A](iterator:Iterator[A]):java.util.Iterator[A] = new java.util.Iterator[A]{
    def hasNext:Boolean = iterator.hasNext
    def next:A = iterator.next
    def remove = false
  }
}
