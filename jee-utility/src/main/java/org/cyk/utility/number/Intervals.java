package org.cyk.utility.number;

import org.cyk.utility.collection.CollectionInstance;

public interface Intervals extends CollectionInstance<Interval> {

	Intervals add(Number lowValue,Boolean isLowValueExcluded,Number highValue,Boolean isHighValueExcluded);
	Intervals add(Number lowValue,Number highValue);
	
	Boolean contains(Number number);
}
