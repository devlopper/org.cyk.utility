package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.util.Collection;

import org.cyk.utility.client.controller.data.Data;

public interface VerySimpleEntity extends Data {

	@Override VerySimpleEntity setIdentifier(Object identifier);
	@Override VerySimpleEntity setCode(String code);
	
	String getName();
	VerySimpleEntity setName(String name);
	
	String getDescription();
	VerySimpleEntity setDescription(String description);
	
	VerySimpleEntityDetails getDetails();
	VerySimpleEntity setDetails(VerySimpleEntityDetails details);
	
	Collection<VerySimpleEntityDetails> getDetailsCollection();
	VerySimpleEntity setDetailsCollection(Collection<VerySimpleEntityDetails> detailsCollection);
	
	VerySimpleEntityEnum getEnumeration();
	VerySimpleEntity setEnumeration(VerySimpleEntityEnum enumeration);
	
	Boolean getBooleanValueCheckBox();
	VerySimpleEntity setBooleanValueCheckBox(Boolean booleanValueCheckBox);
	
	Boolean getBooleanValueButton();
	VerySimpleEntity setBooleanValueButton(Boolean booleanValueButton);
	
	VerySimpleEntityEnum getEnumerationRadio();
	VerySimpleEntity setEnumerationRadio(VerySimpleEntityEnum enumerationRadio);
	
	Collection<VerySimpleEntityEnum> getEnumerations();
	VerySimpleEntity setEnumerations(Collection<VerySimpleEntityEnum> enumerations);
	
	/**/
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_DETAILS = "details";
	public static final String PROPERTY_DETAILS_COLLECTION = "detailsCollection";
	public static final String PROPERTY_BOOLEAN_VALUE_CHECK_BOX = "booleanValueCheckBox";
	public static final String PROPERTY_BOOLEAN_VALUE_BUTTON = "booleanValueButton";
	public static final String PROPERTY_ENUMERATION = "enumeration";
	public static final String PROPERTY_ENUMERATION_RADIO = "enumerationRadio";
	public static final String PROPERTY_ENUMERATIONS = "enumerations";
}
