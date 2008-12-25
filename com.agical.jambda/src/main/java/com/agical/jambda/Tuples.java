package com.agical.jambda;

public class Tuples {
    
    public static <T1, T2> Tuple2<T1, T2> two(T1 t1, T2 t2) {
        return new Tuple2<T1, T2> (t1, t2);
    };
    
    public static <T1, T2, T3> Tuple3<T1, T2, T3> three(T1 t1, T2 t2, T3 t3 ) {
        return new Tuple3<T1, T2, T3> (t1, t2, t3);
    };
    
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> four(T1 t1, T2 t2, T3 t3, T4 t4 ) {
        return new Tuple4<T1, T2, T3, T4> (t1, t2, t3, t4);
    };
    
    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> five(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<T1, T2, T3, T4, T5> (t1, t2, t3, t4, t5);
    };
    
    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> six(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6> (t1, t2, t3, t4, t5, t6);
    };
    
	public static class Tuple2<T1, T2> {
		private final T1 first;
		private final T2 second;
		
		private Tuple2(T1 first, T2 second){
			this.first = first;
			this.second = second;
		}

		public T1 getFirst() {
			return this.first;
		}

		public T2 getSecond() {
			return this.second;
		}
	}
		
	
	public static class Tuple3<T1, T2, T3> {
		private final T1 first;
		private final T2 second;
		private final T3 third;
		
		private Tuple3(T1 first, T2 second, T3 third){
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public T1 getFirst() {
			return this.first;
		}

		public T2 getSecond() {
			return this.second;
		}

		public T3 getThird() {
			return this.third;
		}
	}
	
	public static class Tuple4<T1, T2, T3, T4> {
		private final T1 first;
		private final T2 second;
		private final T3 third;
		private final T4 forth;
		
		public Tuple4(T1 first, T2 second, T3 third, T4 forth){
			this.first = first;
			this.second = second;
			this.third = third;
			this.forth = forth;
		}

		public T1 getFirst() {
			return this.first;
		}

		public T2 getSecond() {
			return this.second;
		}

		public T3 getThird() {
			return this.third;
		}
		
		public T4 getForth() {
			return this.forth;
		}
	}
	
	public static class Tuple5<T1, T2, T3, T4, T5> {
		private final T1 first;
		private final T2 second;
		private final T3 third;
		private final T4 forth;
		private final T5 fifth;
		
		public Tuple5(T1 first, T2 second, T3 third, T4 forth, T5 fifth){
			this.first = first;
			this.second = second;
			this.third = third;
			this.forth = forth;
			this.fifth = fifth;
		}

		public T1 getFirst() {
			return this.first;
		}

		public T2 getSecond() {
			return this.second;
		}

		public T3 getThird() {
			return this.third;
		}
		
		public T4 getForth() {
			return this.forth;
		}
		
		public T5 getFifth() {
			return this.fifth;
		}
	}
	
	public static class Tuple6<T1, T2, T3, T4, T5, T6> {
		private final T1 first;
		private final T2 second;
		private final T3 third;
		private final T4 forth;
		private final T5 fifth;
		private final T6 sixth;
		
		public Tuple6(T1 first, T2 second, T3 third, T4 forth, T5 fifth, T6 sixth){
			this.first = first;
			this.second = second;
			this.third = third;
			this.forth = forth;
			this.fifth = fifth;
			this.sixth = sixth;
		}

		public T1 getFirst() {
			return this.first;
		}

		public T2 getSecond() {
			return this.second;
		}

		public T3 getThird() {
			return this.third;
		}
		
		public T4 getForth() {
			return this.forth;
		}
		
		public T5 getFifth() {
			return this.fifth;
		}
		
		public T6 getSixth() {
			return this.sixth;
		}
	}
}
