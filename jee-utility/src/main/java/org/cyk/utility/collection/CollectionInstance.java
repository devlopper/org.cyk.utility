package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface CollectionInstance<T> extends Objectable {

	Collection<T> get();
	Collection<T> getIsInstanceOf(Collection<Class<?>> classes);
	Collection<T> getIsInstanceOf(Class<?>...classes);
	
	CollectionInstance<T> set(Collection<T> collection);
	CollectionInstance<T> add(Collection<T> collection);
	CollectionInstance<T> add(T...elements);
	
	CollectionInstance<T> add(CollectionInstance<T> elements);
	
	CollectionInstance<T> addAt(T element,Integer index);
	
	CollectionInstance<T> addInstanceOf(Collection<?> collection);
	CollectionInstance<T> addInstanceOfArray(Object...elements);
	
	T getAt(Object index);
	T getFirst();
	T getLast();
	
	Integer getSize();
	
	Class<?> getElementClass();
	CollectionInstance<T> setElementClass(Class<?> elementClass);
}
