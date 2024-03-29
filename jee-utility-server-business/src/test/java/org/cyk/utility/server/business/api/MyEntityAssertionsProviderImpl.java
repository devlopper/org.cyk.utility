package org.cyk.utility.server.business.api;

import java.io.Serializable;

import org.cyk.utility.__kernel__.assertion.AssertionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.field.Field;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.assertion.AbstractAssertionsProviderForImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.persistence.entities.MyEntity;

public class MyEntityAssertionsProviderImpl extends AbstractAssertionsProviderForImpl<MyEntity> implements MyEntityAssertionsProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Function<?, ?> function,Object filter, MyEntity myEntity) {
		if(function instanceof BusinessFunctionCreator) {
			if(filter==null) {
				if(myEntity.getLong1() != null) {
					//assert long1 > -1
					__add__(AssertionHelper.buildAssertionComparison(
							new Value().setObject(myEntity).setField(Field.get(MyEntity.class, "long1"))
							, ComparisonOperator.GT, new Value().set(-1)));
				}
				
			}
		}
	}

}