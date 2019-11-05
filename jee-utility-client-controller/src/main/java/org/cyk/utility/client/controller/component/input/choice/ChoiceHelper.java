package org.cyk.utility.client.controller.component.input.choice;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface ChoiceHelper {

	static String buildChoiceLabel(Object object) {
		if(object == null)
			return "!!NULL!!";
		String label = (String) FieldHelper.readName(object);
		if(StringHelper.isNotBlank(label))
			return label;
		label = (String) FieldHelper.readBusinessIdentifier(object);
		if(StringHelper.isNotBlank(label))
			return label;
		label = (String) FieldHelper.readSystemIdentifier(object);
		if(StringHelper.isNotBlank(label))
			return label;
		label = object.toString();
		if(StringHelper.isNotBlank(label))
			return label;
		return ToStringBuilder.reflectionToString(object);
	}
	
}
