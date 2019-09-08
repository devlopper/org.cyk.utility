package org.cyk.utility.test.weld;

import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

public abstract class AbstractWeldUnitTestPerformance extends org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __formatDuration__(Long duration) {
		return __inject__(DurationStringBuilder.class).setDuration(__inject__(DurationBuilder.class).setNumberOfMillisecond(duration).execute().getOutput()).execute().getOutput();
	}
	
}
