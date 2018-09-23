package org.cyk.utility.collection;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface CollectionInstance<T> extends Objectable {

	Collection<T> get();
	CollectionInstance<T> set(Collection<T> collection);
	CollectionInstance<T> add(Collection<T> collection);
	CollectionInstance<T> add(T...elements);
	
	T getAt(Object index);
	T getFirst();
	T getLast();
}
