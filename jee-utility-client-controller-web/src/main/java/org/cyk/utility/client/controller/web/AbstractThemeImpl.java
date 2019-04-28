package org.cyk.utility.client.controller.web;

import java.io.Serializable;

public abstract class AbstractThemeImpl extends org.cyk.utility.client.controller.component.theme.AbstractThemeImpl implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

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
}
