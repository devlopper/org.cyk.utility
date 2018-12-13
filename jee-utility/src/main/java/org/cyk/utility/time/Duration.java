package org.cyk.utility.time;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Duration extends Objectable {

	Short getNumberOfMillisecond();
	Duration setNumberOfMillisecond(Short numberOfMillisecond);
	
	Byte getNumberOfSecond();
	Duration setNumberOfSecond(Byte numberOfSecond);
	
	Byte getNumberOfMinute();
	Duration setNumberOfMinute(Byte numberOfMinute);
}
