package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;

import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.value.ValueUsageType;

public class ChoicePropertyValueBuilderImpl extends AbstractChoicePropertyValueBuilderImpl implements ChoicePropertyValueBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		String result = null;
		Object object = getObject();
		if(object!=null) {
			String propertyName = getPropertyName();
			if(__injectStringHelper__().isBlank(propertyName))
				propertyName = __inject__(FieldNameGetter.class).execute(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS).getOutput();
			if(__injectStringHelper__().isNotBlank(propertyName) && __injectCollectionHelper__().isNotEmpty(__inject__(FieldGetter.class).execute(object.getClass(), propertyName).getOutput())) {
				Object value = __injectFieldValueGetter__().execute(object, propertyName).getOutput();
				if(value == null)
					result = StringConstant.EMPTY;
				else
					result = value.toString();
			}else
				result = object.toString();
		}
		return result;
	}
	
}
