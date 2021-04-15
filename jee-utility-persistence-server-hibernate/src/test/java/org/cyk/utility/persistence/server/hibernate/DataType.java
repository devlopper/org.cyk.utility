package org.cyk.utility.persistence.server.hibernate;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

@Entity
@NamedStoredProcedureQueries(value = {@NamedStoredProcedureQuery(name = "myproc", procedureName = "myproc")})
public class DataType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	public static String myStoredProcedure() {
		return null;
	}
	
}
