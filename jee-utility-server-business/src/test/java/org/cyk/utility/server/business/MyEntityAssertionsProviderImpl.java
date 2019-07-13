package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.assertion.AbstractAssertionsProviderForImpl;
import org.cyk.utility.assertion.AssertionBuilderComparison;
import org.cyk.utility.server.persistence.entities.MyEntity;

public class MyEntityAssertionsProviderImpl extends AbstractAssertionsProviderForImpl<MyEntity> implements MyEntityAssertionsProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Function<?, ?> function,Object filter, MyEntity myEntity) {
		if(function instanceof BusinessFunction) {
			if(filter==null) {
				if(myEntity.getLong1() != null) {
					//assert long1 > -1
					__injectAssertionBuilderComparison__()
					.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__injectFieldValueGetter__().setObject(myEntity).setField("long1")).getParentAs(AssertionBuilderComparison.class)
					.getAssertedValue2(Boolean.TRUE).setValue(-1).getParentAs(AssertionBuilderComparison.class).setOperator(ComparisonOperator.GT).setIsThrownWhenValueIsNotTrue(Boolean.TRUE)
					.execute()
					;
				}
				
			}
		}
	}

}