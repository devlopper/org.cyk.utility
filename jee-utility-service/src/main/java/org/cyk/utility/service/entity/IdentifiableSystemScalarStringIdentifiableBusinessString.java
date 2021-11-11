package org.cyk.utility.service.entity;

public interface IdentifiableSystemScalarStringIdentifiableBusinessString extends IdentifiableSystemScalarString {

	String getCode();
	IdentifiableSystemScalarStringIdentifiableBusinessString setCode(String code);
	
	String PROPERTY_CODE = "code";
}