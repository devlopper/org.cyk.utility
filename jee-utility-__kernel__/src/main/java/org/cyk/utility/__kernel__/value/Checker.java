package org.cyk.utility.__kernel__.value;

public interface Checker {

	default Boolean isNull(Object value) {
		if(value == null)
			return Boolean.TRUE;
		return Boolean.FALSE; 
	}
	
	/**/
	
	Checker INSTANCE = new Checker() {
		
	};
	
}
