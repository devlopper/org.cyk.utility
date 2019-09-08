package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.object.Objects;

public abstract class AbstractAssertionsProviderForImpl<T> extends AbstractAssertionsProviderImpl implements AssertionsProviderFor<T>,Serializable {
	private static final long serialVersionUID = 1L;

	private Objects fors;
	
	@Override
	protected void ____execute____(Function<?, ?> function,Object filter) {
		Objects fors = __injectValueHelper__().returnOrThrowIfBlank("assertions provider fors", getFors());	
		____execute____(function,filter,(Collection<T>) fors.get());
	}
	
	protected void ____execute____(Function<?, ?> function,Object filter,Collection<T> fors) {
		for(T index : fors)
			____execute____(function, filter, index);
	}
	
	protected abstract void ____execute____(Function<?, ?> function,Object filter,T for_);
	
	@Override
	public Objects getFors() {
		return fors;
	}
	
	@Override
	public AssertionsProviderFor<T> setFors(Objects fors) {
		this.fors = fors;
		return this;
	}
	
	@Override
	public Objects getFors(Boolean injectIfNull) {
		if(fors == null && Boolean.TRUE.equals(injectIfNull))
			fors = DependencyInjection.inject(Objects.class);
		return fors;
	}
	
	@Override
	public AssertionsProviderFor<T> addFors(Collection<Object> fors) {
		getFors(Boolean.TRUE).add(fors);
		return this;
	}
	
	@Override
	public AssertionsProviderFor<T> addFors(Object... fors) {
		getFors(Boolean.TRUE).add(fors);
		return this;
	}
	
	/**/
	
	public static final String FIELD_FORS = "fors";
}
