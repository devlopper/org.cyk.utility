package org.cyk.utility.__kernel__.number;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
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
				contains = NumberHelper.compare(number, high.getPreparedValueAs(Double.class), ComparisonOperator.LTE);
		else
			if(high == null)
				contains = NumberHelper.compare(number, low.getPreparedValueAs(Double.class), ComparisonOperator.GTE);
			else
				contains = NumberHelper.compare(number, low.getPreparedValueAs(Double.class), ComparisonOperator.GTE) && 
						NumberHelper.compare(number, high.getPreparedValueAs(Double.class), ComparisonOperator.LTE);
		return contains;
	}
	
	@Override
	public IntervalExtremity getLow() {
		return low;
	}
	
	@Override
	public IntervalExtremity getLow(Boolean injectIfNull) {
		if(low == null && Boolean.TRUE.equals(injectIfNull))
			low = __inject__(IntervalExtremity.class);
		return low;
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
		if(high == null && Boolean.TRUE.equals(injectIfNull))
			high = __inject__(IntervalExtremity.class);
		return high;
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
