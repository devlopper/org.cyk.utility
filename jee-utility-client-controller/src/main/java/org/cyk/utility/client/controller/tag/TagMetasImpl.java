package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class TagMetasImpl extends AbstractCollectionInstanceImpl<TagMeta> implements TagMetas,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public TagMetas addByHttpEquivByContent(String httpEquiv, String content) {
		add(__inject__(TagMeta.class).setHttpEquiv(httpEquiv).setContent(content));
		return this;
	}

	@Override
	public TagMetas addByNameByContent(String name, String content) {
		add(__inject__(TagMeta.class).setName(name).setContent(content));
		return this;
	}

}
