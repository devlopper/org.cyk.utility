package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped @Deprecated
public class NavigationHelperImpl extends AbstractHelper implements NavigationHelper,Serializable {
	private static final long serialVersionUID = 1L;

	public static String __buildIdentifier__(SystemAction systemAction) {
		Object[] arguments = new Object[2];
		if(systemAction != null) {
			if(systemAction.getEntities()!=null && systemAction.getEntities().getElementClass()!=null)
				arguments[0] = StringHelper.applyCase(systemAction.getEntities().getElementClass().getSimpleName(), Case.FIRST_CHARACTER_LOWER);
			else
				arguments[0] = __ENTITY__;
			
			if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete)
				arguments[1] = EDIT;
			else
				arguments[1] = StringUtils.substringBetween(systemAction.getClass().getSimpleName(), SystemAction.class.getSimpleName(), "Impl") ;
		}
		return String.format(IDENTIFIER_FORMAT, arguments);
	}
	
	/**/
	
	private static final String IDENTIFIER_FORMAT = "%s%sView";
	private static final String EDIT = "Edit";
	private static final String __ENTITY__ = "__entity__";

}
