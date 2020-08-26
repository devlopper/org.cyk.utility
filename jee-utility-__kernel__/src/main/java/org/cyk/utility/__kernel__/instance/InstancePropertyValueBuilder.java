package org.cyk.utility.__kernel__.instance;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface InstancePropertyValueBuilder {

	default Object build(Object instance,Property property) {
		if(instance == null)
			return null;
		if(Property.LABEL.equals(property)) {
			String label = (String) FieldHelper.readName(instance);
			if(StringHelper.isNotBlank(label))
				return label;
			label = (String) FieldHelper.readBusinessIdentifier(instance);
			if(StringHelper.isNotBlank(label))
				return label;
			label = (String) FieldHelper.readSystemIdentifier(instance);
			if(StringHelper.isNotBlank(label))
				return label;
			return null;	
		}
		throw new RuntimeException("build property "+property+" not yet handled");
	}
	
	default String buildLabel(Object instance) {
		if(instance == null)
			return null;
		return (String) build(instance, Property.LABEL);
	}
	
	/**/
	
	static InstancePropertyValueBuilder getInstance() {
		return Helper.getInstance(InstancePropertyValueBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static enum Property {
		LABEL
		,VALUE
		,DESCRIPTION
		,IS_DISABLE
		,IS_LABEL_ESCAPABLE
	}
}
