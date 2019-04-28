package org.cyk.utility.client.controller.tag;

import org.cyk.utility.collection.CollectionInstance;

public interface TagMetas extends CollectionInstance<TagMeta> {

	TagMetas addByHttpEquivByContent(String httpEquiv,String content);
	TagMetas addByNameByContent(String name,String content);
	
}
