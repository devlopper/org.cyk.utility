package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.value.ValueUsageType;

@Dependent @Deprecated
public class FieldTypeGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<FieldType> implements FieldTypeGetter, Serializable {
	private static final long serialVersionUID = 1L;

	private FieldsGetter fieldGetter;
	
	@Override
	protected FieldType __execute__() {
		FieldType fieldType = null;
		FieldsGetter fieldGetter = getFieldGetter();
		
		Class<?> klass = getClazz();
		
		if(klass == null && fieldGetter != null)
			klass = fieldGetter.getClazz();
		
		Field field = getField();
		if(field == null) {
			if(fieldGetter != null)
				field = __injectCollectionHelper__().getFirst(fieldGetter.execute().getOutput());
		}
		
		if(field!=null){
			if(klass == null)
				klass = field.getDeclaringClass();
			
			fieldType = __inject__(FieldType.class).setField(field);
			if(field.getType().equals(field.getGenericType())) {
				fieldType.setType(field.getType());
			}else {
				if(field.getGenericType() instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
					if(__inject__(ArrayHelper.class).isEmpty(parameterizedType.getActualTypeArguments())) {
							
					}else {
						fieldType.setType(field.getType());
						for(Integer index = 0 ; index < parameterizedType.getActualTypeArguments().length ; index = index + 1) {
							Type type = parameterizedType.getActualTypeArguments()[index];
							Class<?> argumentClass = null;
							if(type instanceof TypeVariable) {
								argumentClass = __inject__(ClassHelper.class).getParameterAt(klass, index, Object.class);
							}else
								argumentClass = (Class<?>) type;
							fieldType.getParameterizedClasses(Boolean.TRUE).set(index,argumentClass);
						}
					}
				}else {
					fieldType.setType(__inject__(ClassHelper.class).getParameterAt(klass, 0, Object.class));
				}
			}
			
			if(fieldType.getType() == null)
				fieldType.setType(Object.class);
		}
		
		return fieldType;
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
	public FieldsGetter getFieldGetter() {
		return fieldGetter;
	}
	
	@Override
	public FieldsGetter getFieldGetter(Boolean injectIfNull) {
		return (FieldsGetter) __getInjectIfNull__(FIELD_FIELD_GETTER, injectIfNull);
	}
	
	@Override
	public FieldTypeGetter setFieldGetter(FieldsGetter fieldGetter) {
		this.fieldGetter = fieldGetter;
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
			setField(__inject__(CollectionHelper.class).getFirst(__inject__(FieldsGetter.class).execute(aClass,FieldHelper.join(names)).getOutput()));
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
	

	/**/

	private static final String FIELD_FIELD_GETTER ="fieldGetter";
}
