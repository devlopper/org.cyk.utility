package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractAssertionsProviderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Assertion>> implements AssertionsProvider, Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<Assertion> __assertions__;
	private Function<?, ?> function;
	
	@Override
	public Function<?, ?> getFunction() {
		return function;
	}
	
	@Override
	public AssertionsProvider setFunction(Function<?, ?> function) {
		this.function = function;
		return this;
	}
	
	@Override
	public Object getFilter() {
		return getProperties().getFilter();
	}
	
	@Override
	public AssertionsProvider setFilter(Object filter) {
		getProperties().setFilter(filter);
		return this;
	}
	
	@Override
	protected Collection<Assertion> __execute__() throws Exception {
		Function<?, ?> function = getFunction();
		Object filter = getFilter();
		____execute____(function,filter);
		return __assertions__;
	}
	
	protected abstract void ____execute____(Function<?, ?> function,Object filter);
	
	protected AssertionsProvider __add__(Collection<Assertion> assertions) {
		if(CollectionHelper.isNotEmpty(assertions)) {
			if(__assertions__ == null)
				__assertions__ = new ArrayList<>();
			__assertions__.addAll(assertions);
		}
		return this;
	}
	
	protected AssertionsProvider __add__(Assertion...assertions) {
		return __add__(List.of(assertions));
	}
	
	@Deprecated
	protected AssertionsProvider __addFromBuilders__(Collection<AssertionBuilder> assertionBuilders) {
		if(CollectionHelper.isNotEmpty(assertionBuilders)) {
			for(AssertionBuilder index : assertionBuilders)
				__add__(index.execute().getOutput());
		}
		return this;
	}
	
	@Deprecated
	protected AssertionsProvider __addFromBuilders__(AssertionBuilder...assertionBuilders) {
		return __addFromBuilders__(List.of(assertionBuilders));
	}
	
	/**/
	
	@Deprecated
	protected static AssertionBuilderComparison __injectAssertionBuilderComparison__() {
		return __inject__(AssertionBuilderComparison.class);
	}
	
	@Deprecated
	protected static AssertionBuilderNull __injectAssertionBuilderNull__() {
		return __inject__(AssertionBuilderNull.class);
	}
}
