package org.cyk.utility.number;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Interval extends Objectable {

	IntervalExtremity getLow();
	IntervalExtremity getLow(Boolean injectIfNull);
	Interval setLow(IntervalExtremity low);
	<T extends Number> T getLowValueAs(Class<T> aClass);
	
	IntervalExtremity getHigh();
	IntervalExtremity getHigh(Boolean injectIfNull);
	Interval setHigh(IntervalExtremity high);
	<T extends Number> T getHighValueAs(Class<T> aClass);
}
