package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

public class TagLinkImpl extends AbstractTagImpl implements TagLink,Serializable {
	private static final long serialVersionUID = 1L;

	private String rel,type,href,sizes;
	
	@Override
	public String getRel() {
		return rel;
	}

	@Override
	public TagLink setRel(String rel) {
		this.rel = rel;
		return this;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public TagLink setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public String getHref() {
		return href;
	}

	@Override
	public TagLink setHref(String href) {
		this.href = href;
		return this;
	}
	
	@Override
	public String getSizes() {
		return sizes;
	}
	
	@Override
	public TagLink setSizes(String sizes) {
		this.sizes = sizes;
		return this;
	}
}
