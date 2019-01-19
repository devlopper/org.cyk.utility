package org.cyk.utility.field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.map.MapHelper;

public class FieldValueCopyImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements FieldValueCopy,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FieldValueCopy execute(Object source, Object destination, Map<String, String> fieldNameMap) {
		return (FieldValueCopy) setValueGetter(__inject__(FieldValueGetter.class).setObject(source))
				.setValueSetter(__inject__(FieldValueSetter.class).setObject(destination)).setFieldNameMap(fieldNameMap).execute();
	}
	
	@Override
	public FieldValueCopy execute(Object source, Object destination, String fieldName) {
		@SuppressWarnings("rawtypes")
		Map map = __inject__(MapHelper.class).instanciate(fieldName,fieldName);
		return execute(source, destination,map );
	}
	
	@Override
	public FieldValueGetter getValueGetter() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.VALUE,Properties.GETTER);
	}

	@Override
	public FieldValueCopy setValueGetter(FieldValueGetter valueGetter) {
		getProperties().setFromPath(new Object[] {Properties.VALUE,Properties.GETTER},valueGetter);
		return this;
	}
	
	@Override
	public FieldValueGetter getValueGetter(Boolean injectIfNull) {
		FieldValueGetter getter = getValueGetter();
		if(getter == null && Boolean.TRUE.equals(injectIfNull))
			setValueGetter(getter = __inject__(FieldValueGetter.class));
		return getter;
	}

	@Override
	public FieldValueSetter getValueSetter() {
		return (FieldValueSetter) getProperties().getFromPath(Properties.VALUE,Properties.SETTER);
	}

	@Override
	public FieldValueCopy setValueSetter(FieldValueSetter valueSetter) {
		getProperties().setFromPath(new Object[] {Properties.VALUE,Properties.SETTER},valueSetter);
		return this;
	}
	
	@Override
	public FieldValueSetter getValueSetter(Boolean injectIfNull) {
		FieldValueSetter setter = getValueSetter();
		if(setter == null && Boolean.TRUE.equals(injectIfNull))
			setValueSetter(setter = __inject__(FieldValueSetter.class));
		return setter;
	}
	
	@Override
	public Map<String, String> getFieldNameMap() {
		return (Map<String, String>) getProperties().getFromPath(Properties.MAP,Properties.FIELD_NAME);
	}
	
	@Override
	public FieldValueCopy setFieldNameMap(Map<String, String> fieldNameMap) {
		getProperties().setFromPath(new Object[] {Properties.MAP,Properties.FIELD_NAME},fieldNameMap);
		return this;
	}
	
	@Override
	public FieldValueCopy setFieldName(String source, String destination) {
		Map<String,String> map = getFieldNameMap();
		if(map == null)
			setFieldNameMap(map = new HashMap<>());
		map.put(source, destination);
		return this;
	}
	
	@Override
	public FieldValueCopy setFieldName(String fieldName) {
		return setFieldName(fieldName, fieldName);
	}
	
	@Override
	public FieldValueCopy setSource(Object source) {
		getValueGetter(Boolean.TRUE).setObject(source);
		return this;
	}
	
	@Override
	public FieldValueCopy setDestination(Object destination) {
		getValueSetter(Boolean.TRUE).setObject(destination);
		return this;
	}

	@Override
	public FieldValueCopy setIsAutomaticallyDetectFields(Boolean value) {
		getProperties().setFromPath(new String[] {Properties.IS,Properties.DETECT,Properties.FIELD},value);
		return this;
	}
	
	@Override
	public Boolean getIsAutomaticallyDetectFields() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.DETECT,Properties.FIELD);
	}
}
