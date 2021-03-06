package org.cyk.utility.common.test;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class BasedRunnable<TEST_CASE extends TestCase> extends AbstractBean implements java.lang.Runnable,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected TEST_CASE testCase;
	
	public BasedRunnable(TEST_CASE testCase) {
		this.testCase = testCase;
	}
	
	public BasedRunnable() {
		this(null);
	}
	
	@Override
	public void run() {
		try {
			__run__();
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}
	
	protected abstract void __run__() throws java.lang.Throwable;

	public <T> T instanciateOne(Class<T> aClass) {
		return testCase.instanciateOne(aClass);
	}

	public <T> T instanciateOne(Class<T> aClass, Object identifier) {
		return testCase.instanciateOne(aClass, identifier);
	}

	public <T> T instanciateOneWithRandomIdentifier(Class<T> aClass) {
		return testCase.instanciateOneWithRandomIdentifier(aClass);
	}
	
	/**/
	
	
}