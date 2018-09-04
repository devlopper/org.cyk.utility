package org.cyk.utility.assertion;

public interface AssertionsProviderFor<T> extends AssertionsProvider {

	T getFor();
	AssertionsProviderFor<T> setFor(T value);
	
}
