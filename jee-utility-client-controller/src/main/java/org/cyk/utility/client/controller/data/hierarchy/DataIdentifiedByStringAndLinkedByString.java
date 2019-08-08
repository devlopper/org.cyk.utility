package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndLinkedByString extends DataIdentifiedByString {
	
	String getLink();
	DataIdentifiedByStringAndLinkedByString setLink(String link);
	
	public static final String PROPERTY_LINK = "link";
	
}
