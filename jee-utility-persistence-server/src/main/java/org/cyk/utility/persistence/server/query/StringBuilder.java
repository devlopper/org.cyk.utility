package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface StringBuilder {

	String build();	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements StringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	/**/
	
	static StringBuilder getInstance() {
		return Helper.getInstance(StringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}