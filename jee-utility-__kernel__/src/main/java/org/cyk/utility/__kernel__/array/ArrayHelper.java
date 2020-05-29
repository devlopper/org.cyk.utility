package org.cyk.utility.__kernel__.array;

import java.lang.reflect.Array;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface ArrayHelper {

	static Boolean isEmpty(Object[] objects) {
		if(objects == null)
			return Boolean.TRUE;
		return objects.length == 0;
	}
	
	static Boolean isNotEmpty(Object[] objects) {
		if(objects == null)
			return Boolean.FALSE;
		return !isEmpty(objects);
	}
	
	@SuppressWarnings("unchecked")
	static <ELEMENT> ELEMENT[] instantiate(Class<ELEMENT> klass,Collection<ELEMENT> collection) {
		if(klass == null || collection == null || collection.isEmpty())
			return null;
		ELEMENT[] array = (ELEMENT[]) Array.newInstance(klass, collection.size());
		Integer index = 0;
		for(ELEMENT element : collection)
			array[index++] = element;
		return array;
	}
	
	static <ELEMENT> ELEMENT[] instantiate(Class<ELEMENT> klass, CollectionInstance<ELEMENT> collectionInstance) {
		if(klass == null || collectionInstance == null || collectionInstance.isEmpty())
			return null;
		return instantiate(klass,collectionInstance.get());
	}
	
	static Object[] instantiate(Collection<Object> collection) {
		if(collection == null)
			return null;
		return instantiate(Object.class, collection);
	}
	
	static Integer getSize(Object[] objects) {
		if(objects == null)
			return 0;
		return objects.length;
	}
	
	static <T> T getElementAt(T[] objects,Integer index) {
		if(isEmpty(objects))
			return null;
		return objects[index];
	}
}
