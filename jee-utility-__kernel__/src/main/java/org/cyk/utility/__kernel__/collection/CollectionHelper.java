package org.cyk.utility.__kernel__.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;

import one.util.streamex.StreamEx;

public interface CollectionHelper {

	static <T> Collection<T> instantiate(Class<?> klass,@SuppressWarnings("unchecked") T...elements) {
		if(klass == null || elements == null || elements.length == 0)
			return null;
		if(klass.equals(List.class))
			return List.of(elements);
		if(klass.equals(Set.class))
			return Set.of(elements);
		throw new RuntimeException("instantiate collection of type "+klass+" not yet handled");
	}
	
	@SuppressWarnings("unchecked")
	static <T> List<T> listOf(T...elements) {
		if(elements == null || elements.length == 0)
			return null;
		return (List<T>) instantiate(List.class,elements);
	}
	
	@SuppressWarnings("unchecked")
	static <T> Set<T> setOf(T...elements) {
		if(elements == null || elements.length == 0)
			return null;
		return (Set<T>) instantiate(Set.class,elements);
	}
	
	static Boolean isEmpty(Collection<?> collection){
		if(collection == null)
			return Boolean.TRUE;
		return collection.isEmpty();
	}
	
	static Boolean isNotEmpty(Collection<?> collection){
		if(collection == null)
			return Boolean.FALSE;
		return !isEmpty(collection);
	}
	
	static Boolean isEmpty(CollectionInstance<?> collectionInstance){
		if(collectionInstance == null)
			return Boolean.TRUE;
		return collectionInstance.isEmpty();
	}
	
	static Boolean isNotEmpty(CollectionInstance<?> collectionInstance){
		if(collectionInstance == null)
			return Boolean.FALSE;
		return collectionInstance.isNotEmpty();
	}
	
	static Integer getSize(Collection<?> collection) {
		if(collection == null)
			return 0;
		return collection.size();
	}
	
	static Integer getSize(CollectionInstance<?> collectionInstance) {
		if(collectionInstance == null)
			return 0;
		return collectionInstance.getSize();
	}
	
	static <ELEMENT> void setElementAt(Collection<ELEMENT> collection, Object index, ELEMENT value) {
		Integer indexValue = index instanceof Integer ? (Integer)index :  NumberHelper.getInteger(index);
		if(isEmpty(collection) || indexValue == null || indexValue < 0 || indexValue >= getSize(collection))
			return;
		if(collection instanceof List)
			((List<ELEMENT>)collection).set(indexValue.intValue(),value);
		else
			throw new RuntimeException("collection helper : set at index in collection of type "+collection.getClass()+" not yet implemented");
	}

	static <ELEMENT> void setElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index,ELEMENT value) {
		Integer indexValue = index instanceof Integer ? (Integer)index :  NumberHelper.getInteger(index);
		if(isEmpty(collectionInstance) || indexValue == null || indexValue < 0 || indexValue >= getSize(collectionInstance))
			return;
		setElementAt(collectionInstance.get(),index,value);
	}
	
	static <ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection,Object index){
		Integer indexValue = index instanceof Integer ? (Integer)index :  NumberHelper.getInteger(index);
		if(isEmpty(collection) || indexValue == null || indexValue < 0 || indexValue >= getSize(collection))
			return null;
		if(collection instanceof List)
			return ((List<ELEMENT>)collection).get(indexValue.intValue());
		java.util.Iterator<ELEMENT> iterator = collection.iterator();
		Integer count = 0;
		ELEMENT element = null;		
		while (count++ <= indexValue)
			element = iterator.next();
		return element;
	}
	
	static <ELEMENT> ELEMENT getElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index) {
		Integer indexValue = index instanceof Integer ? (Integer)index :  NumberHelper.getInteger(index);
		if(isEmpty(collectionInstance) || indexValue == null || indexValue >= getSize(collectionInstance))
			return null;
		return getElementAt(collectionInstance.get(), index);
	}
	
	static <ELEMENT> Collection<ELEMENT> getElementsFromTo(Collection<ELEMENT> collection,Integer begin, Integer end) {
		/*
		 * end is exclusive
		 */
		if(isEmpty(collection))
			return null;		
		if(begin==null || begin < 0)
			begin = 0;
		if(end==null || end < 0)
			end = collection.size();
		if(collection instanceof List)
			return ((List<ELEMENT>)collection).subList(begin, end);
		throw new RuntimeException("Cannot get elements from "+begin+" to "+end+" of type "+collection.getClass());
	}
	
	static <ELEMENT> Collection<ELEMENT> getElementsFrom(Collection<ELEMENT> collection,Integer begin) {
		if(isEmpty(collection))
			return null;		
		if(begin==null || begin < 0)
			begin = 0;
		return getElementsFromTo(collection,begin,null);
	}
	
	static <ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		if(collection instanceof List)
			return ((List<ELEMENT>)collection).get(0);
		return collection.iterator().next();
	}
	
	static <ELEMENT> ELEMENT getFirst(CollectionInstance<ELEMENT> collectionInstance) {
		if(isEmpty(collectionInstance))
			return null;
		return getFirst(collectionInstance.get());
	}
	
	static <ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		if(!(collection instanceof List))
			collection = new ArrayList<>(collection);
		return ((List<ELEMENT>)collection).get(((List<ELEMENT>)collection).size()-1);
	}
	
	static void clear(Collection<?> collection){
		if(isEmpty(collection))
			return;
		collection.clear();
	}
	
	@SuppressWarnings("unchecked")
	static <ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass,Collection<?> collection){
		return (Collection<ELEMENT>) collection;
	}
	
	static <ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections) {
		if(isEmpty(collections))
			return null;
		Collection<ELEMENT> collection = new ArrayList<ELEMENT>();
		for(Collection<ELEMENT> index : collections)
			if(isNotEmpty(index))
				collection.addAll(index);		
		return collection;
	}
	
	static <ELEMENT> Collection<ELEMENT> concatenate(@SuppressWarnings("unchecked") Collection<ELEMENT>...collections) {
		if(collections == null || collections.length == 0)
			return null;
		return concatenate(List.of(collections));
	}	
	
	static <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		Collection<ELEMENT> result = Boolean.TRUE.equals(append) ? collection : null;
		if(result==null)
			result = Set.class.isAssignableFrom(collectionClass) ? new LinkedHashSet<ELEMENT>() : new ArrayList<ELEMENT>();
		if(Boolean.TRUE.equals(append))
			;
		else
			if(collection!=null)
				result.addAll(collection);
		if(isNotEmpty(elements))
			result.addAll(elements);
		return result;
	}
	
	static <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		return add(List.class, collection, append, elements);
	}
	
	static <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,ELEMENT[] elements){
		return add(collectionClass,collection,append,List.of(elements));
	}
	
	static <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,ELEMENT[] elements){
		return add(collectionClass,collection,Boolean.TRUE,List.of(elements));
	}
	
	static <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,ELEMENT[] elements){
		return add(collection,append,List.of(elements));
	}
	
	static <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,ELEMENT[] elements){
		return add(collection,Boolean.TRUE,List.of(elements));
	}

	static <ELEMENT> Collection<ELEMENT> addElementAt(Collection<ELEMENT> collection, Object index, ELEMENT element) {
		if(collection!=null) {
			Integer indexValue = NumberHelper.getInteger(index);
			if(collection.isEmpty() && indexValue == 0)
				collection.add(element);
			else if( indexValue < getSize(collection) ){
				if(collection instanceof List)
					((List<ELEMENT>)collection).add(indexValue, element);
				else
					throw new RuntimeException("cannot insert in collection of type : "+collection.getClass());	
			}else
				throw new RuntimeException("cannot insert at index "+index+" in collection of size "+collection.size());
		}
		return collection;
	}
	
	static Boolean contains(Collection<?> collection, Object element) {
		return collection == null ? Boolean.FALSE : collection.contains(element);
	}
	
	static Boolean containsAll(Collection<?> collection, Collection<?> elements) {
		if(Boolean.TRUE.equals(isNotEmpty(collection)) && Boolean.TRUE.equals(isNotEmpty(elements))) {
			for(Object index : elements)
				if(!Boolean.TRUE.equals(contains(collection, index)))
					return Boolean.FALSE;
			return Boolean.TRUE;
		}
		return null;
	}
	
	static Boolean contains(CollectionInstance<?> collectionInstance, Object element) {
		return collectionInstance!=null && contains(collectionInstance.get(), element);
	}
	
	static <ELEMENT> Collection<ELEMENT> removeNullValue(Collection<ELEMENT> collection) {
		return collection == null ? null : collection.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	static <ELEMENT> Collection<ELEMENT> removeDuplicate(Collection<ELEMENT> collection,Function<? super ELEMENT, ?> function) {
		return Boolean.TRUE.equals(isEmpty(collection)) ? collection : StreamEx.of(collection).distinct(function).toList();
	}
	
	static <ELEMENT> void swap(Collection<ELEMENT> collection, Object index01, Object index02) {
		if(Boolean.TRUE.equals(isNotEmpty(collection))) {
			ELEMENT object01 = getElementAt(collection, index01);
			ELEMENT object02 = getElementAt(collection, index02);
			setElementAt(collection, index01, object02);
			setElementAt(collection, index02, object01);
		}
	}
	
	static <ELEMENT> void swap(CollectionInstance<ELEMENT> collection, Object index01, Object index02) {
		if(Boolean.TRUE.equals(isNotEmpty(collection)))
			swap(collection.get(), index01, index02);
	}
	
	static <ELEMENT> List<List<ELEMENT>> getBatches(List<ELEMENT> collection, Object size) {
		Integer __size__  = NumberHelper.getInteger(size);
		if(__size__!= null && __size__ > 0)
			return ListUtils.partition(collection, __size__);
		return null;
	}

	static <ELEMENT> Collection<ELEMENT> getElementsNotIn(Collection<ELEMENT> collection1,Collection<ELEMENT> collection2) {
		if(isEmpty(collection1))
			return null;
		Collection<ELEMENT> collection = null;
		for(ELEMENT index : collection1) {
			if(contains(collection2, index))
				continue;			
			if(collection == null)
				collection = new ArrayList<>();
			collection.add(index);			
		}
		return collection;
	}
	
	static <ELEMENT> Collection<ELEMENT> getElementsNotIn(Collection<ELEMENT> collection1,Collection<?> collection2,Collection<String> fieldNames) {
		@SuppressWarnings("unchecked")
		Collection<ELEMENT> __collection2__ = (Collection<ELEMENT>) FieldHelper.readMany(collection2, FieldHelper.join(fieldNames));
		return getElementsNotIn(collection1, __collection2__);
	}
	
	static <ELEMENT> Collection<ELEMENT> getElementsNotIn(Collection<ELEMENT> collection1,Collection<?> collection2,String...fieldNames) {
		return getElementsNotIn(collection1, collection2, listOf(fieldNames));
	}
}