package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

public class TagScriptImpl extends AbstractTagImpl implements TagScript,Serializable {
	private static final long serialVersionUID = 1L;

	private String src;
	
	@Override
	public String getSrc() {
		return src;
	}

	@Override
	public TagScript setSrc(String src) {
		this.src = src;
		return this;
	}
	
	@Override
	public TagScript setBody(String body) {
		return (TagScript) super.setBody(body);
	}
}
