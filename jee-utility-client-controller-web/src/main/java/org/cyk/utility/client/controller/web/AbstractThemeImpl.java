package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.file.MimeTypeGetter;

public abstract class AbstractThemeImpl extends org.cyk.utility.client.controller.component.theme.AbstractThemeImpl implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public org.cyk.utility.client.controller.component.theme.Theme build() {
		org.cyk.utility.client.controller.component.theme.Theme theme = super.build();
		Object request = getRequest();
		
		getTagMetas(Boolean.TRUE)
		.addByHttpEquivByContent("X-UA-Compatible", "IE=edge")
		.addByHttpEquivByContent("Content-Type","text/html; charset=UTF-8" )
		.addByHttpEquivByContent("Cache-control", "public")
		.addByHttpEquivByContent("Cache-control", "max-age=864000")
		.addByHttpEquivByContent("Cache-control", "s-maxage=1296000")
		
		.addByNameByContent("viewport", "width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0")
		.addByNameByContent("mobile-web-app-capable", "yes")
		.addByNameByContent("apple-mobile-web-app-capable", "yes")
		
		//.addByNameByContent("theme-color", "#444")
		
		;
	
		__addFavicons__(request);
		
		//"#{request.contextPath}/javax.faces.resource/#{indexFile.properties.name}.jsf?ln=#{indexFile.properties.library}&amp;con=#{indexFile.properties.contracts}"
		
		getTagLinks(Boolean.TRUE)
			//.add("shortcut icon","image/x-icon","#{resource['favicon/favicon.ico']}")
			//.add("manifest","text/json","manifest.json")
			;
		
		
		/*
		<link rel="shortcut icon" type="image/x-icon" href="#{resource['favicon/favicon.ico']}"/>
	    <link rel="shortcut icon" type="image/x-icon" href="#{resource['favicon/favicon-16x16.png']}" sizes="16x16"/>
	    <link rel="shortcut icon" type="image/x-icon" href="#{resource['favicon/favicon-32x32.png']}" sizes="32x32"/>
	    <link rel="shortcut icon" type="image/x-icon" href="#{resource['favicon/favicon-96x96.png']}" sizes="96x96"/>
	    
	    */
		
		return theme;
	}
	
	/* Favicons */
	
	protected void __addFavicons__(Object request) {
		for(Integer index : new Integer[] {16,32,64,128,256})
			__addFavicon__(request, index);
	}
	
	protected void __addFavicon__(Object request,Integer width,Integer height) {
		String size = width+"x"+height;
		String fileName = __getFaviconFileName__(request, width, height);
		String fileExtension = __getFaviconFileExtension__(request, width, height);
		if(StringHelper.isNotBlank(fileExtension))
			fileName += "." + fileExtension;
		String mimeType = __inject__(MimeTypeGetter.class).setExtension(fileExtension).execute().getOutput();
		String folder = __getImageFolder__(request);
		__addFavicon__(request,__getFaviconRel(request, width, height), folder,fileName, mimeType,size);
	}
	
	protected void __addFavicon__(Object request,Integer size) {
		__addFavicon__(request, size, size);
	}
	
	protected void __addFavicon__(Object request,String rel,String folder,String fileNameAndExtension,String mimeType,String size) {
		
	}
	
	protected String __getFaviconRel(Object request,Integer width,Integer height) {
		return "icon";
	}
	
	protected String __getFaviconFileExtension__(Object request,Integer width,Integer height) {
		return __getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_NAME_EXTENSION, "png");
	}
	
	protected String __getFaviconFileNamePrefix__(Object request,Integer width,Integer height) {
		return __getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_NAME_PREFIX, "favicon");
	}
	
	protected String __getFaviconFileName__(Object request,Integer width,Integer height) {
		return __getFaviconFileNamePrefix__(request, width, height)+width+"x"+height;
	}
	
	protected String __getImageFolder__(Object request) {
		return __getConfigurationParameterValue__(Constant.CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_FOLDER, "image");
	}
	
	protected void __addTagMetaByHttpEquivByContent__(String httpEquiv, String content) {
		getTagMetas(Boolean.TRUE).addByHttpEquivByContent(httpEquiv, content);
	}
	
	protected void __addTagMetaByNameByContent__(String name, String content) {
		getTagMetas(Boolean.TRUE).addByNameByContent(name, content);
	}
	
	protected void __addTagLink__(String rel, String type, String href, String sizes) {
		getTagLinks(Boolean.TRUE).add(rel, type, href, sizes);
	}
	
	protected void __addTagLink__(String rel, String type, String href) {
		__addTagLink__(rel, type, href, null);
	}
	
	protected void __addTagLinkStyleSheet__(String href) {
		__addTagLink__("stylesheet", "text/css", href);
	}
	
	@Override
	protected Object __getContext__(Object request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) getRequest();
		return httpServletRequest == null ? null : httpServletRequest.getServletContext();
	}
	
	/**/
	
}
