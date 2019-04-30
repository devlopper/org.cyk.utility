package org.cyk.utility.client.controller.web.jsf;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public interface Constant extends org.cyk.utility.client.controller.web.Constant {
	
	String RESOURCE_RELATIVE_URL_FORMAT = "%s/javax.faces.resource/%s.jsf?ln=%s&con=%s";
	
	static String formatResourceRelativeUrl(Object request,String name,String library,String contract) {
		if(DependencyInjection.inject(StringHelper.class).isBlank(contract))
			DependencyInjection.inject(ThrowableHelper.class).throwRuntimeException("theme contract is required");
		return String.format(RESOURCE_RELATIVE_URL_FORMAT, DependencyInjection.inject(RequestPropertyValueGetter.class).setRequest(request).setProperty(RequestProperty.CONTEXT)
				.execute().getOutput(),name,library,contract);
	}
}