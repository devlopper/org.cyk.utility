package org.cyk.utility.test;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractTestImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements Test,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected AssertionHelper assertionHelper;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsCatchThrowable(Boolean.TRUE);
	}
		
	@Override
	public Test setup() {
		return this;
	}

	@Override
	public Test clean() {
		return this;
	}
	
	@Override
	public Throwable getThrowable() {
		return (Throwable) getProperties().getThrowable();
	}
	
	@Override
	public Test assertThrowableCauseIsInstanceOf(Class<?> aClass) {
		assertionHelper.assertTrue(__inject__(ThrowableHelper.class).getInstanceOf(getThrowable(), aClass) != null);
		return this;
	}
	
	@Override
	public Test assertThrowableIsNull() {
		assertionHelper.assertTrue(getThrowable() == null);
		return this;
	}
}
