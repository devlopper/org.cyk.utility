package org.cyk.utility.object;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldInstanceValue;
import org.cyk.utility.field.FieldInstanceValues;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.string.Strings;

public abstract class AbstractObjectToStringBuilderImpl extends AbstractObjectToOrFromStringBuilderImpl<String> implements ObjectToStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object object;
	
	@Override
	protected String __execute__(Strings fieldNamesStrings) throws Exception {
		Object object = __injectValueHelper__().returnOrThrowIfBlank("object to stringify", getObject());
		FieldInstanceValues fieldInstanceValues = null;
		if(Boolean.TRUE.equals(__inject__(ClassHelper.class).isBelongsToJavaPackages(object.getClass()))) {
			
		}else {
			if(__injectCollectionHelper__().isNotEmpty(fieldNamesStrings)) {
				fieldInstanceValues = __inject__(FieldInstanceValues.class);
				for(String index : fieldNamesStrings.get()) {
					FieldInstanceValue fieldInstanceValue = __inject__(FieldInstanceValue.class);
					fieldInstanceValue.setFieldInstance(__inject__(FieldInstancesRuntime.class).get(object.getClass(), index));
					fieldInstanceValue.setValue(__inject__(FieldValueGetter.class).execute(object, index).getOutput());
					fieldInstanceValues.add(fieldInstanceValue);
				}
			}	
		}
		return __execute__(object,fieldInstanceValues);
	}
	
	protected abstract String __execute__(Object object,FieldInstanceValues fieldInstanceValues) throws Exception;
	
	@Override
	public Object getObject() {
		return object;
	}
	
	@Override
	public ObjectToStringBuilder setObject(Object object) {
		this.object = object;
		return this;
	}
	
	@Override
	public ObjectToStringBuilder addFieldNamesStrings(Collection<String> fieldNamesStrings) {
		return (ObjectToStringBuilder) super.addFieldNamesStrings(fieldNamesStrings);
	}
	
	@Override
	public ObjectToStringBuilder addFieldNamesStrings(String... fieldNamesStrings) {
		return (ObjectToStringBuilder) super.addFieldNamesStrings(fieldNamesStrings);
	}
	
}
