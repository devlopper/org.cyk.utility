package org.cyk.utility.__kernel__.test.weld;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractWeldUnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected final void execute(String name,Integer numberOfCalls,Integer expectedMaximumNumberOfMillisecond,Runnable runnable) {
		Long t1 = System.currentTimeMillis();
		__start__(t1);
		for(Integer index = 0 ; index < numberOfCalls ; index = index + 1)
			runnable.run();
		Long t2 = System.currentTimeMillis();
		Long duration = t2 - t1;
		__end__(t2,duration);
		System.out.println(String.format("%s , #calls=%s,duration=%s",name,numberOfCalls, __formatDuration__(duration)));
		assertThat(duration).isLessThanOrEqualTo(expectedMaximumNumberOfMillisecond);
	}
	
	protected void __start__(Long timestamp) {}
	protected void __end__(Long timestamp,Long duration) {}
	
	protected String __formatDuration__(Long duration) {
		return String.valueOf(duration);
	}
}
