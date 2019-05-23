package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.value.ValueUsageType;

public class ChoicePropertyValueBuilderImpl extends AbstractChoicePropertyValueBuilderImpl implements ChoicePropertyValueBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Object[][] FIELD_NAME_VALUE_USAGE_TYPE = {
			{FieldName.NAME,ValueUsageType.BUSINESS},{FieldName.IDENTIFIER,ValueUsageType.BUSINESS},{FieldName.IDENTIFIER,ValueUsageType.SYSTEM}
	};
	
	@Override
	protected String __execute__() throws Exception {
		String result = null;
		Object object = getObject();
		String fieldName = null;
		if(object!=null) {
			String propertyName = getPropertyName();
			Field field = null;
			if(__injectStringHelper__().isNotBlank(propertyName) )
				field = __injectCollectionHelper__().getFirst(__inject__(FieldGetter.class).execute(object.getClass(), propertyName).getOutput());
			
			if(__injectStringHelper__().isBlank(propertyName) || field == null) {
				FieldGetter fieldGetter = __inject__(FieldGetter.class).setClazz(object.getClass());
				for(Object[] index : FIELD_NAME_VALUE_USAGE_TYPE)
					fieldGetter.addNameToken((FieldName)index[0], (ValueUsageType)index[1]);
				field = __injectCollectionHelper__().getFirst(fieldGetter.execute().getOutput());
			}
			
			if(field != null) {
				fieldName = field.getName();
				Object value = __injectFieldValueGetter__().execute(object, field).getOutput();
				if(value == null)
					result = ConstantEmpty.STRING;
				else
					result = value.toString();
			}else
				result = object.toString();
		}
		if(__injectStringHelper__().isBlank(result)) {
			System.out.println("blank result has been found for Object : "+object+" , and field : "+fieldName);
		}
		return result;
	}
	
}
