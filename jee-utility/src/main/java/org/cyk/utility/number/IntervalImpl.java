package org.cyk.utility.number;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class IntervalImpl extends AbstractObject implements Interval,Serializable {
	private static final long serialVersionUID = 1L;

	private IntervalExtremity low,high;
	
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
	public <T extends Number> T getHighValueAs(Class<T> aClass) {
		return high == null ? null : high.getPreparedValueAs(aClass);
	}

	/**/
	
	public static final String FIELD_LOW = "low";
	public static final String FIELD_HIGH = "high";
}
