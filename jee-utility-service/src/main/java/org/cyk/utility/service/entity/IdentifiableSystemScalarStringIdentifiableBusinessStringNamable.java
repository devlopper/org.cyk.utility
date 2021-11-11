package org.cyk.utility.service.entity;

public interface IdentifiableSystemScalarStringIdentifiableBusinessStringNamable extends IdentifiableSystemScalarStringIdentifiableBusinessString {

	String getName();
	IdentifiableSystemScalarStringIdentifiableBusinessStringNamable setName(String name);
	
	String PROPERTY_NAME = "name";
}