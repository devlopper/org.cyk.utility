package org.cyk.utility.test.weld;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.time.Duration;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

public abstract class AbstractWeldUnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected final void execute(Integer numberOfCalls,Integer expectedMaximumNumberOfMillisecond) {
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		Long t = System.currentTimeMillis();
		for(Integer index = 0 ; index < numberOfCalls ; index = index + 1)
			__execute__();
		t = System.currentTimeMillis() - t;
		Duration duration = durationBuilder.setEndToNow().execute().getOutput();
		System.out.println(String.format("#calls=%s,duration=%s",numberOfCalls, __inject__(DurationStringBuilder.class).setDuration(duration).execute().getOutput()));
		assertThat(t).isLessThanOrEqualTo(expectedMaximumNumberOfMillisecond);
	}
	
	protected abstract void __execute__();
}
