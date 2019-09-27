package org.cyk.utility.__kernel__.object.__static__.representation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface CollectionOfIdentifiedByStringAndCodedAndNamed<ELEMENT> extends CollectionOfIdentifiedByStringAndCoded<ELEMENT> {

	default CollectionOfIdentifiedByStringAndCodedAndNamed<ELEMENT> add(String identifier,String code,String name) {
		add(identifier,code);
		ELEMENT element = CollectionHelper.getLast(getElements());
		if(element == null)
			return this;
		if(name != null) {
			FieldHelper.write(element, FieldName.NAME,ValueUsageType.BUSINESS, name);
		}
		System.out.println(ToStringBuilder.reflectionToString(element));
		return this;
	}
	
}
