package org.cyk.utility.assertion;

import java.io.Serializable;

public abstract class AbstractAssertionsProviderForImpl<T> extends AbstractAssertionsProviderImpl implements AssertionsProviderFor<T>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____(Object filter) {
		T for_ = getFor();
		____execute____(filter,for_);
	}
	
	protected abstract void ____execute____(Object filter,T for_);
	
	@Override
	public AssertionsProviderFor<T> setFor(T value) {
		getProperties().setFor(value);
		return this;
	}
	
	@Override
	public T getFor() {
		return (T) getProperties().getFor();
	}
}
