package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.inject.Singleton;

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
