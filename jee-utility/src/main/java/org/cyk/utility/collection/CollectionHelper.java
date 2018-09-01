package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface CollectionHelper extends Helper {

	Boolean isEmpty(Collection<?> collection);
	Boolean isNotEmpty(Collection<?> collection);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> instanciate(ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<ELEMENT>...collections);
	Integer getSize(Collection<?> collection);
	<ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection, Integer index);
	<ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection);
	<ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection);
	CollectionHelper clear(Collection<?> collection);
	<ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass, Collection<?> collection);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, Boolean append,Collection<ELEMENT> elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, Boolean append, Collection<ELEMENT> elements);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, Boolean append,ELEMENT[] elements);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, ELEMENT[] elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, Boolean append, ELEMENT[] elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, ELEMENT[] elements);
	
	<ELEMENT> Collection<ELEMENT> addElementAt(Collection<ELEMENT> collection,Integer index, ELEMENT element);
	
	Boolean contains(Collection<?> collection,Object element);
}
