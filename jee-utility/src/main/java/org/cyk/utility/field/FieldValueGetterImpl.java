package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.log.Log;


@Dependent @Deprecated
public class FieldValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements FieldValueGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() {
		Object value = null;
		Object object = getObject();
		Field field = getField();
		if(field!=null && Modifier.isStatic(field.getModifiers())) {
			try {
				value = field.get(null);
			} catch (Exception exception) {
				__inject__(Log.class).executeThrowable(exception);
			}
		}else {
			if(object!=null){
				if(field == null) {
					String methodName = "get"+StringHelper.applyCase(getFieldName(), Case.FIRST_CHARACTER_UPPER);
					Method method = MethodUtils.getAccessibleMethod(object.getClass(), methodName);
					if(method!=null)
						try {
							value = method.invoke(object);
						} catch (Exception exception) {
							__inject__(Log.class).executeThrowable(exception);
						}
				}else{			
					try {
						value = FieldUtils.readField(field, object, Boolean.TRUE);
					} catch (IllegalAccessException exception) {
						__inject__(Log.class).executeThrowable(exception);
					}	
				}
			}	
		}
		return value;
	}
	
	@Override
	public FieldValueGetter execute(Object object, Field field) {
		setObject(object).setField(field).execute();
		return this;
	}

	@Override
	public FieldValueGetter execute(Object object, String fieldName) {
		setObject(object).setField(fieldName).execute();
		return this;
	}
	
	@Override
	public FieldValueGetter execute(Object object, FieldName fieldName,ValueUsageType valueUsageType) {
		setObject(object).setField(fieldName,valueUsageType).execute();
		return this;
	}

	@Override
	public Object getObject() {
		return getProperties().getObject();
	}

	@Override
	public FieldValueGetter setObject(Object object) {
		getProperties().setObject(object);
		return this;
	}

	@Override
	public Field getField() {
		return (Field) getProperties().getField();
	}

	@Override
	public FieldValueGetter setField(Field field) {
		getProperties().setField(field);
		if(field!=null)
			setFieldName(field.getName());
		return this;
	}
	
	@Override
	public FieldValueGetter setField(Class<?> aClass, Collection<String> names) {
		if(aClass !=null && CollectionHelper.isNotEmpty(names)){
			String fieldName = FieldHelper.join(names);
			setFieldName(fieldName);
			setField(CollectionHelper.getFirst(__inject__(FieldsGetter.class).execute(aClass, fieldName).getOutput()));
		}
		return this;
	}
	
	@Override
	public FieldValueGetter setField(Class<?> aClass, String... names) {
		setField(aClass, List.of(names));
		return this;
	}
	
	@Override
	public FieldValueGetter setField(Collection<String> names) {
		Object object = getObject();
		if(object!=null)
			setField(object.getClass(), names);
		return this;
	}
	
	@Override
	public FieldValueGetter setField(String... names) {
		Object object = getObject();
		if(object!=null)
			setField(object.getClass(), names);
		return this;
	}
	
	@Override
	public FieldValueGetter setField(FieldName fieldName,ValueUsageType valueUsageType) {
		if(getObject() == null){
			//TODO log warning
		}else{
			//TODO handle it by using ClassInstanceRunctime to get already loaded field like system or business identifier
			/*Field field = null;
			if(FieldName.IDENTIFIER.equals(fieldName) && (ValueUsageType.SYSTEM.equals(valueUsageType) || ValueUsageType.BUSINESS.equals(valueUsageType))) {
				ClassInstance classInstance = __inject__(ClassInstancesRuntime.class).get(getObject().getClass());
				if(classInstance != null) {
					if(ValueUsageType.SYSTEM.equals(valueUsageType))
						field = classInstance.getSystemIdentifierField();
					else if(ValueUsageType.BUSINESS.equals(valueUsageType))						
						field = classInstance.getBusinessIdentifierField();	
				}
			}
			if(field == null) {*/
				setField(__inject__(FieldNameGetter.class).setClazz(getObject().getClass()).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput());
			/*}else
				setField(field);
			*/
		}
		return this;
	}

	@Override
	public String getFieldName() {
		return (String) getProperties().getFieldName();
	}

	@Override
	public FieldValueGetter setFieldName(String fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}



}
