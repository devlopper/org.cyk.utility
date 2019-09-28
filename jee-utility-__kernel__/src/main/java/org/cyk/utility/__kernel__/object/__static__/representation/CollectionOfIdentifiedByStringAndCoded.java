package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentifiedByStringAndCoded<IDENTIFIED> extends CollectionOfIdentifiedByString<IDENTIFIED> {

	default CollectionOfIdentifiedByStringAndCoded<IDENTIFIED> add(String identifier,String code) {
		add(identifier);
		IDENTIFIED identified = CollectionHelper.getLast(getElements());
		if(identified == null)
			return this;
		writeCode(identified, code);
		return this;
	}
	
	default void writeCode(IDENTIFIED identified,String code) {
		FieldHelper.write(identified, FieldName.IDENTIFIER,ValueUsageType.BUSINESS, code);
	}
}
