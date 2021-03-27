package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.value.Value;

public interface CodeExecutor extends SpecificFieldExecutor<String> {

	public static abstract class AbstractImpl extends SpecificFieldExecutor.AbstractImpl<String> implements CodeExecutor,Serializable {

		public AbstractImpl() {
			super(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, String.class);
		}
		
	}
	
	/**/
	
	static CodeExecutor getInstance() {
		return Helper.getInstance(CodeExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}