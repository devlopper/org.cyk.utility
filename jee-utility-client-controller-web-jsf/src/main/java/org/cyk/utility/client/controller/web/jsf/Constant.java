package org.cyk.utility.client.controller.web.jsf;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.identifier.resource.RequestProperty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.request.RequestPropertyValueGetter;

public interface Constant extends org.cyk.utility.client.controller.web.Constant {
	
	String RESOURCE_RELATIVE_URL_FORMAT = "%s/javax.faces.resource/%s.jsf?ln=%s&con=%s";
	
	static String formatResourceRelativeUrl(Object request,String name,String library,String contract) {
		if(StringHelper.isBlank(contract))
			throw new RuntimeException("theme contract is required");
		return String.format(RESOURCE_RELATIVE_URL_FORMAT, DependencyInjection.inject(RequestPropertyValueGetter.class).setRequest(request).setProperty(RequestProperty.CONTEXT)
				.execute().getOutput(),name,library,contract);
	}
}