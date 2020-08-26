package org.cyk.utility.__kernel__.test;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface TestRunner {

	default void run(Test test) {
		if(test.getRunnable() == null) {
			LogHelper.logWarning("no runnable found for test "+test.getName(), TestRunner.class);
			return;
		}
		if(StringHelper.isNotBlank(test.getName()))
			LogHelper.logInfo("Test "+test.getName()+" started", TestRunner.class);
		Long t1 = System.currentTimeMillis();
		test.getRunnable().run();
		test.setDuration(System.currentTimeMillis() - t1);
		if(Boolean.TRUE.equals(test.getLogDuration()))
			LogHelper.logInfo(String.format("duration=%s", test.getDuration()), TestRunner.class);
		if(StringHelper.isNotBlank(test.getName()))
			LogHelper.logInfo("Test "+test.getName()+" done", TestRunner.class);
	}
	
	default void run(String name,Runnable runnable,Class<? extends Throwable> expectedThrowableClass) {
		run(new Test().setName(name).setRunnable(runnable).setExpectedThrowableClass(expectedThrowableClass));
	}
	
	default void run(String name,Runnable runnable) {
		run(new Test().setName(name).setRunnable(runnable));
	}
	
	/**/
	
	static TestRunner getInstance() {
		return Helper.getInstance(TestRunner.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static enum Action {
		CONSTRUCT,EXECUTE
	}
	
	public static enum When {
		BEFORE,NOW,AFTER
	}
}
