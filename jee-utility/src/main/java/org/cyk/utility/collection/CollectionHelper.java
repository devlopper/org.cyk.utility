package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface CollectionHelper extends Helper {

	Boolean isEmpty(Collection<?> collection);
	Boolean isNotEmpty(Collection<?> collection);
	Boolean isEmpty(CollectionInstance<?> collectionInstance);
	Boolean isNotEmpty(CollectionInstance<?> collectionInstance);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> instanciate(ELEMENT...elements);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections);
	<ELEMENT> Collection<ELEMENT> concatenate(Collection<ELEMENT>...collections);
	Integer getSize(Collection<?> collection);
	Integer getSize(CollectionInstance<?> collectionInstance);
	
	<ELEMENT> CollectionHelper setElementAt(Collection<ELEMENT> collection, Object index,ELEMENT value);
	<ELEMENT> CollectionHelper setElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index,ELEMENT value);
	
	<ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection, Object index);
	<ELEMENT> ELEMENT getElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index);
	<ELEMENT> Collection<ELEMENT> getElementsFromTo(Collection<ELEMENT> collection,Integer begin,Integer end);
	<ELEMENT> Collection<ELEMENT> getElementsFrom(Collection<ELEMENT> collection,Integer begin);
	<ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection);
	<ELEMENT> ELEMENT getFirst(CollectionInstance<ELEMENT> collection);
	<ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection);
	CollectionHelper clear(Collection<?> collection);
	<ELEMENT> CollectionHelper swap(Collection<ELEMENT> collection,Object index01,Object index02);
	<ELEMENT> CollectionHelper swap(CollectionInstance<ELEMENT> collectionInstance,Object index01,Object index02);
	<ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass, Collection<?> collection);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, Boolean append,Collection<ELEMENT> elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, Boolean append, Collection<ELEMENT> elements);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, Boolean append,ELEMENT[] elements);
	<COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass, Collection<ELEMENT> collection, ELEMENT[] elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, Boolean append, ELEMENT[] elements);
	<ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection, ELEMENT[] elements);
	
	<ELEMENT> Collection<ELEMENT> addElementAt(Collection<ELEMENT> collection,Object index, ELEMENT element);
	
	Boolean contains(Collection<?> collection,Object element);
	Boolean containsAll(Collection<?> collection, Collection<?> elements);
	Boolean contains(CollectionInstance<?> collectionInstance,Object element);
	
	<ELEMENT> Collection<ELEMENT> removeNullValue(Collection<ELEMENT> collection);
	
	<ELEMENT> Collection<ELEMENT> removeDuplicate(Collection<ELEMENT> collection,java.util.function.Function<? super ELEMENT, ?> function);
}
