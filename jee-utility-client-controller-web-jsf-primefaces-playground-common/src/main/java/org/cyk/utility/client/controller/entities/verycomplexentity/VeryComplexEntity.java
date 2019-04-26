package org.cyk.utility.client.controller.entities.verycomplexentity;

import java.util.Collection;

import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;

public interface VeryComplexEntity extends DataIdentifiedByStringAndCoded {

	@Override VeryComplexEntity setIdentifier(Object identifier);
	@Override VeryComplexEntity setCode(String code);
	
	String getFirstName();
	VeryComplexEntity setFirstName(String firstName);
	
	String getLastNames();
	VeryComplexEntity setLastNames(String lastNames);
	
	String getDescription();
	VeryComplexEntity setDescription(String description);
	
	String getElectronicMailAddress();
	VeryComplexEntity setElectronicMailAddress(String electronicMailAddress);
	
	String getPhoneNumber();
	VeryComplexEntity setPhoneNumber(String phoneNumber);
	
	String getPostalBox();
	VeryComplexEntity setPostalBox(String postalBox);
	
	VeryComplexEntityEnum01 getEnumeration01();
	VeryComplexEntity setEnumeration01(VeryComplexEntityEnum01 enumeration01);

	Collection<VeryComplexEntityEnum01> getEnumerations01();
	VeryComplexEntity setEnumerations01(Collection<VeryComplexEntityEnum01> enumerations01);
	
	VeryComplexEntityEnum02 getEnumeration02();
	VeryComplexEntity setEnumeration02(VeryComplexEntityEnum02 enumeration02);

	Collection<VeryComplexEntityEnum02> getEnumerations02();
	VeryComplexEntity setEnumerations02(Collection<VeryComplexEntityEnum02> enumerations02);
	
	Boolean getBooleanValueCheckBox();
	VeryComplexEntity setBooleanValueCheckBox(Boolean booleanValueCheckBox);
	
	Boolean getBooleanValueButton();
	VeryComplexEntity setBooleanValueButton(Boolean booleanValueButton);
	
	
	
	
	
	/**/
	
	public static final String PROPERTY_FIRST_NAME = "firstName";
	public static final String PROPERTY_LAST_NAMES = "lastNames";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_BOOLEAN_VALUE_CHECK_BOX = "booleanValueCheckBox";
	public static final String PROPERTY_BOOLEAN_VALUE_BUTTON = "booleanValueButton";
	public static final String PROPERTY_ELECTRONIC_MAIL_ADDRESS = "electronicMailAddress";
	public static final String PROPERTY_PHONE_NUMBER = "phoneNumber";
	public static final String PROPERTY_POSTAL_BOX = "postalBox";
	public static final String PROPERTY_ENUMERATION_01 = "enumeration01";
	public static final String PROPERTY_ENUMERATIONS_01 = "enumerations01";
	public static final String PROPERTY_ENUMERATION_02 = "enumeration02";
	public static final String PROPERTY_ENUMERATIONS_02 = "enumerations02";
}
