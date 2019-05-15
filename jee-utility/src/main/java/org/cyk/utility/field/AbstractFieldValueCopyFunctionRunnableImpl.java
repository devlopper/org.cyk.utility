package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByLong;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.value.ValueConverter;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractFieldValueCopyFunctionRunnableImpl extends AbstractFunctionRunnableImpl<FieldValueCopy> {
	private static final long serialVersionUID = 1L;

	public AbstractFieldValueCopyFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				__process__();
			}
		});
	}
	
	protected void __process__() {
		FieldValueCopy fieldValueCopy = getFunction();
		FieldValueGetter getterModel = fieldValueCopy.getValueGetter();
		FieldValueSetter setterModel = fieldValueCopy.getValueSetter();
		Map<String,String> fieldNameMap = fieldValueCopy.getFieldNameMap();
		if(fieldNameMap == null) {
			Boolean isAutomaticallyDetectFields = fieldValueCopy.getIsAutomaticallyDetectFields();
			if(isAutomaticallyDetectFields == null)
				isAutomaticallyDetectFields = Boolean.TRUE;
			if(Boolean.TRUE.equals(isAutomaticallyDetectFields)) {
				Fields fields = __inject__(FieldValueCopyFieldsGetter.class).setSourceClass(getterModel.getObject().getClass())
						.setDestinationClass(setterModel.getObject().getClass()).execute().getOutput();
				if(__inject__(CollectionHelper.class).isNotEmpty(fields)) {
					for(Field index : fields.get()) {
						if(fieldNameMap == null)
							fieldNameMap = new HashMap<>();
						if(!Modifier.isStatic(index.getModifiers()) && !Modifier.isFinal(index.getModifiers()))
							fieldNameMap.put(index.getName(), index.getName());
					}
				}
			}
		}
		if(fieldNameMap == null) {
			Object value = getterModel.execute().getOutput();
			if(setterModel.getField() == null && getterModel.getField()!=null)
				setterModel.setField(getterModel.getField().getName());
			setterModel.setValue(value).execute();
		}else {
			for(Map.Entry<String, String> entry : fieldNameMap.entrySet()) {
				FieldValueSetter setter = __inject__(FieldValueSetter.class).setObject(setterModel.getObject()).setField(entry.getValue());
				if(setter.getField()!=null) {
					FieldValueGetter getter = __inject__(FieldValueGetter.class).setObject(getterModel.getObject()).setField(entry.getKey());
					Object value = getter.execute().getOutput();
					if(value!=null)
						value = __processValue__(getter.getField(),setter.getField(),value);
					setter.setValue(value);
					if(setter.getField() == null)
						setter.setField(getter.getField().getName());
					setter.execute();	
				}
			}
		}
	}
	
	protected Object __processValue__(Field source,Field destination,Object value) {
		ClassHelper classHelper = __inject__(ClassHelper.class);
		ClassInstancesRuntime classInstancesRuntime = __inject__(ClassInstancesRuntime.class);
		Class<?> sourceType = __inject__(FieldTypeGetter.class).execute(source).getOutput();
		Class<?> destinationType = __inject__(FieldTypeGetter.class).execute(destination).getOutput();
		Properties properties = new Properties();
		properties.copyFrom(getFunction().getProperties(), Properties.CONTEXT,Properties.REQUEST);
		
		//Primitive or string or enum
		if(classHelper.isNumberOrStringOrEnum(sourceType)) {
			if(classHelper.isNumberOrStringOrEnum(destinationType)) {
				//convert value
				return __inject__(ValueConverter.class).execute(value, destinationType).getOutput();
			}
			if(!classHelper.isBelongsToJavaPackages(destinationType)) {
				//value might be a destination type instance business identifier
				return __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, value,properties);
			}
		}
		
		//Non java classes , means custom classes
		if(!classHelper.isBelongsToJavaPackages(sourceType)) {
			if(classHelper.isNumberOrStringOrEnum(destinationType)) {
				//get value business identifier
				return __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
			}
			if(!classHelper.isBelongsToJavaPackages(destinationType)) {
				if(Boolean.TRUE.equals(classInstancesRuntime.get(destinationType).getIsPersistable())) {
					if(destination.isAnnotationPresent(javax.persistence.ManyToOne.class)) {
						//Find the object to be linked by its identifier (system and/or business)
						Object identifier = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(value);
						if(identifier == null) {
							identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
							return __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, identifier,properties);
						}else {
							Fields fields =  __inject__(FieldGetter.class).setClazz(destinationType).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM)
									.execute().getOutput();
							if(__inject__(CollectionHelper.class).isNotEmpty(fields)) {
								Class<?> identifierTypeDestinationType = null;//fields.getFirst().getType();
								if(__inject__(ClassHelper.class).isInstanceOf(destinationType, AbstractIdentifiedPersistableByLong.class))
									identifierTypeDestinationType = Long.class;
								identifier = __inject__(ValueConverter.class).execute(identifier, identifierTypeDestinationType).getOutput();	
							}
							
							return __inject__(InstanceHelper.class).getByIdentifierSystem(destinationType, identifier,properties);
						}	
					}
				}
				
				//get the field to be copied and call field value copier
				Object temp = value;
				value = __inject__(destinationType);
				FieldValueCopy fieldValueCopy = __inject__(FieldValueCopy.class).setSource(temp).setDestination(value);
				if(Boolean.TRUE.equals(classInstancesRuntime.get(sourceType).getIsPersistable()) && 
						Boolean.TRUE.equals(classInstancesRuntime.get(destinationType).getIsTransferable())) {
					//TODO if business identifier is there take it first otherwise take system identifier
					fieldValueCopy.setFieldName(__inject__(FieldNameGetter.class).execute(sourceType, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput());
					//fieldValueCopy.setFieldName(__inject__(FieldNameGetter.class).execute(sourceType, FieldName.IDENTIFIER, ValueUsageType.BUSINESS).getOutput());								
				}else {
					fieldValueCopy.setIsAutomaticallyDetectFields(Boolean.TRUE);
				}
				fieldValueCopy.execute();
				return value;
			}
		}
		
		/*if(isUnSet && !classHelper.isBelongsToJavaPackages(sourceType)) {
			//value will be compute by converter
			isUnSet = Boolean.FALSE;
		}*/
		
		//__inject__(ThrowableHelper.class).throwRuntimeException("value copy from field <<"+source+">> to field <<"+destination+">> is not yet handled");
		
		/*
		if(!sourceType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(sourceType.getName(), "java.","javax.")) {
			//source is a custom object
			if(Boolean.TRUE.equals(__inject__(ClassInstancesRuntime.class).get(destinationType).getIsPersistable())) {
				if(destination.isAnnotationPresent(javax.persistence.ManyToOne.class)) {
					//Find the object to be linked by its identifier (system and/or business)
					Object identifier = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(value);
					if(identifier == null) {
						identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
						value = __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, identifier,properties);
					}else {
						value = __inject__(InstanceHelper.class).getByIdentifierSystem(destinationType, identifier,properties);
					}	
					isUnSet = Boolean.FALSE;
				}
			}
			
			if(Boolean.TRUE.equals(isUnSet)) {
				if(String.class.equals(destinationType) || destinationType.isPrimitive()) {
					//single value
					value = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
					isUnSet = Boolean.FALSE;
				}else if(!StringUtils.startsWithAny(destinationType.getName(), "java.","javax.")){
					//not a single value
					Object temp = value;
					value = __inject__(destinationType);
					FieldValueCopy fieldValueCopy = __inject__(FieldValueCopy.class).setSource(temp).setDestination(value);
					fieldValueCopy.getProperties().setCaller(getFunction());
					if(getFunction().getProperties().getCaller() == null) {
						fieldValueCopy.setIsAutomaticallyDetectFields(Boolean.TRUE);		
					}else {
						fieldValueCopy.setFieldName(__inject__(FieldNameGetter.class).execute(sourceType, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput());
						fieldValueCopy.setFieldName(__inject__(FieldNameGetter.class).execute(sourceType, FieldName.IDENTIFIER, ValueUsageType.BUSINESS).getOutput());
					}
					fieldValueCopy.execute();
					isUnSet = Boolean.FALSE;
				}	
			}
		}
		
		if(Boolean.TRUE.equals(isUnSet)) {
			if(!destinationType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(destinationType.getName(), "java.","javax.")) {
				value = __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, value,properties);
			}	
		}
		*/
		return value;
	}
	
}
