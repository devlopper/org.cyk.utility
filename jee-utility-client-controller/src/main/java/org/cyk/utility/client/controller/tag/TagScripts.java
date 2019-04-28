package org.cyk.utility.client.controller.tag;

import org.cyk.utility.collection.CollectionInstance;

public interface TagScripts extends CollectionInstance<TagScript> {

	TagScripts addBySrc(String src);
	TagScripts addBody(String body);
	
}
