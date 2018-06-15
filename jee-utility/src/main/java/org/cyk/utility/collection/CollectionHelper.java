package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface CollectionHelper extends Helper {

	Boolean isEmpty(Collection<?> collection);
	Boolean isNotEmpty(Collection<?> collection);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,@SuppressWarnings("unchecked") ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> instanciate(@SuppressWarnings("unchecked") ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections);
	<ELEMENT> Collection<ELEMENT> concatenate(@SuppressWarnings("unchecked") Collection<ELEMENT>...collections);
	Integer getSize(Collection<?> collection);
	<ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection, Integer index);
	<ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection);
	<ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection);
	CollectionHelper clear(Collection<?> collection);
	<ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass, Collection<?> collection);
}
