package org.cyk.utility.__kernel__.enumeration;

import org.cyk.utility.__kernel__.string.StringHelper;

public enum Action {

	CREATE
	,READ
	,UPDATE
	,DELETE
	
	,EDIT
	
	,LIST
	,TREAT
	;
	
	public static Action getByName(String name,Boolean isNameCaseSensitive) {
		if(StringHelper.isBlank(name))
			return null;
		return EnumerationHelper.getByName(Action.class,name,isNameCaseSensitive);
	}
	
	public static Action getByName(String name) {
		if(StringHelper.isBlank(name))
			return null;
		return getByName(name,Boolean.TRUE);
	}
	
	public static Action getByNameCaseInsensitive(String name) {
		if(StringHelper.isBlank(name))
			return null;
		return getByName(name,Boolean.FALSE);
	}
}
