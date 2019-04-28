package org.cyk.utility.client.controller.tag;

public interface TagMeta extends Tag {

	String getName();
	TagMeta setName(String name);
	
	String getHttpEquiv();
	TagMeta setHttpEquiv(String httpEquiv);
	
	String getContent();
	TagMeta setContent(String content);
}
