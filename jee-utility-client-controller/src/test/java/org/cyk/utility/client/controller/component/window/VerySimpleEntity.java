package org.cyk.utility.client.controller.component.window;

import java.util.Collection;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;

public interface VerySimpleEntity extends DataIdentifiedByStringAndCoded {

	@Override VerySimpleEntity setIdentifier(String identifier);
	@Override VerySimpleEntity setCode(String code);
	
	String getName();
	VerySimpleEntity setName(String name);
	
	String getDescription();
	VerySimpleEntity setDescription(String description);
	
	VerySimpleEntityDetails getDetails();
	VerySimpleEntity setDetails(VerySimpleEntityDetails details);
	
	VerySimpleEntityEnum getEnumeration();
	VerySimpleEntity setEnumeration(VerySimpleEntityEnum enumeration);
	
	Collection<VerySimpleEntityEnum> getEnumerations();
	VerySimpleEntity setEnumerations(Collection<VerySimpleEntityEnum> enumerations);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_DETAILS = "details";
	public static final String PROPERTY_ENUMERATION = "enumeration";
	public static final String PROPERTY_ENUMERATIONS = "enumerations";
}
