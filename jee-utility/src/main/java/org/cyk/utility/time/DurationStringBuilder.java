package org.cyk.utility.time;

import org.cyk.utility.string.StringFunction;

public interface DurationStringBuilder extends StringFunction {

	Duration getDuration();
	DurationStringBuilder setDuration(Duration duration);
	
}
