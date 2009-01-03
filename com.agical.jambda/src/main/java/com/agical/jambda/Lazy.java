package com.agical.jambda;

import com.agical.jambda.Functions.Fn0;

/**
 * A Lazy container that encapsulates a value that is calculated on demand.
 * @author kullbom
 *
 * @param <T> The type of the encapsulated value.
 */
public class Lazy<T> {
	private final Fn0<T> forceFn;
	// ToDo: Make threadsafe - mutable state...
	private Option<T> value = Option.none();
	
	public Lazy(final Fn0<T> contentFn) {
		final Lazy<T> self = this;
		this.forceFn = 
			new Fn0<T>() {
				public T apply() {
					T value = contentFn.apply();
					self.value = Option.some(value);
					return value;
				}
		};
	}
	
	/**
	 * Forces the value of the Lazy to be calculated (and cached) 
	 * @return The cached or newly calculated value
	 */
	public T force() {
		return this.value.escape(forceFn);
	}
	
	/**
	 * Resets the Lazy to unforced state. The value will be recalculated at next force.
	 * @return Unit
	 */
	public Unit reset() {
		this.value = Option.none();
		return Unit.unit;
	}
}
