package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface CollectionInstance<T> extends Objectable {

	Collection<T> get();
	Collection<T> get(Integer begin,Integer end);
	Collection<T> get(Integer begin);
	Collection<T> getWithoutLast(Integer count);
	
	Collection<T> getIsInstanceOf(Collection<Class<?>> classes);
	Collection<T> getIsInstanceOf(Class<?>...classes);
	<I> Collection<I> getIsInstanceOfOne(Class<I> aClass);
	<I> I getIsInstanceOfOneByIdentifier(Class<I> aClass,Object identifier);
	
	CollectionInstance<T> set(Collection<T> collection);
	CollectionInstance<T> add(Collection<T> collection);
	CollectionInstance<T> add(T...elements);
	
	CollectionInstance<T> add(CollectionInstance<T> elements);
	
	CollectionInstance<T> addAt(T element,Integer index);
	
	CollectionInstance<T> addInstanceOf(Collection<?> collection);
	CollectionInstance<T> addInstanceOfArray(Object...elements);
	
	CollectionInstance<T> setIsDoNotAddNull(Boolean isDoNotAddNull);
	Boolean getIsDoNotAddNull();
	
	Boolean contains(T element);
	
	T getAt(Object index);
	T getFirst();
	<I> I getFirstInstanceOf(Class<I> elementClass);
	T getLast();
	
	Integer getSize();
	
	Class<?> getElementClass();
	CollectionInstance<T> setElementClass(Class<?> elementClass);
	
	Class<?> getCollectionClass();
	CollectionInstance<T> setCollectionClass(Class<?> collectionClass);
	
	T getByIdentifier(Object identifier);
	
	CollectionInstance<T> remove(T element);
	CollectionInstance<T> removeMany(Collection<T> elements);
	CollectionInstance<T> removeMany(T...elements);
	CollectionInstance<T> removeAll();
	CollectionInstance<T> removeDuplicate(java.util.function.Function<? super T, ?> function);
	
	Boolean isEmpty();
	Boolean isNotEmpty();

}
