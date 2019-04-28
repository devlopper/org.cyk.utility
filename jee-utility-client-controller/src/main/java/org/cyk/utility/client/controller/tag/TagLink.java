package org.cyk.utility.client.controller.tag;

public interface TagLink extends Tag {

	String getRel();
	TagLink setRel(String rel);
	
	String getType();
	TagLink setType(String type);
	
	String getHref();
	TagLink setHref(String href);
	
	String getSizes();
	TagLink setSizes(String sizes);
}
