package com.agical.jambda;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.agical.jambda.Functions.*;

import static com.agical.jambda.Numeric.*;
import static com.agical.jambda.Sequence.*;

public abstract class Numeric<T> {
    public abstract T zero();
    public abstract T one();
    public abstract T fromInt(Integer x);
    
    public abstract T plus(T x1, T x2);
	public abstract T minus(T x1, T x2);
	public abstract T multiply(T x1, T x2);
	public abstract T divide(T x1, T x2);
	public abstract T modulo(T x, T m);
    
	public abstract Boolean equalTo(T x1, T x2);
	public abstract Boolean greaterThan(T x1, T x2);
    public abstract Boolean smallerThan(T x1, T x2);
    
    public static <T> Fn2<T, T, T> plus(final Numeric<T> numType) {
        return new Fn2<T, T, T>() {
            public T apply(T x1, T x2) { return numType.plus(x1, x2); }
        };
    }
    
    public static <T> Fn2<T, T, T> multiply(final Numeric<T> numType) {
        return new Fn2<T, T, T>() {
            public T apply(T x1, T x2) { return numType.multiply(x1, x2); }
        };
    }
        
    public static <T> Fn2<T, T, T> modulo(final Numeric<T> numType) {
        return new Fn2<T, T, T>() {
            public T apply(T x, T m) { return numType.modulo(x, m); }
        };
    }
        
    public static <T> Fn2<T, T, Boolean> equalTo(final Numeric<T> numType) {
        return new Fn2<T, T, Boolean>() {
             public Boolean apply(T x1, T x2) { return numType.equalTo(x1, x2); }
        };
    }
            
    public static <T> Fn2<T, T, Boolean> greaterThan(final Numeric<T> numType) {
        return new Fn2<T, T, Boolean>() {
             public Boolean apply(T x1, T x2) { return numType.greaterThan(x1, x2); }
        };
    }
    
    public static <T> Fn2<T, T, Boolean> smallerThan(final Numeric<T> numType) {
        return new Fn2<T, T, Boolean>() {
             public Boolean apply(T x1, T x2) { return numType.smallerThan(x1, x2); }
        };
    }
    
    public static Integer sum(Iterable<Integer> source) {
        return sum(integerType, source);
    }
    
    public static Integer product(Iterable<Integer> source) {
        return product(integerType, source);
    }
    
    public static Integer average(Iterable<Integer> source) {
        return average(integerType, source);
    }
        
    public static <T, TNum extends Numeric<T>> T sum(TNum numType, Iterable<T> source) {
        return foldLeft(source, plus(numType), numType.zero());
    }
    
    public static <T, TNum extends Numeric<T>> T product(TNum numType, Iterable<T> source) {
        return foldLeft(source, multiply(numType), numType.one());
    }
    
    public static <T, TNum extends Numeric<T>> T average(TNum numType, Iterable<T> source) {
        return numType.divide(sum(numType, source), numType.fromInt(count(source)));
    }
    
    
    public static final IntegerType integerType = new IntegerType();
    
    public static class IntegerType extends Numeric<Integer>{
	    public Integer zero() { return 0; }
	    public Integer one() { return 1; }

        public Integer fromInt(Integer x) { return x; }

        public Integer plus(Integer x1, Integer x2) { return x1 + x2; }
        public Integer minus(Integer x1, Integer x2) { return x1 - x2; }
        public Integer multiply(Integer x1, Integer x2) { return x1 * x2; }
        public Integer divide(Integer x1, Integer x2) { return x1 / x2; }

        public Integer modulo(Integer x, Integer m) { return x % m; }
        
        public Boolean equalTo(Integer x1, Integer x2) { return x1 == x2; }
        public Boolean greaterThan(Integer x1, Integer x2) { return x1 > x2; }
        public Boolean smallerThan(Integer x1, Integer x2) { return x1 < x2; }
	}
    
    public static final LongType longType = new LongType();
    
    public static class LongType extends Numeric<Long>{
        public Long zero() { return 0l; }
        public Long one() { return 1l; }

        public Long fromInt(Integer x) { return new Long(x); }

        public Long plus(Long x1, Long x2) { return x1 + x2; }
        public Long minus(Long x1, Long x2) { return x1 - x2; }
        public Long multiply(Long x1, Long x2) { return x1 * x2; }
        public Long divide(Long x1, Long x2) { return x1 / x2; }

        public Long modulo(Long x, Long m) { return x % m; }
        
        public Boolean equalTo(Long x1, Long x2) { return x1 == x2; }
        public Boolean greaterThan(Long x1, Long x2) { return x1 > x2; }
        public Boolean smallerThan(Long x1, Long x2) { return x1 < x2; }
    }
	
    public static final FloatType floatType = new FloatType();

    public static class FloatType extends Numeric<Float>{
        public Float zero() { return 0f; }
        public Float one() { return 1f; }
        
        public Float fromInt(Integer x) { return new Float(x); }
        
        public Float plus(Float x1, Float x2) { return x1 + x2; }
        public Float minus(Float x1, Float x2) { return x1 - x2; }
        public Float multiply(Float x1, Float x2) { return x1 * x2; }
        public Float divide(Float x1, Float x2) { return x1 / x2; }

        public Float modulo(Float x, Float m) { return x % m; }
        
        public Boolean equalTo(Float x1, Float x2) { return x1 == x2; }
        public Boolean greaterThan(Float x1, Float x2) { return x1 > x2; }
        public Boolean smallerThan(Float x1, Float x2) { return x1 < x2; }
    }
    
    public static final DoubleType doubleType = new DoubleType();

	public static class DoubleType extends Numeric<Double>{
	    public Double zero() { return 0d; }
        public Double one() { return 1d; }
        
        public Double fromInt(Integer x) { return new Double(x); }
        
        public Double plus(Double x1, Double x2) { return x1 + x2; }
        public Double minus(Double x1, Double x2) { return x1 - x2; }
        public Double multiply(Double x1, Double x2) { return x1 * x2; }
        public Double divide(Double x1, Double x2) { return x1 / x2; }

        public Double modulo(Double x, Double m) { return x % m; }
        
        public Boolean equalTo(Double x1, Double x2) { return x1 == x2; }
        public Boolean greaterThan(Double x1, Double x2) { return x1 > x2; }
        public Boolean smallerThan(Double x1, Double x2) { return x1 < x2; }
    }

    public static final BigIntegerType bigIntegerType = new BigIntegerType();

    public static class BigIntegerType extends Numeric<BigInteger>{
        public BigInteger zero() { return BigInteger.ZERO; }
        public BigInteger one() { return BigInteger.ONE; }
        
        // This can not be the best way...
        public BigInteger fromInt(Integer x) { return new BigInteger(x.toString()); }
        
        public BigInteger plus(BigInteger x1, BigInteger x2) { return x1.add(x2); }
        public BigInteger minus(BigInteger x1, BigInteger x2) { return x1.subtract(x2); }
        public BigInteger multiply(BigInteger x1, BigInteger x2) { return x1.multiply(x2); }
        public BigInteger divide(BigInteger x1, BigInteger x2) { return x1.divide(x2); }

        public BigInteger modulo(BigInteger x, BigInteger m) { return x.mod(m); }

        public Boolean equalTo(BigInteger x1, BigInteger x2) { return x1.compareTo(x2) == 0; }
        public Boolean greaterThan(BigInteger x1, BigInteger x2) { return x1.compareTo(x2) == 1; }
        public Boolean smallerThan(BigInteger x1, BigInteger x2) { return x1.compareTo(x2) == -1; }
    }
    
    public static final BigDecimalType bigDecimalType = new BigDecimalType();

    public static class BigDecimalType extends Numeric<BigDecimal>{
        public BigDecimal zero() { return BigDecimal.ZERO; }
        public BigDecimal one() { return BigDecimal.ONE; }
        
        // This can not be the best way...
        public BigDecimal fromInt(Integer x) { return new BigDecimal(x.toString()); }
        
        public BigDecimal plus(BigDecimal x1, BigDecimal x2) { return x1.add(x2); }
        public BigDecimal minus(BigDecimal x1, BigDecimal x2) { return x1.subtract(x2); }
        public BigDecimal multiply(BigDecimal x1, BigDecimal x2) { return x1.multiply(x2); }
        public BigDecimal divide(BigDecimal x1, BigDecimal x2) { return x1.divide(x2); }

        public BigDecimal modulo(BigDecimal x, BigDecimal m) { 
            throw new UnsupportedOperationException("BigDecimals does not support modulo."); 
        }

        public Boolean equalTo(BigDecimal x1, BigDecimal x2) { return x1.compareTo(x2) == 0; }
        public Boolean greaterThan(BigDecimal x1, BigDecimal x2) { return x1.compareTo(x2) == 1; }
        public Boolean smallerThan(BigDecimal x1, BigDecimal x2) { return x1.compareTo(x2) == -1; }
    }

    public static final Iterable<Integer> naturalNumbers = Sequence.range(plus(integerType).apply(1), 0);
}
