package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueConverter;

@Dependent @Deprecated
public class FieldValueSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FieldValueSetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____() {
		Object object = getObject();
		Object value = getValue();
		Field field = getField();
		if(object!=null){
			Class<?> fieldType = null;
			if(field == null) {
				String getMethodName = "get"+__inject__(StringHelper.class).applyCase(getFieldName(), Case.FIRST_CHARACTER_UPPER);
				Method getMethod = MethodUtils.getAccessibleMethod(object.getClass(), getMethodName);
				if(getMethod!=null)
					fieldType = getMethod.getReturnType();
			}else {
				fieldType = (Class<?>) FieldHelper.getType(field, null);
			}
			Class<?> fieldTypeWrapper = ClassUtils.primitiveToWrapper(fieldType);
			if(value!=null && !fieldTypeWrapper.equals(value.getClass())) {
				value = __inject__(ValueConverter.class).execute(value, fieldTypeWrapper).getOutput();
			}
			
			String methodName = "set"+__inject__(StringHelper.class).applyCase(getFieldName(), Case.FIRST_CHARACTER_UPPER);
			Method method = MethodUtils.getAccessibleMethod(object.getClass(), methodName,fieldType);

			if(method == null) {
				if(field!=null){
					try {
						
						if(Modifier.isFinal(field.getModifiers())) {
							//TODO log warning
						}else {
							FieldUtils.writeField(field, object,value, Boolean.TRUE);	
						}
					} catch (IllegalAccessException exception) {
						__inject__(Log.class).executeThrowable(exception);
					}
				}else {
					
				}	
			}else {
				try {
					method.invoke(object, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		setObject(object).setFieldName(fieldName).setField(fieldName).setValue(value).execute();
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
		if(aClass !=null && CollectionHelper.isNotEmpty(names)){
			setField(CollectionHelper.getFirst(__inject__(FieldsGetter.class).execute(aClass, FieldHelper.join(names)).getOutput()));
		}
		return this;
	}
	
	@Override
	public FieldValueSetter setField(Class<?> aClass, String... names) {
		setField(aClass, List.of(names));
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
			setFieldName(__inject__(FieldNameGetter.class).setClazz(getObject().getClass()).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput());
			setField(getFieldName());	
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
