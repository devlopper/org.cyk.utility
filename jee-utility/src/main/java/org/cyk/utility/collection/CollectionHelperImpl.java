package org.cyk.utility.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueHelper;

import one.util.streamex.StreamEx;

@ApplicationScoped
public class CollectionHelperImpl extends AbstractHelper implements CollectionHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Collection<?> collection){
		return collection==null || collection.isEmpty();
	}
	
	@Override
	public Boolean isNotEmpty(Collection<?> collection){
		return Boolean.FALSE.equals(isEmpty(collection));
	}
	
	@Override
	public Boolean isEmpty(CollectionInstance<?> collectionInstance){
		return collectionInstance==null || isEmpty(collectionInstance.get());
	}
	
	@Override
	public Boolean isNotEmpty(CollectionInstance<?> collectionInstance){
		return Boolean.FALSE.equals(isEmpty(collectionInstance));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> instanciate(Class<COLLECTION> collectionClass,ELEMENT...elements){
		if(collectionClass==null)
			return null;
		if(Set.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(LinkedHashSet.class,collectionClass);
		else if(List.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(ArrayList.class, collectionClass);
		else if(Collection.class.equals(collectionClass))
			collectionClass = __inject__(ValueHelper.class).cast(ArrayList.class, collectionClass);			
		Collection<ELEMENT> collection = (Collection<ELEMENT>) __inject__(ClassHelper.class).instanciateOne(collectionClass);
		if(__inject__(ArrayHelper.class).isNotEmpty(elements))
			collection.addAll(Arrays.asList(elements));
		return collection;
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> instanciate(ELEMENT...elements){
		return instanciate(ArrayList.class, elements);
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> concatenate(Collection<Collection<ELEMENT>> collections) {
		Collection<ELEMENT> collection = null;
		if(isNotEmpty(collections)){
			collection = new ArrayList<ELEMENT>();
			for(Collection<ELEMENT> index : collections)
				if(isNotEmpty(index))
					collection.addAll(index);
		}
		return collection;
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> concatenate(Collection<ELEMENT>... collections) {
		return concatenate(instanciate(collections));
	}
	
	@Override
	public Integer getSize(Collection<?> collection) {
		if(isEmpty(collection))
			return 0;
		return collection.size();
	}
	
	@Override
	public Integer getSize(CollectionInstance<?> collectionInstance) {
		return collectionInstance == null ? 0 : getSize(collectionInstance.get());
	}
	
	@Override
	public <ELEMENT> CollectionHelper setElementAt(Collection<ELEMENT> collection, Object index, ELEMENT value) {
		Integer indexValue = __inject__(NumberHelper.class).getInteger(index);
		if(isNotEmpty(collection) && indexValue < getSize(collection)){
			if(collection instanceof List)
				((List<ELEMENT>)collection).set(indexValue.intValue(),value);
			else {
				__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("collection helper set at");
			}
		}
		return this;
	}
	
	@Override
	public <ELEMENT> CollectionHelper setElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index,ELEMENT value) {
		if(Boolean.TRUE.equals(isNotEmpty(collectionInstance)))
			setElementAt(collectionInstance.get(),index,value);
		return this;
	}
	
	@Override
	public <ELEMENT> ELEMENT getElementAt(Collection<ELEMENT> collection,Object index){
		Integer indexValue = __inject__(NumberHelper.class).getInteger(index);
		ELEMENT element = null;
		if(isNotEmpty(collection) && indexValue < getSize(collection)){
			if(collection instanceof List)
				element = ((List<ELEMENT>)collection).get(indexValue.intValue());
			else {
				java.util.Iterator<ELEMENT> iterator = collection.iterator();
				Integer count = 0;
				while (count++ <= indexValue)
					element = iterator.next();
			}
		}
		return element;
	}

	@Override
	public <ELEMENT> ELEMENT getElementAt(CollectionInstance<ELEMENT> collectionInstance, Object index) {
		return collectionInstance == null ? null : getElementAt(collectionInstance.get(), index);
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> getElementsFromTo(Collection<ELEMENT> collection,Integer begin, Integer end) {
		if(collection!=null) {
			if(begin==null)
				begin = 0;
			if(end==null || end < 0)
				end = collection.size();
			if(collection instanceof List)
				return ((List<ELEMENT>)collection).subList(begin, end);
			__inject__(ThrowableHelper.class).throwRuntimeException("Cannot get elements from "+begin+" to "+end+" of "+collection.getClass());
		}
		return null;
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> getElementsFrom(Collection<ELEMENT> collection,Integer begin) {
		return getElementsFromTo(collection,begin,null);
	}
	
	@Override
	public <ELEMENT> ELEMENT getFirst(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		return collection.iterator().next();
	}
	
	@Override
	public <ELEMENT> ELEMENT getFirst(CollectionInstance<ELEMENT> collection) {
		return collection == null ? null : getFirst(collection.get());
	}
	
	@Override
	public <ELEMENT> ELEMENT getLast(Collection<ELEMENT> collection){
		if(isEmpty(collection))
			return null;
		if(collection instanceof LinkedHashSet)
			collection = new ArrayList<>(collection);
		if(collection instanceof List)
			return ((List<ELEMENT>)collection).get(((List<ELEMENT>)collection).size()-1);
		throw new RuntimeException("cannot find last on collection of type "+collection.getClass());
	}
	
	@Override
	public CollectionHelper clear(Collection<?> collection){
		if(collection!=null)
			collection.clear();
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <ELEMENT> Collection<ELEMENT> cast(Class<ELEMENT> aClass,Collection<?> collection){
		Collection<ELEMENT> result;
		if(collection==null){
			result = null;
		}else{
			result = new ArrayList<ELEMENT>();
			for(Object item : collection)
				result.add((ELEMENT) item);	
		}
		return result;
	}

	/**/
	
	@Override
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
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
	
	@Override
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,Collection<ELEMENT> elements){
		return add(List.class, collection, append, elements);
	}
	
	@Override
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,Boolean append,ELEMENT[] elements){
		return add(collectionClass,collection,append,instanciate(elements));
	}
	
	@Override
	public <COLLECTION extends Collection<?>,ELEMENT> Collection<ELEMENT> add(Class<COLLECTION> collectionClass,Collection<ELEMENT> collection,ELEMENT[] elements){
		return add(collectionClass,collection,Boolean.TRUE,instanciate(elements));
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,Boolean append,ELEMENT[] elements){
		return add(collection,append,instanciate(elements));
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> add(Collection<ELEMENT> collection,ELEMENT[] elements){
		return add(collection,Boolean.TRUE,instanciate(elements));
	}

	@Override
	public <ELEMENT> Collection<ELEMENT> addElementAt(Collection<ELEMENT> collection, Object index, ELEMENT element) {
		if(collection!=null) {
			Integer indexValue = __inject__(NumberHelper.class).getInteger(index);
			if(collection.isEmpty() && indexValue == 0)
				collection.add(element);
			else if( indexValue < getSize(collection) ){
				if(collection instanceof List)
					((List<ELEMENT>)collection).add(indexValue, element);
				else
					__inject__(ThrowableHelper.class).throwRuntimeException("cannot insert in collection of type : "+collection.getClass());	
			}else
				__inject__(ThrowableHelper.class).throwRuntimeException("cannot insert at index "+index+" in collection of size "+collection.size());
		}
		return collection;
	}
	
	@Override
	public Boolean contains(Collection<?> collection, Object element) {
		return collection == null ? Boolean.FALSE : collection.contains(element);
	}
	
	@Override
	public Boolean containsAll(Collection<?> collection, Collection<?> elements) {
		if(Boolean.TRUE.equals(isNotEmpty(collection)) && Boolean.TRUE.equals(isNotEmpty(elements))) {
			for(Object index : elements)
				if(!Boolean.TRUE.equals(contains(collection, index)))
					return Boolean.FALSE;
			return Boolean.TRUE;
		}
		return null;
	}
	
	@Override
	public Boolean contains(CollectionInstance<?> collectionInstance, Object element) {
		return collectionInstance!=null && contains(collectionInstance.get(), element);
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> removeNullValue(Collection<ELEMENT> collection) {
		return collection == null ? null : collection.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	@Override
	public <ELEMENT> Collection<ELEMENT> removeDuplicate(Collection<ELEMENT> collection,Function<? super ELEMENT, ?> function) {
		return Boolean.TRUE.equals(isEmpty(collection)) ? collection : StreamEx.of(collection).distinct(function).toList();
	}
	
	@Override
	public <ELEMENT> CollectionHelper swap(Collection<ELEMENT> collection, Object index01, Object index02) {
		if(Boolean.TRUE.equals(isNotEmpty(collection))) {
			ELEMENT object01 = getElementAt(collection, index01);
			ELEMENT object02 = getElementAt(collection, index02);
			setElementAt(collection, index01, object02);
			setElementAt(collection, index02, object01);
		}
		return this;
	}
	
	@Override
	public <ELEMENT> CollectionHelper swap(CollectionInstance<ELEMENT> collection, Object index01, Object index02) {
		if(Boolean.TRUE.equals(isNotEmpty(collection)))
			swap(collection.get(), index01, index02);
		return	this; 
	}
}
