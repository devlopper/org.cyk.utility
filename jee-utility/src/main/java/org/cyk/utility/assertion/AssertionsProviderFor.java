package org.cyk.utility.assertion;

import java.util.Collection;

import org.cyk.utility.object.Objects;

public interface AssertionsProviderFor<T> extends AssertionsProvider {

	Objects getFors();
	Objects getFors(Boolean injectIfNull);
	AssertionsProviderFor<T> setFors(Objects fors);
	AssertionsProviderFor<T> addFors(Collection<Object> fors);
	AssertionsProviderFor<T> addFors(Object...fors);
}
