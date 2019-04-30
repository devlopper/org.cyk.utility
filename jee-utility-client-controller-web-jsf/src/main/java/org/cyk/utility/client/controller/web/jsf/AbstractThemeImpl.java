package org.cyk.utility.client.controller.web.jsf;

import static org.cyk.utility.client.controller.web.jsf.Constant.formatResourceRelativeUrl;

import java.io.Serializable;

public abstract class AbstractThemeImpl extends org.cyk.utility.client.controller.web.AbstractThemeImpl implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __addFavicon__(Object request,String rel, String folder,String fileNameAndExtension, String mimeType,String size) {
		// We will get our favicons from resources
		__addTagLinkResource__(rel, mimeType, request, fileNameAndExtension,folder, size);
	}
	
	protected String __getResourceRelativeUrl__(Object request,String name,String library) {
		return formatResourceRelativeUrl(request, name, library, __getIdentifier__());
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library,String sizes,String contract) {
		__addTagLink__(rel, type, formatResourceRelativeUrl(request,name,library,contract),sizes);
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library,String sizes) {
		__addTagLinkResource__(rel, type, request, name, library, sizes, __getIdentifier__());
	}
	
	protected void __addTagLinkResource__(String rel,String type,Object request,String name,String library) {
		__addTagLinkResource__(rel, type, request, name, library, null);
	}
	
	protected void __addTagLinkResourceStyleSheet__(Object request,String name,String library) {
		__addTagLinkResource__("stylesheet", "text/css", request, name, library);
	}
	
	protected void __addTagScriptResource__(Object request,String name,String library) {
		getTagScripts(Boolean.TRUE).addBySrc(__getResourceRelativeUrl__(request, name,library));	
	}

	/**/
	
}
