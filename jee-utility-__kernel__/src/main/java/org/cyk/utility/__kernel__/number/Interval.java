package org.cyk.utility.__kernel__.number;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Interval extends Objectable {

	IntervalExtremity getLow();
	IntervalExtremity getLow(Boolean injectIfNull);
	Interval setLow(IntervalExtremity low);
	Interval setLow(Number value,Boolean isExcluded);
	Interval setLow(Number value);
	<T extends Number> T getLowValueAs(Class<T> aClass);
	
	IntervalExtremity getHigh();
	IntervalExtremity getHigh(Boolean injectIfNull);
	Interval setHigh(IntervalExtremity high);
	Interval setHigh(Number value,Boolean isExcluded);
	Interval setHigh(Number value);
	<T extends Number> T getHighValueAs(Class<T> aClass);
	
	Boolean contains(Number number);
}
