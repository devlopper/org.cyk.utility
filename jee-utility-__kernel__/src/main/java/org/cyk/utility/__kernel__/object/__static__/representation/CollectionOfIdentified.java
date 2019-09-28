package org.cyk.utility.__kernel__.object.__static__.representation;

import java.util.List;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentified<IDENTIFIED,IDENTIFIER> extends Collection<IDENTIFIED> {

	default CollectionOfIdentified<IDENTIFIED,IDENTIFIER> add(IDENTIFIER identifier) {
		IDENTIFIED identified = instantiateElement();
		if(identified == null)
			return this;
		writeIdentifier(identified, identifier);
		add(List.of(identified));
		return this;
	}
	
	default void writeIdentifier(IDENTIFIED identified,IDENTIFIER identifier) {
		FieldHelper.write(identified, FieldName.IDENTIFIER,ValueUsageType.SYSTEM, identifier);
	}
}
