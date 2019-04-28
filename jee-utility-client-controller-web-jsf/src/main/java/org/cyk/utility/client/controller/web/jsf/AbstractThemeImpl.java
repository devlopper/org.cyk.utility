package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractThemeImpl extends org.cyk.utility.client.controller.web.AbstractThemeImpl implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	protected String __getResourceRelativeUrl__(Object request,String name,String library,String contract) {
		if(__inject__(StringHelper.class).isBlank(contract))
			contract = __getIdentifier__();
		return String.format(RESOURCE_RELATIVE_URL_FORMAT, __inject__(RequestPropertyValueGetter.class).setRequest(request).setProperty(RequestProperty.CONTEXT).execute()
				.getOutput(),name,library,contract);
	}
	
	protected String __getResourceRelativeUrl__(Object request,String name,String library) {
		return __getResourceRelativeUrl__(request, name, library, __getIdentifier__());
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library,String sizes,String contract) {
		__addTagLink__(rel, type, __getResourceRelativeUrl__(request,name,library,contract),sizes);
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library,String sizes) {
		__addTagLinkResource__(rel, type, request, name, library, sizes, __getIdentifier__());
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library) {
		__addTagLinkResource__(rel, type, request, name, library, null);
	}
	
	/**/
	
	private static final String RESOURCE_RELATIVE_URL_FORMAT = "%s/javax.faces.resource/%s.jsf?ln=%s&con=%s";
}
