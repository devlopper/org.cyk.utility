package org.cyk.utility.__kernel__.object.__static__.representation;

import java.util.List;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentified<ELEMENT,IDENTIFIER> extends Collection<ELEMENT> {

	default CollectionOfIdentified<ELEMENT,IDENTIFIER> add(IDENTIFIER identifier) {
		ELEMENT element = instantiateElement();
		if(element == null)
			return this;
		if(identifier != null) {
			FieldHelper.write(element, FieldName.IDENTIFIER,ValueUsageType.SYSTEM, identifier);	
		}
		add(List.of(element));
		return this;
	}
	
}
