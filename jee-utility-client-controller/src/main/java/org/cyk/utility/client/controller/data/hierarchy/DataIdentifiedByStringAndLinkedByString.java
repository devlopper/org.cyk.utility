package org.cyk.utility.client.controller.data.hierarchy;

public interface DataIdentifiedByStringAndLinkedByString<NODE> extends DataIdentifiedByString<NODE> {
	
	String getLink();
	DataIdentifiedByStringAndLinkedByString<NODE> setLink(String link);
	
	public static final String PROPERTY_LINK = "link";
	
}
