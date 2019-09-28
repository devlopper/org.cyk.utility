package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentifiedByStringAndCodedAndNamed<IDENTIFIED> extends CollectionOfIdentifiedByStringAndCoded<IDENTIFIED> {

	default CollectionOfIdentifiedByStringAndCodedAndNamed<IDENTIFIED> add(String identifier,String code,String name) {
		add(identifier,code);
		IDENTIFIED identified = CollectionHelper.getLast(getElements());
		if(identified == null)
			return this;
		writeName(identified, name);
		return this;
	}
	
	default void writeName(IDENTIFIED identified,String name) {
		FieldHelper.write(identified, FieldName.NAME,ValueUsageType.BUSINESS, name);
	}
}
