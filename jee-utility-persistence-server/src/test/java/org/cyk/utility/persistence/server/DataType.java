package org.cyk.utility.persistence.server;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
@NamedStoredProcedureQueries(value = {@NamedStoredProcedureQuery(name = "myproc", procedureName = "myproc")})
public class DataType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	private String description;
	
	@Transient private String codeAndName;
	
	public static String myStoredProcedure() {
		return null;
	}
	
	public static final String FIELD_CODE_AND_NAME = "codeAndName";
}