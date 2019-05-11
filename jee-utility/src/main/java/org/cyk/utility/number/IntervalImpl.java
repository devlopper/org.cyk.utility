package org.cyk.utility.number;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class IntervalImpl extends AbstractObject implements Interval,Serializable {
	private static final long serialVersionUID = 1L;

	private IntervalExtremity low,high;
	
	@Override
	public Boolean contains(Number number) {
		Boolean contains = null;
		IntervalExtremity low = getLow();
		IntervalExtremity high = getHigh();
		if(low == null)
			if(high == null)
				contains = Boolean.TRUE;
			else
				contains = __inject__(NumberHelper.class).compare(number, high.getPreparedValueAs(Double.class), ComparisonOperator.LTE);
		else
			if(high == null)
				contains = __inject__(NumberHelper.class).compare(number, low.getPreparedValueAs(Double.class), ComparisonOperator.GTE);
			else
				contains = __inject__(NumberHelper.class).compare(number, low.getPreparedValueAs(Double.class), ComparisonOperator.GTE) && 
				__inject__(NumberHelper.class).compare(number, high.getPreparedValueAs(Double.class), ComparisonOperator.LTE);
		return contains;
	}
	
	@Override
	public IntervalExtremity getLow() {
		return low;
	}
	
	@Override
	public IntervalExtremity getLow(Boolean injectIfNull) {
		return (IntervalExtremity) __getInjectIfNull__(FIELD_LOW, injectIfNull);
	}

	@Override
	public Interval setLow(IntervalExtremity low) {
		this.low = low;
		return this;
	}
	
	@Override
	public <T extends Number> T getLowValueAs(Class<T> aClass) {
		return low == null ? null : low.getPreparedValueAs(aClass);
	}
	
	@Override
	public Interval setLow(Number value, Boolean isExcluded) {
		getLow(Boolean.TRUE).setValue(value).setIsExcluded(isExcluded);
		return this;
	}
	
	@Override
	public Interval setLow(Number value) {
		setLow(value,Boolean.FALSE);
		return this;
	}

	@Override
	public IntervalExtremity getHigh() {
		return high;
	}
	
	@Override
	public IntervalExtremity getHigh(Boolean injectIfNull) {
		return (IntervalExtremity) __getInjectIfNull__(FIELD_HIGH, injectIfNull);
	}

	@Override
	public Interval setHigh(IntervalExtremity high) {
		this.high = high;
		return this;
	}
	
	@Override
	public Interval setHigh(Number value, Boolean isExcluded) {
		getHigh(Boolean.TRUE).setValue(value).setIsExcluded(isExcluded);
		return this;
	}
	
	@Override
	public Interval setHigh(Number value) {
		setHigh(value,Boolean.FALSE);
		return this;
	}
	
	@Override
	public <T extends Number> T getHighValueAs(Class<T> aClass) {
		return high == null ? null : high.getPreparedValueAs(aClass);
	}

	@Override
	public String toString() {
		return (low == null || Boolean.TRUE.equals(low.getIsExcluded()) ? "]" : "[")
			+ (low == null ? ConstantString.INFINITE : low.toString())
			+";"
			+ (high == null ? ConstantString.INFINITE : high.toString())
			+ (high == null || Boolean.TRUE.equals(high.getIsExcluded()) ? "[" : "]")
			;
	}
	
	/**/
	
	public static final String FIELD_LOW = "low";
	public static final String FIELD_HIGH = "high";
}
