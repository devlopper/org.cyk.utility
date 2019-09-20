package org.cyk.utility.__kernel__;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface CollectionHelper {

	static <T> void addToCollection(Collection<T> collection, Collection<T> elements) {
		if(collection!=null && elements!=null) {
			collection.addAll(elements);
		}
	}
	
	static <T> void addToCollection(Collection<T> collection, @SuppressWarnings("unchecked") T... elements) {
		if(collection!=null && elements!=null) {
			addToCollection(collection, Arrays.asList(elements));
		}
	}
	
	static <T> T getElementAt(Collection<T> collection, Integer index) {
		T object = null;
		if(index!=null && index >=0 &&  collection!=null && index < collection.size()) {
			if(collection instanceof List)
				object = ((List<T>)collection).get(index.intValue());
			else {
				Iterator<T> iterator = collection.iterator();
				object = iterator.next();
				Integer count = 0;
				while(count++<index)
					object = iterator.next();
			}
		}
		return object;
	}
	
	static <T> T getElementAtEnd(Collection<T> collection) {
		return collection == null || collection.isEmpty() ? null : getElementAt(collection,collection.size()-1);
	}
	
}
