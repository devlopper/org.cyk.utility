package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class TagScriptsImpl extends AbstractCollectionInstanceImpl<TagScript> implements TagScripts,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public TagScripts addBySrc(String src) {
		add(__inject__(TagScript.class).setSrc(src));
		return this;
	}
	
	@Override
	public TagScripts addBody(String body) {
		add(__inject__(TagScript.class).setBody(body));
		return this;
	}

}
