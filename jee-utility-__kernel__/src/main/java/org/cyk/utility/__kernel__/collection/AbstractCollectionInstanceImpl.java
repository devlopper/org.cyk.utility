package org.cyk.utility.__kernel__.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractCollectionInstanceImpl<T> extends AbstractObject implements CollectionInstance<T>, Serializable {
	private static final long serialVersionUID = -1888054558236819369L;

	@Deprecated //use elementClass instead
	private Class<T> clazz;
	private Class<?> elementClass;
	protected Collection<T> collection;
	private Class<?> collectionClass;
	private Boolean isDoNotAddNull;

	@SuppressWarnings("unchecked")
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		setElementClass(clazz = (Class<T>) ClassHelper.getParameterAt(getClass(), 0));
	}
	
	@Override
	public Collection<T> get() {
		return collection;
	}
	
	@Override
	public Collection<T> get(Integer begin, Integer end) {
		return CollectionHelper.getElementsFromTo(collection, begin, end);
	}
	
	@Override
	public Collection<T> get(Integer begin) {
		return get(begin,null);
	}
	
	@Override
	public Collection<T> getWithoutLast(Integer count) {
		Integer end = getSize() - (count == null || count < 0 ? 0 : count);
		return get(0,end);
	}
	
	@Override
	public Collection<T> getIsInstanceOf(Collection<Class<?>> classes) {
		Collection<T> collection = null;
		if(CollectionHelper.isNotEmpty(this.collection)) {
			for(T index : this.collection) {
				if(index!=null && org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOfOne(index.getClass(), classes)) {
					if(collection == null)
						collection = new ArrayList<>();
					collection.add(index);
				}
			}
		}
		return collection;
	}
	
	@Override
	public Collection<T> getIsInstanceOf(Class<?>... classes) {
		return getIsInstanceOf(List.of(classes));
	}
	
	@Override
	public <I> Collection<I> getIsInstanceOfOne(Class<I> aClass) {
		@SuppressWarnings("unchecked")
		Collection<I> instances = (Collection<I>) getIsInstanceOf(aClass);
		return instances;
	}
	
	@Override
	public <I> I getIsInstanceOfOneByIdentifier(Class<I> aClass, Object identifier) {
		Collection<I> instances = getIsInstanceOfOne(aClass);
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(instances))) {
			for(I index : instances) {
				Object indexIdentifier = null;
				if(index instanceof AbstractObject)
					indexIdentifier = ((AbstractObject)index).getIdentifier();
				if(indexIdentifier!=null && indexIdentifier.equals(identifier))
					return index;	
			}
		}
		return null;
	}

	@Override
	public CollectionInstance<T> set(Collection<T> collection) {
		this.collection = collection;
		return this;
	}
	
	protected Collection<T> __getCollection__(Boolean instanciateIfNull){
		if(collection == null && Boolean.TRUE.equals(instanciateIfNull)) {
			Class<?> collectionClass = getCollectionClass();
			if(collectionClass == null)
				collectionClass = List.class;
			if(List.class.equals(collectionClass))
				collection = new ArrayList<>();
			else if(Set.class.equals(collectionClass))
				collection = new LinkedHashSet<>();
		}
		return collection;
	}

	@Override
	public CollectionInstance<T> add(Collection<T> collection) {
		if(CollectionHelper.isNotEmpty(collection)) {
			__add__(collection);
		}
		return this;
	}
	
	protected void  __add__(Collection<T> collection) {
		Collection<T> __collection__ = __getCollection__(Boolean.TRUE);
		Boolean isDoNotAddNull = getIsDoNotAddNull();
		if(isDoNotAddNull == null)
			isDoNotAddNull = Boolean.TRUE;
		for(T index : collection)
			if(index != null || Boolean.FALSE.equals(isDoNotAddNull))
				____add____(__collection__,index);
	}
	
	protected void ____add____(Collection<T> __collection__,T item) {
		__collection__.add(item);
	}

	@Override
	public CollectionInstance<T> add(@SuppressWarnings("unchecked") T... elements) {
		if(elements == null || elements.length == 0)
			return this;
		add(List.of(elements));
		return this;
	}
	
	@Override
	public CollectionInstance<T> add(CollectionInstance<T> elements) {
		if(CollectionHelper.isNotEmpty(elements))
			add(elements.get());
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CollectionInstance<T> addInstanceOf(Collection<?> collection) {
		for(Object index : collection)
			if(clazz.isAssignableFrom(index.getClass()))
				add((T)index);
		return this;
	}
	
	@Override
	public CollectionInstance<T> addInstanceOfArray(Object... elements) {
		return addInstanceOf(List.of(elements));
	}
	
	@Override
	public CollectionInstance<T> addAt(T element, Integer index) {
		CollectionHelper.addElementAt(__getCollection__(Boolean.TRUE), index, element);
		return this;
	}
	
	@Override
	public Boolean getIsDoNotAddNull() {
		return isDoNotAddNull;
	}
	
	@Override
	public CollectionInstance<T> setIsDoNotAddNull(Boolean isDoNotAddNull) {
		this.isDoNotAddNull = isDoNotAddNull;
		return this;
	}
	
	@Override
	public T getAt(Object index) {
		return CollectionHelper.getElementAt(collection, index);
	}
	
	@Override
	public T getFirst() {
		return CollectionHelper.getFirst(collection);
	}
	
	@Override
	public T getLast() {
		return CollectionHelper.getLast(collection);
	}
	
	@Override
	public Integer getSize() {
		return CollectionHelper.getSize(collection);
	}
	
	@Override
	public Class<?> getElementClass() {
		return elementClass;
	}
	
	@Override
	public CollectionInstance<T> setElementClass(Class<?> elementClass) {
		this.elementClass = elementClass;
		return this;
	}
	
	@Override
	public Class<?> getCollectionClass() {
		return collectionClass;
	}
	@Override
	public CollectionInstance<T> setCollectionClass(Class<?> collectionClass) {
		this.collectionClass = collectionClass;
		return this;
	}
	
	@Override
	public T getByIdentifier(Object identifier) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(collection))) {
			for(T index : collection) {
				Object indexIdentifier = null;
				if(index instanceof AbstractObject)
					indexIdentifier = ((AbstractObject)index).getIdentifier();
				if(indexIdentifier!=null && indexIdentifier.equals(identifier))
					return index;
			}
		}
		return null;
	}
	
	@Override
	public <I> I getFirstInstanceOf(Class<I> elementClass) {
		return CollectionHelper.getFirst(getIsInstanceOfOne(elementClass));
	}
	
	@Override
	public CollectionInstance<T> remove(T element) {
		if(collection != null)
			collection.remove(element);
		return this;
	}
	
	@Override
	public CollectionInstance<T> removeMany(Collection<T> elements) {
		if(collection != null && elements!=null)
			for(T index : elements)
				collection.remove(index);
		return this;
	}
	
	@Override
	public CollectionInstance<T> removeMany(@SuppressWarnings("unchecked") T... elements) {
		return removeMany(List.of(elements));
	}
	
	@Override
	public CollectionInstance<T> removeAll() {
		if(collection!=null)
			collection.clear();
		return this;
	}
	
	@Override
	public CollectionInstance<T> removeDuplicate(Function<? super T, ?> function) {
		collection = CollectionHelper.removeDuplicate(collection, function);
		return this;
	}
	
	@Override
	public Boolean contains(T element) {
		return CollectionHelper.contains(collection, element);
	}
	
	@Override
	public Boolean isEmpty() {
		return CollectionHelper.isEmpty(collection);
	}
	
	@Override
	public Boolean isNotEmpty() {
		return CollectionHelper.isNotEmpty(collection);
	}

	@Override
	public String toString() {
		return collection == null ? ConstantEmpty.STRING : collection.toString();
	}
}
