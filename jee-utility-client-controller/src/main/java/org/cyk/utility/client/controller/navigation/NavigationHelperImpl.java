package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterNameStringBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierParameterValueStringBuilder;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.object.Objects;
import org.cyk.utility.resource.locator.UrlBuilder;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringFormat;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.throwable.ThrowableHelperImpl;

@ApplicationScoped
public class NavigationHelperImpl extends AbstractHelper implements NavigationHelper,Serializable {
	private static final long serialVersionUID = 1L;

	public static String __buildIdentifier__(SystemAction systemAction) {
		Object[] arguments = new Object[2];
		if(systemAction != null) {
			if(systemAction.getEntities()!=null && systemAction.getEntities().getElementClass()!=null)
				arguments[0] = StringHelperImpl.__applyCase__(systemAction.getEntities().getElementClass().getSimpleName(), Case.FIRST_CHARACTER_LOWER);
			else
				arguments[0] = __ENTITY__;
			
			if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete)
				arguments[1] = EDIT;
			else
				arguments[1] = StringUtils.substringBetween(systemAction.getClass().getSimpleName(), SystemAction.class.getSimpleName(), "Impl") ;
		}
		return String.format(IDENTIFIER_FORMAT, arguments);
	}
	
	private static void __process__(NavigationIdentifierToUrlStringMapper identifierToUrlStringMapper,Object context,Object uniformResourceLocatorMap) {
		identifierToUrlStringMapper.setPropertyIfNull(Properties.CONTEXT,context);
		identifierToUrlStringMapper.setPropertyIfNull(Properties.UNIFORM_RESOURCE_LOCATOR_MAP,uniformResourceLocatorMap);
	}
	
	/**/
	
	private static final String IDENTIFIER_FORMAT = "%s%sView";
	private static final String EDIT = "Edit";
	private static final String __ENTITY__ = "__entity__";

}
