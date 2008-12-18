package com.agical.jfunc.scala

import com.agical.jfunc.Functions._

object JavaFunctions {
  implicit def scalaFn0ToJavaFn0[R](f:Function0[R]):Fn0[R] = new Fn0[R] {
    def apply():R = f.apply
  }
  
  implicit def scalaFn1ToJavaFn1[T, R](f:Function1[T, R]):Fn1[T,R] = new Fn1[T,R] {
    def apply(a:T):R = f.apply(a)
  }
}
