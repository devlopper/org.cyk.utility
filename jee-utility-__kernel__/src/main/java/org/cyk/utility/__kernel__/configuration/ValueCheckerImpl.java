package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;

import org.cyk.utility.__kernel__.value.Checker;

public class ValueCheckerImpl implements org.cyk.utility.__kernel__.value.Checker,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isNull(Object value) {
		if(Checker.super.isNull(value))
			return Boolean.TRUE;
		if(value instanceof String && ((String)value).trim().startsWith("${"))
			return Boolean.TRUE;			
		return Boolean.FALSE;
	}	
	
	/**/
	
	public static final org.cyk.utility.__kernel__.value.Checker INSTANCE = new ValueCheckerImpl();
}