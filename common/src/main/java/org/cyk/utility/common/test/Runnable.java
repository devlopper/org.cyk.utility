package org.cyk.utility.common.test;

public abstract class Runnable implements java.lang.Runnable {

	protected TestCase testCase;
	
	public Runnable(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public Runnable() {
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