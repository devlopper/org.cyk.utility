package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.log.Log;

public class FieldValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements FieldValueGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() {
		Object value = null;
		Object object = getObject();
		Field field = getField();
		if(object!=null){
			if(field!=null){
				try {
					value = FieldUtils.readField(field, object, Boolean.TRUE);
				} catch (IllegalAccessException exception) {
					__inject__(Log.class).executeThrowable(exception);
				}
			}else {
				
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
		return this;
	}
	
	@Override
	public FieldValueGetter setField(Class<?> aClass, Collection<String> names) {
		if(aClass !=null && __inject__(CollectionHelper.class).isNotEmpty(names)){
			setField(__inject__(CollectionHelper.class).getFirst(__inject__(FieldGetter.class).execute(aClass, __inject__(FieldHelper.class).concatenate(names)).getOutput()));
		}
		return this;
	}
	
	@Override
	public FieldValueGetter setField(Class<?> aClass, String... names) {
		setField(aClass, __inject__(CollectionHelper.class).instanciate(names));
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
	public String getFieldName() {
		return (String) getProperties().getFieldName();
	}

	@Override
	public FieldValueGetter setFieldName(String fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}



}
