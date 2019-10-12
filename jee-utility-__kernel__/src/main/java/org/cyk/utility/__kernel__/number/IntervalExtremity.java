package org.cyk.utility.__kernel__.number;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface IntervalExtremity extends Objectable {

	Number getValue();
	IntervalExtremity setValue(Number value);
	
	Boolean getIsExcluded();
	IntervalExtremity setIsExcluded(Boolean isExcluded);
	
	<T extends Number> T getPreparedValueAs(Class<T> aClass);
}
