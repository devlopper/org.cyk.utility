package org.cyk.utility.persistence.server;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class DataType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	private String description;
	
	@Transient private String codeAndName;
	
	public static final String FIELD_CODE_AND_NAME = "codeAndName";
}
