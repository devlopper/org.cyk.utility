package org.cyk.utility.array;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.helper.Helper;

public interface ArrayHelper extends Helper {

	Boolean isEmpty(Object[] objects);
	Boolean isNotEmpty(Object[] objects);
	<ELEMENT> ELEMENT[] instanciate(Class<ELEMENT> aClass,Collection<ELEMENT> collection);
	<ELEMENT> ELEMENT[] instanciate(Class<ELEMENT> aClass,CollectionInstance<ELEMENT> collectionInstance);
	Object[] instanciate(Collection<Object> collection);
	Integer getSize(Object[] objects);
}
