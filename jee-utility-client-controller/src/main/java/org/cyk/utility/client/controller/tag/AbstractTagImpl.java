package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractTagImpl extends AbstractObject implements Tag,Serializable{
	private static final long serialVersionUID = 1L;

	private String body;
	
	@Override
	public String getBody() {
		return body;
	}
	
	@Override
	public Tag setBody(String body) {
		this.body = body;
		return this;
	}
	
}
