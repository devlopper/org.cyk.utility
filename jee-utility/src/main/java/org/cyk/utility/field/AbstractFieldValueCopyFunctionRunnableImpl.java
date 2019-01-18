package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.clazz.ClassHelper;
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
				Collection<Field> fields = __inject__(FieldGetter.class).setClazz(getterModel.getObject().getClass()).execute().getOutput();
				if(__inject__(CollectionHelper.class).isNotEmpty(fields))
					for(Field index : fields) {
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
		
		if(!sourceType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(sourceType.getName(), "java.","javax."))
			value = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(value);
		
		if(!destinationType.isPrimitive() && !sourceType.isEnum() && !__inject__(ClassHelper.class).isInstanceOf(sourceType, Collection.class) && !StringUtils.startsWithAny(destinationType.getName(), "java.","javax."))
			value = __inject__(InstanceHelper.class).getByIdentifierBusiness(destinationType, value);
		return value;
	}
	
}
