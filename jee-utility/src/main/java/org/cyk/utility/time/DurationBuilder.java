package org.cyk.utility.time;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface DurationBuilder extends FunctionWithPropertiesAsInput<Duration> {

	Long getBegin();
	DurationBuilder setBegin(Long begin);
	DurationBuilder setBeginToNow();
	
	Long getEnd();
	DurationBuilder setEnd(Long end);
	DurationBuilder setEndNow();
	
	Long getNumberOfMillisecond();
	DurationBuilder setNumberOfMillisecond(Long numberOfMillisecond);
}
