package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

public class TagMetaImpl extends AbstractTagImpl implements TagMeta,Serializable {
	private static final long serialVersionUID = 1L;

	private String name,httpEquiv,content;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public TagMeta setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getHttpEquiv() {
		return httpEquiv;
	}

	@Override
	public TagMeta setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
		return this;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public TagMeta setContent(String content) {
		this.content = content;
		return this;
	}
	
}
