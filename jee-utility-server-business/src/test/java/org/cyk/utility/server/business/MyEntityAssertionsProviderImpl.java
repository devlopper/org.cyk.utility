package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.assertion.AbstractAssertionsProviderForImpl;
import org.cyk.utility.assertion.AssertionBuilderComparison;

public class MyEntityAssertionsProviderImpl extends AbstractAssertionsProviderForImpl<MyEntity> implements MyEntityAssertionsProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Object filter, MyEntity myEntity) {
		if(filter==null) {
			//assert long1 > -1
			__injectAssertionBuilderComparison__()
			.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__injectFieldValueGetter__().setObject(myEntity).setField("long1")).getParentAs(AssertionBuilderComparison.class)
			.getAssertedValue2(Boolean.TRUE).setValue(-1).getParentAs(AssertionBuilderComparison.class).setOperator(ComparisonOperator.GT).setIsThrownWhenValueIsNotTrue(Boolean.TRUE)
			.execute();
			
			
		}
	}

}