package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentifiedByStringAndCoded<ELEMENT> extends CollectionOfIdentifiedByString<ELEMENT> {

	default CollectionOfIdentifiedByStringAndCoded<ELEMENT> add(String identifier,String code) {
		add(identifier);
		ELEMENT element = CollectionHelper.getLast(getElements());
		if(element == null)
			return this;
		if(code != null) {
			FieldHelper.write(element, FieldName.IDENTIFIER,ValueUsageType.BUSINESS, code);	
		}
		return this;
	}
	
}
