package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

@ApplicationScoped
public class FieldHelperImpl extends AbstractHelper implements FieldHelper,Serializable {
	private static final long serialVersionUID = -5367150176793830358L;

	@Override
	public <TYPE> Class<TYPE> getParameterAt(Field field, Integer index, Class<TYPE> typeClass) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<TYPE>) type.getActualTypeArguments()[index];
	}
	
	@Override
	public String join(Collection<String> paths) {
		return __inject__(StringHelper.class).concatenate(paths, DOT);
	}

	@Override
	public String join(String... paths) {
		return join(__inject__(CollectionHelper.class).instanciate(paths));
	}

	@Override
	public Strings disjoin(Collection<String> paths) {
		String path = join(paths);
		Strings names = __inject__(Strings.class);
		names.add(StringUtils.split(path,DOT));
		return names;
	}
	
	@Override
	public Strings disjoin(String... paths) {
		return disjoin(__inject__(CollectionHelper.class).instanciate(paths));
	}
	
	@Override
	public Object getFieldValueSystemIdentifier(Object object) {
		return __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).execute().getOutput();
	}

	@Override
	public Object getFieldValueBusinessIdentifier(Object object) {
		return __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.BUSINESS).execute().getOutput();
	}

	@Override
	public Object getFieldValueSystemOrBusinessIdentifier(Object object) {
		Object identifier = getFieldValueSystemIdentifier(object);
		if(identifier == null)
			identifier = getFieldValueBusinessIdentifier(object);
		return identifier;
	}
	
	@Override
	public FieldHelper setFieldValueSystemIdentifier(Object object, Object value) {
		__inject__(FieldValueSetter.class).setObject(object).setField(FieldName.IDENTIFIER, ValueUsageType.SYSTEM).setValue(value).execute();
		return this;
	}
	
	@Override
	public FieldHelper setFieldValueBusinessIdentifier(Object object, Object value) {
		__inject__(FieldValueSetter.class).setObject(object).setField(FieldName.IDENTIFIER, ValueUsageType.BUSINESS).setValue(value).execute();
		return this;
	}
	
	@Override
	public FieldHelper copy(Object source, Object destination,Properties properties) {
		FieldValueCopy fieldValueCopy = __inject__(FieldValueCopy.class);
		fieldValueCopy.copyProperty(Properties.CONTEXT, properties);
		fieldValueCopy.copyProperty(Properties.REQUEST, properties);
		Boolean isAutomaticallyDetectFields = null;
		Object fields = properties.getFields();
		if(fields instanceof Collection && __inject__(CollectionHelper.class).isNotEmpty((Collection<?>) fields)) {
			for(String index : ((Collection<String>)fields))
				fieldValueCopy.setFieldName(index);
			isAutomaticallyDetectFields = Boolean.FALSE;
		}else {
			isAutomaticallyDetectFields = Boolean.TRUE;
		}
		fieldValueCopy.setSource(source).setDestination(destination).setIsAutomaticallyDetectFields(isAutomaticallyDetectFields).execute();
		return this;
	}
	
	@Override
	public FieldHelper copy(Object source, Object destination) {
		return copy(source, destination, null);
	}
	
	@Override
	public Field getField(Class<?> aClass, Collection<String> fieldNames) {
		//System.out.println("FieldHelperImpl.getField() ::: "+aClass+" ::: "+fieldNames);
		CollectionHelper collectionHelper = __inject__(CollectionHelper.class);
		Field field;
		if(aClass == null || collectionHelper.isEmpty(fieldNames)) {
			field = null;
		}else {
			String fieldName = join(fieldNames);
			fieldNames = collectionHelper.instanciate(StringUtils.split(fieldName, DOT));
			field = collectionHelper.getFirst(__inject__(FieldsGetter.class).execute(aClass, collectionHelper.getElementAt(fieldNames, 0)).getOutput());
			if(collectionHelper.getSize(fieldNames) == 1) {
			
			}else {
				aClass = __inject__(FieldTypeGetter.class).execute(field).getOutput().getType();
				if(Boolean.TRUE.equals(aClass.isInterface()))
					aClass = __inject__(aClass).getClass();
				field = getField(aClass, collectionHelper.getElementsFrom(fieldNames, 1));
			}
		}
		
		return field;
	}
	
	@Override
	public Field getField(Class<?> aClass, String... fieldNames) {
		return getField(aClass, __inject__(CollectionHelper.class).instanciate(fieldNames));
	}
	
	@Override
	public Field getField(Class<?> klass, FieldName fieldName, ValueUsageType valueUsageType) {
		String name = __inject__(FieldNameGetter.class).execute(klass, fieldName,valueUsageType).getOutput();
		return __inject__(CollectionHelper.class).getFirst(__inject__(FieldsGetter.class).execute(klass, name).getOutput());
	}
	
	@Override
	public <T> Collection<T> getSystemIdentifiers(Class<T> identifierClass,Collection<?> objects) {
		Collection<T> collection = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(objects)) {
			collection = new ArrayList<T>();
			for(Object index : objects)
				collection.add((T) getFieldValueSystemIdentifier(index));
		}
		return collection;
	}
	
	@Override
	public <T> Collection<T> getBusinessIdentifiers(Class<T> identifierClass,Collection<?> objects) {
		Collection<T> collection = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(objects)) {
			collection = new ArrayList<T>();
			for(Object index : objects)
				collection.add((T) getFieldValueBusinessIdentifier(index));
		}
		return collection;
	}
	
	@Override
	public Object readFieldValue(Object object,Field field) {
		try {
			return FieldUtils.readField(field,object, Boolean.TRUE);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public Object readFieldValue(Object object,String fieldName) {
		try {
			return FieldUtils.readField(object, fieldName, Boolean.TRUE);
		} catch (IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/*
	public FieldHelper __set__(Object source,Object destination){
		for(FieldName indexFieldName : new FieldName[] {FieldName.IDENTIFIER})
			for(ValueUsageType indexValueUsageType : new ValueUsageType[] {ValueUsageType.SYSTEM,ValueUsageType.BUSINESS}) {
				Object value = __inject__(FieldValueGetter.class).execute(persistenceEntity, indexFieldName, indexValueUsageType);
				__inject__(FieldValueSetter.class).execute(this, indexFieldName, indexValueUsageType,__stringify__(value));
			}
				
		//__inject__(FieldValueSetter.class).execute(this, FieldName.IDENTIFIER, ValueUsageType.BUSINESS,);
		return this;
	}*/
	
	/**/
	
	private static final String DOT = ConstantCharacter.DOT.toString();
}
