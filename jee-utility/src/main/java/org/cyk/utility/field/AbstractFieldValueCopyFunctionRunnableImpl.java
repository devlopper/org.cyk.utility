package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.instance.InstanceHelper;

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
				Fields fields = __inject__(FieldGetter.class).setClazz(getterModel.getObject().getClass()).execute().getOutput();
				if(__inject__(CollectionHelper.class).isNotEmpty(fields))
					for(Field index : fields.get()) {
						if(fieldNameMap == null)
							fieldNameMap = new HashMap<>();
						if(!Modifier.isStatic(index.getModifiers()) && !Modifier.isFinal(index.getModifiers()))
							fieldNameMap.put(index.getName(), index.getName());
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
		Class<?> sourceType = __inject__(FieldTypeGetter.class).execute(source).getOutput();
		Class<?> destinationType = __inject__(FieldTypeGetter.class).execute(destination).getOutput();
		//System.out.println("FieldValueCopyImpl.__processValue__() "+source+" *** "+sourceType+" *** "+destination+" *** "+destinationType);
		Boolean isUnSet = Boolean.TRUE;
		if(!sourceType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(sourceType.getName(), "java.","javax.")) {
			//source is a custom object
			if(Boolean.TRUE.equals(__inject__(ClassInstancesRuntime.class).get(destinationType).getIsPersistable())) {
				if(destination.isAnnotationPresent(javax.persistence.ManyToOne.class)) {
					//Find the object to be linked by its identifier (system or business)
					Object identifier = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(value);
					if(identifier == null) {
						identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
						value = __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, identifier);
					}else {
						value = __inject__(InstanceHelper.class).getByIdentifierSystem(destinationType, identifier);
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
					__inject__(FieldValueCopy.class).setSource(temp).setDestination(value).setIsAutomaticallyDetectFields(Boolean.TRUE).execute();
					isUnSet = Boolean.FALSE;
				}	
			}
		}
		
		if(Boolean.TRUE.equals(isUnSet)) {
			if(!destinationType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(destinationType.getName(), "java.","javax.")) {
				value = __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, value);
			}	
		}
		
		return value;
	}
	
}
