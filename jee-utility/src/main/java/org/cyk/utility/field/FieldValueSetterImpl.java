package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.value.ValueUsageType;

public class FieldValueSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FieldValueSetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____() {
		Object object = getObject();
		Object value = getValue();
		Field field = getField();
		if(object!=null){
			if(field!=null){
				try {
					FieldUtils.writeField(field, object,value, Boolean.TRUE);
				} catch (IllegalAccessException exception) {
					__inject__(Log.class).executeThrowable(exception);
				}
			}else {
				
			}
		}
	}
	
	@Override
	public FieldValueSetter execute(Object object, Field field,Object value) {
		setObject(object).setField(field).setValue(value).execute();
		return this;
	}

	@Override
	public FieldValueSetter execute(Object object, String fieldName,Object value) {
		setObject(object).setField(fieldName).setValue(value).execute();
		return this;
	}
	
	@Override
	public FieldValueSetter execute(Object object, FieldName fieldName,ValueUsageType valueUsageType,Object value) {
		setObject(object).setField(fieldName,valueUsageType).setValue(value).execute();
		return this;
	}

	@Override
	public Object getObject() {
		return getProperties().getObject();
	}

	@Override
	public FieldValueSetter setObject(Object object) {
		getProperties().setObject(object);
		return this;
	}
	
	@Override
	public Object getValue() {
		return getProperties().getValue();
	}

	@Override
	public FieldValueSetter setValue(Object object) {
		getProperties().setValue(object);
		return this;
	}

	@Override
	public Field getField() {
		return (Field) getProperties().getField();
	}

	@Override
	public FieldValueSetter setField(Field field) {
		getProperties().setField(field);
		return this;
	}
	
	@Override
	public FieldValueSetter setField(Class<?> aClass, Collection<String> names) {
		if(aClass !=null && __inject__(CollectionHelper.class).isNotEmpty(names)){
			setField(__inject__(CollectionHelper.class).getFirst(__inject__(FieldGetter.class).execute(aClass, __inject__(FieldHelper.class).concatenate(names)).getOutput()));
		}
		return this;
	}
	
	@Override
	public FieldValueSetter setField(Class<?> aClass, String... names) {
		setField(aClass, __inject__(CollectionHelper.class).instanciate(names));
		return this;
	}
	
	@Override
	public FieldValueSetter setField(Collection<String> names) {
		Object object = getObject();
		if(object!=null)
			setField(object.getClass(), names);
		return this;
	}
	
	@Override
	public FieldValueSetter setField(String... names) {
		Object object = getObject();
		if(object!=null)
			setField(object.getClass(), names);
		return this;
	}
	
	@Override
	public FieldValueSetter setField(FieldName fieldName,ValueUsageType valueUsageType) {
		if(getObject() == null){
			//TODO log warning
		}else{
			setField(__inject__(FieldNameGetter.class).setClazz(getObject().getClass()).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput());	
		}
		return this;
	}

	@Override
	public String getFieldName() {
		return (String) getProperties().getFieldName();
	}

	@Override
	public FieldValueSetter setFieldName(String fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}



}
