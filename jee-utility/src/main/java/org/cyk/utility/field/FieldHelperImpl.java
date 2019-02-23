package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class FieldHelperImpl extends AbstractHelper implements FieldHelper,Serializable {
	private static final long serialVersionUID = -5367150176793830358L;

	@Override
	public <TYPE> Class<TYPE> getParameterAt(Field field, Integer index, Class<TYPE> typeClass) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<TYPE>) type.getActualTypeArguments()[index];
	}
	
	@Override
	public String concatenate(Collection<String> names) {
		return __inject__(StringHelper.class).concatenate(names, CharacterConstant.DOT.toString());
	}

	@Override
	public String concatenate(String... names) {
		return concatenate(__inject__(CollectionHelper.class).instanciate(names));
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
	public FieldHelper copy(Object source, Object destination) {
		__inject__(FieldValueCopy.class).setSource(source).setDestination(destination).setIsAutomaticallyDetectFields(Boolean.TRUE).execute();
		return this;
	}
	
	@Override
	public Field getField(Class<?> aClass, Collection<String> fieldNames) {
		//System.out.println("FieldHelperImpl.getField() ::: "+aClass+" ::: "+fieldNames);
		CollectionHelper collectionHelper = __inject__(CollectionHelper.class);
		Field field;
		if(aClass == null || collectionHelper.isEmpty(fieldNames)) {
			field = null;
		}else {
			String fieldName = concatenate(fieldNames);
			fieldNames = collectionHelper.instanciate(StringUtils.split(fieldName, CharacterConstant.DOT.toString()));
			field = collectionHelper.getFirst(__inject__(FieldGetter.class).execute(aClass, collectionHelper.getElementAt(fieldNames, 0)).getOutput());
			if(collectionHelper.getSize(fieldNames) == 1) {
			
			}else {
				aClass = __inject__(FieldTypeGetter.class).execute(field).getOutput();
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
}
