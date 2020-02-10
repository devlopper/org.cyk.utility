package org.cyk.utility.__kernel__.collection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.bind.JsonbBuilder;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import one.util.streamex.StreamEx;

public interface CollectionHelper {

	static <T> Collection<T> instantiate(Class<?> klass,Boolean ignoreNullElement,@SuppressWarnings("unchecked") T...elements) {
		if(klass == null || elements == null || elements.length == 0)
			return null;
		if(klass.equals(List.class)) {
			Collection<T> collection = new ArrayList<>();
			for(T element : elements) {
				if(element == null && Boolean.TRUE.equals(ignoreNullElement))
					continue;
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(element);
			}
			return collection;
		}
		if(klass.equals(Set.class))
			return Set.of(elements);
		throw new RuntimeException("instantiate collection of type "+klass+" not yet handled");
	}
	
	static <T> Collection<T> instantiate(Class<?> klass,@SuppressWarnings("unchecked") T...elements) {
		return instantiate(klass,Boolean.FALSE, elements);
	}
	
	@SuppressWarnings("unchecked")
	static <T> Collection<T> instantiateFromJson(Class<T> elementClass,String json,Class<?> collectionClass) {
		if(elementClass == null || StringHelper.isBlank(json))
			return null;
		Type collectionType = null;
		if(collectionClass == null) {
			collectionType = new ArrayList<T>() {private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
		}
		return (Collection<T>) JsonbBuilder.create().fromJson(json, collectionType);
	}
	
	static <T> Collection<T> instantiateFromJson(Class<T> elementClass,String json) {
		if(elementClass == null || StringHelper.isBlank(json))
			return null;
		return instantiateFromJson(elementClass, json, null);
	}
	
	@SuppressWarnings("unchecked")
	static <T> Collection<T> instantiateFromJson(Type elementType,String json,Class<?> collectionClass) {
		if(elementType == null || StringHelper.isBlank(json))
			return null;
		Type collectionType = null;
		if(collectionClass == null) {
			collectionType = new ArrayList<T>() {private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
		}
		return (Collection<T>) JsonbBuilder.create().fromJson(json, collectionType);
	}
	
	static <T> Collection<T> instantiateFromJson(Type elementType,String json) {
		if(elementType == null || StringHelper.isBlank(json))
			return null;
		return instantiateFromJson(elementType,json,null);
	}
	
	static Collection<Map<String,?>> instantiateFromJson(String json) {
		if(StringHelper.isBlank(json))
			return null;
		return instantiateFromJson(new HashMap<String,Object>() {private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass(), json, null);
	}
	
	static <T> Collection<T> instantiateFromJson(Class<T> elementClass,URI uri) {
		if(uri == null)
			return null;
		try {
			return instantiateFromJson(elementClass,IOUtils.toString(uri, "UTF-8"));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <T> Collection<T> instantiateFromJson(Type elementType,URI uri) {
		if(uri == null)
			return null;
		try {
			return instantiateFromJson(elementType,IOUtils.toString(uri, "UTF-8"));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <T> Collection<T> instantiateFromJsonLocatedAt(Class<T> elementClass,String uniformResourceIdentifier) {
		if(StringHelper.isBlank(uniformResourceIdentifier))
			return null;
		try {
			URI uri = new URI(uniformResourceIdentifier);
			return instantiateFromJson(elementClass,uri);
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <T> Collection<T> instantiateFromJsonLocatedAt(Type elementType,String uniformResourceIdentifier) {
		if(StringHelper.isBlank(uniformResourceIdentifier))
			return null;
		try {
			URI uri = new URI(uniformResourceIdentifier);
			return instantiateFromJson(elementType,uri);
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <T> Collection<T> instantiateFromMaps(Class<T> elementClass,Collection<Map<String,?>> maps,Map<String,String> fieldsNames,Class<?> collectionClass) {
		if(elementClass == null || isEmpty(maps))
			return null;
		Collection<T> elements = new ArrayList<>();
		for(Map<String,?> index : maps) {
			T element = ClassHelper.instanciate(elementClass);
			InstanceHelper.copy(index, element, fieldsNames);
			elements.add(element);
		}
		return elements;
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,URI uri,Map<String,String> fieldsNames) {
		if(klass == null)
			return null;
		if(uri == null) {
			String uniformResourceIdentifier = ConfigurationHelper.getClassUniformResourceIdentifier(klass,classifier);
			if(StringHelper.isBlank(uniformResourceIdentifier))
				return null;
			try {
				uri = new URI(uniformResourceIdentifier);
			} catch (URISyntaxException exception) {
				throw new RuntimeException(exception);
			}
		}
		Collection<Map<String,?>> maps = instantiateFromJson(new ArrayList<Map<String,?>>().getClass().getGenericSuperclass(), uri);
		Collection<INSTANCE> instances = instantiateFromMaps(klass, maps, fieldsNames, null);
		return instances;
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,URI uri,Map<String,String> fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,null,uri,fieldsNames);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,URI uri,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(klass,classifier, fieldsNames);
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier,uri,map);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,URI uri,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,null,uri,fieldsNames);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,URI uri,String...fieldsNames) {
		if(klass == null)
			return null;
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(klass,classifier, fieldsNames);
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier,uri,map);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,URI uri,String...fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,null,uri,fieldsNames);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,String uniformResourceIdentifier,Map<String,String> fieldsNames) {
		if(klass == null)
			return null;
		try {
			URI uri = StringHelper.isBlank(uniformResourceIdentifier) ? null : new URI(uniformResourceIdentifier);
			return getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier, uri, fieldsNames);
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,String uniformResourceIdentifier,Map<String,String> fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass, null,uniformResourceIdentifier, fieldsNames);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,String uniformResourceIdentifier,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(klass, fieldsNames);
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier,uniformResourceIdentifier,map);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,String uniformResourceIdentifier,Collection<String> fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,null,uniformResourceIdentifier,fieldsNames);
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,Object classifier,String uniformResourceIdentifier,String...fieldsNames) {
		if(klass == null)
			return null;
		Map<String,String> map = FieldHelper.getFieldsNamesMapping(klass, fieldsNames);
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,classifier,uniformResourceIdentifier,map);	
	}
	
	static <INSTANCE> Collection<INSTANCE> getFromJsonLocatedAtUniformResourceIdentifier(Class<INSTANCE> klass,String uniformResourceIdentifier,String...fieldsNames) {
		if(klass == null)
			return null;
		return getFromJsonLocatedAtUniformResourceIdentifier(klass,null,uniformResourceIdentifier,fieldsNames);	
	}
	
	@SuppressWarnings("unchecked")
	static <INSTANCE> Collection<INSTANCE> build(Class<INSTANCE> klass,CollectionAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		if(parameter.getValue() != null)
			return (Collection<INSTANCE>) parameter.getValue();
		Collection<INSTANCE> instances = null;
		if(parameter.getElementClass()!= null && StringHelper.isNotBlank(parameter.getUniformResourceIdentifier())) {
			Collection<?> collection = getFromJsonLocatedAtUniformResourceIdentifier(parameter.getElementClass(), parameter.getUniformResourceIdentifier(), parameter.getFieldsNames());
			if(isNotEmpty(collection)) {
				if(instances == null)
					instances = new ArrayList<>();
				instances.addAll((Collection<? extends INSTANCE>) collection);
			}
		}
		return instances;
	}
	
	@SuppressWarnings("unchecked")
	static <INSTANCE> Collection<INSTANCE> build(Class<INSTANCE> klass,CollectionsAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		if(parameter.getValue() != null)
			return (Collection<INSTANCE>) parameter.getValue();
		Collection<INSTANCE> instances = null;
		if(isNotEmpty(parameter.getCollectionAsFunctionParameters())) {
			for(CollectionAsFunctionParameter index : parameter.getCollectionAsFunctionParameters()) {
				Collection<?> collection = build(klass, index);
				if(isNotEmpty(collection)) {
					if(instances == null)
						instances = new ArrayList<>();
					instances.addAll((Collection<? extends INSTANCE>) collection);
				}
			}
		}
		return instances;
	}
	
	@SuppressWarnings("unchecked")
	static <T> List<T> listOf(Boolean ignoreNullElement,T...elements) {
		if(elements == null || elements.length == 0)
			return null;
		return (List<T>) instantiate(List.class,ignoreNullElement,elements);
	}
	
	@SafeVarargs
	static <T> List<T> listOf(T...elements) {
		return listOf(Boolean.FALSE,elements);
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
	
	@SafeVarargs
	static <ELEMENT> Collection<ELEMENT> concatenate(Collection<ELEMENT>...collections) {
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
