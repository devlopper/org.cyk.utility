package org.cyk.utility.client.controller.tag;

public interface TagScript extends Tag {

	String getSrc();
	TagScript setSrc(String src);

	@Override TagScript setBody(String body);
}
