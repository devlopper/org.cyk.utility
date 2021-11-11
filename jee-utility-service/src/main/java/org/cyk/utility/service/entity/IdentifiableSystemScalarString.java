package org.cyk.utility.service.entity;

public interface IdentifiableSystemScalarString {

	String getIdentifier();
	IdentifiableSystemScalarString setIdentifier(String identifier);

	String PROPERTY_IDENTIFIER = "identifier";
}