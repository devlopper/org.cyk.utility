package org.cyk.utility.client.controller.tag;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface TagLinks extends CollectionInstance<TagLink> {

	TagLinks add(String rel,String type,String href,String sizes);
	TagLinks add(String rel,String type,String href);
	
}
