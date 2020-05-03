package org.cyk.utility.__kernel__.user.interface_;

import org.cyk.utility.__kernel__.enumeration.EnumerationHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public enum UserInterfaceAction {

	EXECUTE_FUNCTION
	,NAVIGATE_TO_VIEW
	,OPEN_VIEW_IN_DIALOG
	,RETURN_FROM_VIEW_IN_DIALOG
	,SHOW_DIALOG
	
	;
	
	public static UserInterfaceAction getByName(String name,Boolean isNameCaseSensitive) {
		if(StringHelper.isBlank(name))
			return null;
		return EnumerationHelper.getByName(UserInterfaceAction.class,name,isNameCaseSensitive);
	}
	
	public static UserInterfaceAction getByName(String name) {
		if(StringHelper.isBlank(name))
			return null;
		return getByName(name,Boolean.TRUE);
	}
	
	public static UserInterfaceAction getByNameCaseInsensitive(String name) {
		if(StringHelper.isBlank(name))
			return null;
		return getByName(name,Boolean.FALSE);
	}
}