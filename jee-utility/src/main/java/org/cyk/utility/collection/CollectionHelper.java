package org.cyk.utility.collection;

import java.util.Collection;

public interface CollectionHelper {

	Boolean isEmpty(Collection<?> collection);
	Boolean isNotEmpty(Collection<?> collection);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,@SuppressWarnings("unchecked") ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> instanciate(@SuppressWarnings("unchecked") ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections);
	<ELEMENT> Collection<ELEMENT> concatenate(@SuppressWarnings("unchecked") Collection<ELEMENT>...collections);
}
