package org.cyk.utility.__kernel__.identifier.resource;

public enum Component {

	SCHEME
	,USER
	,PASSWORD
	,HOST
	,PORT
	,PATH
	,QUERY
	,FRAGMENT
	
	;
	
	private Object value;
	
	public Object getValue() {
		return value;
	}
	
	public Component setValue(Object value) {
		this.value = value;
		return this;
	}
	
	/**/
	
	public static String PATH_ROOT;
}
