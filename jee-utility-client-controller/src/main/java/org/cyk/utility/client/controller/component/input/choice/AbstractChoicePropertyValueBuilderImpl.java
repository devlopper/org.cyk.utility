package org.cyk.utility.client.controller.component.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.Fields;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractChoicePropertyValueBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ChoicePropertyValueBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Object[][] FIELD_NAME_VALUE_USAGE_TYPE = {
			{FieldName.NAME,ValueUsageType.BUSINESS},{FieldName.IDENTIFIER,ValueUsageType.BUSINESS},{FieldName.IDENTIFIER,ValueUsageType.SYSTEM}
	};
	
	private ChoiceProperty property;
	private String propertyName;
	private Object object;
	
	@Override
	protected String __execute__() throws Exception {
		String result = null;
		Object object = getObject();
		String fieldName = null;
		if(object!=null) {
			String propertyName = getPropertyName();
			if(__injectStringHelper__().isBlank(propertyName) ) {
				ChoiceProperty property = getProperty();
				if(property != null) {
					//TODO derive property name
					//System.out.println("Property name should be derived from "+property);
				}	
			}
			
			Field field = null;
			if(__injectStringHelper__().isNotBlank(propertyName) )
				field = __injectCollectionHelper__().getFirst(__inject__(FieldsGetter.class).execute(object.getClass(), propertyName).getOutput());
			
			if(__injectStringHelper__().isBlank(propertyName) || field == null) {
				if(field == null) {
					FieldsGetter fieldGetter = __inject__(FieldsGetter.class).setClazz(object.getClass());
					for(Object[] index : FIELD_NAME_VALUE_USAGE_TYPE)
						fieldGetter.addNameToken((FieldName)index[0], (ValueUsageType)index[1]);
					Fields fields = fieldGetter.execute().getOutput();
					if(__injectCollectionHelper__().isNotEmpty(fields)) {
						for(Field indexField : fields.get()) {
							Object fieldValue = FieldHelperImpl.__read__(object, indexField);
							if(Boolean.TRUE.equals(__injectValueHelper__().isNotEmpty(fieldValue))) {
								field = indexField;
								break;
							}
						}	
					}
				}
			}
			
			if(field != null) {
				fieldName = field.getName();
				Object value = FieldHelperImpl.__read__(object, field);
				if(value == null)
					result = ConstantEmpty.STRING;
				else
					result = value.toString();
			}else
				result = object.toString();
		}
	
		if(object != null && __injectStringHelper__().isBlank(result)) {
			System.out.println("blank result has been found for Object : "+object+" , and field : "+fieldName);
		}
		return result;
	}
	
	@Override
	public ChoiceProperty getProperty() {
		return property;
	}
	
	@Override
	public ChoicePropertyValueBuilder setProperty(ChoiceProperty property) {
		this.property = property;
		return this;
	}
	
	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public ChoicePropertyValueBuilder setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public ChoicePropertyValueBuilder setObject(Object object) {
		this.object = object;
		return this;
	}

}
