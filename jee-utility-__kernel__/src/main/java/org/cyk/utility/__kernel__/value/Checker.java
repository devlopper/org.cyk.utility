package org.cyk.utility.__kernel__.value;

import org.cyk.utility.__kernel__.Helper;

public interface Checker {

	default Boolean isNull(Object value) {
		if(value == null)
			return Boolean.TRUE;
		return Boolean.FALSE; 
	}
	
	/**/
	
	static Checker getInstance() {
		return Helper.getInstance(Checker.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
