package org.cyk.utility.common.test;

public abstract class Runnable extends BasedRunnable<TestCase> implements java.lang.Runnable {
	private static final long serialVersionUID = 1L;

	public Runnable(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public Runnable() {
		this(null);
	}
	
}