package org.cyk.utility.client.controller.data.hierarchy;

import java.util.Collection;

public interface DataIdentifiedByString<NODE> extends org.cyk.utility.client.controller.data.DataIdentifiedByString {
	
	Collection<NODE> getParents();
	Collection<NODE> getParents(Boolean instanciateIfNull);
	DataIdentifiedByString<NODE> setParents(Collection<NODE> parents);
	DataIdentifiedByString<NODE> addParents(Collection<NODE> parents);
	DataIdentifiedByString<NODE> addParents(NODE...parents);
	
	Long getNumberOfParents();
	DataIdentifiedByString<NODE> setNumberOfParents(Long numberOfParents);
	
	Long getNumberOfChildren();
	DataIdentifiedByString<NODE> setNumberOfChildren(Long numberOfChildren);
	
	/**/
	
	String PROPERTY_PARENTS = "parents";
	String PROPERTY_NUMBER_OF_PARENTS = "numberOfParents";
	String PROPERTY_NUMBER_OF_CHILDREN = "numberOfChildren";
}
