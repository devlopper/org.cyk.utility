package org.cyk.utility.client.controller.tag;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class TagLinksImpl extends AbstractCollectionInstanceImpl<TagLink> implements TagLinks,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public TagLinks add(String rel, String type, String href, String sizes) {
		add(__inject__(TagLink.class).setRel(rel).setType(type).setHref(href).setSizes(sizes));
		return this;
	}

	@Override
	public TagLinks add(String rel, String type, String href) {
		return add(rel, type, href, null);
	}

}
