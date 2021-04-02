package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.value.Value;

public interface IdentifierExecutor extends SpecificFieldExecutor<String> {
	
	public static abstract class AbstractImpl extends SpecificFieldExecutor.AbstractImpl<String> implements IdentifierExecutor,Serializable {

		public AbstractImpl() {
			super(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER, String.class);
		}
	}
	
	/**/
	
	static IdentifierExecutor getInstance() {
		return Helper.getInstance(IdentifierExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}