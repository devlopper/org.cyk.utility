package org.cyk.utility.internationalization;

import org.cyk.utility.test.weld.AbstractWeldUnitTestBenchmark;
import org.junit.jupiter.api.Test;

public class InternalizationKeyStringBuilderUnitTestPerformance extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Test
	public void none(){
		execute(new Jobs().setName("Build key none").setNumberOfRound(1000000)
				.add("InternalizationKeyStringBuilder",new Runnable() {
				@Override
				public void run() {
					__inject__(InternalizationKeyStringBuilder.class).execute();
				}
			}).add("InternalizationHelperImpl.__buildKey__", new Runnable() {
				@Override
				public void run() {
					InternalizationHelperImpl.__buildInternalizationKey__(null, null);
				}
			})
				);
	}
	
	@Test
	public void empty(){
		assertionHelper.assertEquals(null, __inject__(InternalizationKeyStringBuilder.class).setValue("").execute().getOutput());
		execute(new Jobs().setName("Build key empty").setNumberOfRound(1000000)
				.add("InternalizationKeyStringBuilder",new Runnable() {
				@Override
				public void run() {
					 __inject__(InternalizationKeyStringBuilder.class).setValue("").execute();
				}
			}).add("InternalizationHelperImpl.__buildKey__", new Runnable() {
				@Override
				public void run() {
					InternalizationHelperImpl.__buildInternalizationKey__("", null);
				}
			})
				);
	}
	
	@Test
	public void hi(){
		execute(new Jobs().setName("Build key hi").setNumberOfRound(1000000)
				.add("InternalizationKeyStringBuilder",new Runnable() {
				@Override
				public void run() {
					 __inject__(InternalizationKeyStringBuilder.class).setValue("hi").execute();
				}
			}).add("InternalizationHelperImpl.__buildKey__", new Runnable() {
				@Override
				public void run() {
					InternalizationHelperImpl.__buildInternalizationKey__("hi", null);
				}
			})
				);
	}
	
}
