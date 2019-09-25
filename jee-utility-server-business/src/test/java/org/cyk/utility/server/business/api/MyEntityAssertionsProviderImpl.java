package org.cyk.utility.server.business.api;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.assertion.AbstractAssertionsProviderForImpl;
import org.cyk.utility.field.FieldInstances;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.value.Value;

public class MyEntityAssertionsProviderImpl extends AbstractAssertionsProviderForImpl<MyEntity> implements MyEntityAssertionsProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void ____execute____(Function<?, ?> function,Object filter, MyEntity myEntity) {
		if(function instanceof BusinessFunctionCreator) {
			if(filter==null) {
				if(myEntity.getLong1() != null) {
					//assert long1 > -1
					org.cyk.utility.assertion.AssertionHelper.buildAssertionComparison(
							__inject__(Value.class).setFieldInstance(__inject__(FieldInstances.class).get(MyEntity.class, "long1")), ComparisonOperator.GT
							, __inject__(Value.class).set(-1));
				}
				
			}
		}
	}

}