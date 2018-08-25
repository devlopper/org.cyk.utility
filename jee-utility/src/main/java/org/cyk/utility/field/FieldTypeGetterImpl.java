package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

public class FieldTypeGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class<?>> implements FieldTypeGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<?> __execute__() {
		Class<?> value = null;
		Field field = getField();
		if(field!=null){
			value = field.getType();
		}
		return value;
	}
	
	@Override
	public FieldTypeGetter execute(Field field) {
		setField(field).execute();
		return this;
	}

	@Override
	public FieldTypeGetter execute(Class<?> aClass, String fieldName) {
		setClazz(aClass).setField(fieldName).execute();
		return this;
	}
	
	@Override
	public FieldTypeGetter execute(Class<?> aClass, FieldName fieldName,ValueUsageType valueUsageType) {
		setClazz(aClass).setField(fieldName,valueUsageType).execute();
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public FieldTypeGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public Field getField() {
		return (Field) getProperties().getField();
	}

	@Override
	public FieldTypeGetter setField(Field field) {
		getProperties().setField(field);
		return this;
	}
	
	@Override
	public FieldTypeGetter setField(Class<?> aClass, Collection<String> names) {
		if(aClass !=null && __inject__(CollectionHelper.class).isNotEmpty(names)){
			setField(__inject__(CollectionHelper.class).getFirst(__inject__(FieldGetter.class).execute(aClass, __inject__(FieldHelper.class).concatenate(names)).getOutput()));
		}
		return this;
	}
	
	@Override
	public FieldTypeGetter setField(Class<?> aClass, String... names) {
		setField(aClass, __inject__(CollectionHelper.class).instanciate(names));
		return this;
	}
	
	@Override
	public FieldTypeGetter setField(Collection<String> names) {
		setField(getClazz(), names);
		return this;
	}
	
	@Override
	public FieldTypeGetter setField(String... names) {
		setField(getClazz(), names);
		return this;
	}
	
	@Override
	public FieldTypeGetter setField(FieldName fieldName,ValueUsageType valueUsageType) {
		if(getClazz() == null){
			//TODO log warning
		}else{
			setField(__inject__(FieldNameGetter.class).setClazz(getClazz()).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput());	
		}
		return this;
	}

	@Override
	public String getFieldName() {
		return (String) getProperties().getFieldName();
	}

	@Override
	public FieldTypeGetter setFieldName(String fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}



}
