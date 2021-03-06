package org.cyk.utility.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.clazz.ClassHelper;

public abstract class AbstractCollectionInstanceImpl<T> extends AbstractObject implements CollectionInstance<T>, Serializable {
	private static final long serialVersionUID = -1888054558236819369L;

	@Deprecated //use elementClass instead
	private Class<T> clazz;
	private Class<?> elementClass;
	protected Collection<T> collection;

	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		setElementClass(clazz = (Class<T>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class));
	}
	
	@Override
	public Collection<T> get() {
		return collection;
	}
	
	@Override
	public Collection<T> get(Integer begin, Integer end) {
		return __inject__(CollectionHelper.class).getElementsFromTo(collection, begin, end);
	}
	
	@Override
	public Collection<T> get(Integer begin) {
		return get(begin,null);
	}
	
	@Override
	public Collection<T> getIsInstanceOf(Collection<Class<?>> classes) {
		Collection<T> collection = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(this.collection)) {
			for(T index : this.collection) {
				if(index!=null && Boolean.TRUE.equals(__inject__(ClassHelper.class).isInstanceOfOne(index.getClass(), classes))) {
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
		return getIsInstanceOf(__inject__(CollectionHelper.class).instanciate(classes));
	}
	
	@Override
	public <I> Collection<I> getIsInstanceOfOne(Class<I> aClass) {
		Collection<I> instances = (Collection<I>) getIsInstanceOf(__inject__(CollectionHelper.class).instanciate(aClass));
		return instances;
	}
	
	@Override
	public <I> I getIsInstanceOfOneByIdentifier(Class<I> aClass, Object identifier) {
		Collection<I> instances = getIsInstanceOfOne(aClass);
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(instances))) {
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
		if(collection == null && Boolean.TRUE.equals(instanciateIfNull))
			collection = new ArrayList<>();
		return collection;
	}

	@Override
	public CollectionInstance<T> add(Collection<T> collection) {
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)) {
			__add__(collection);
		}
		return this;
	}
	
	protected void  __add__(Collection<T> collection) {
		__getCollection__(Boolean.TRUE).addAll(collection);
	}

	@Override
	public CollectionInstance<T> add(T... elements) {
		add(__inject__(CollectionHelper.class).instanciate(elements));
		return this;
	}
	
	@Override
	public CollectionInstance<T> add(CollectionInstance<T> elements) {
		if(__inject__(CollectionHelper.class).isNotEmpty(elements))
			add(elements.get());
		return this;
	}
	
	@Override
	public CollectionInstance<T> addInstanceOf(Collection<?> collection) {
		for(Object index : collection)
			if(clazz.isAssignableFrom(index.getClass()))
				add((T)index);
		return this;
	}
	
	@Override
	public CollectionInstance<T> addInstanceOfArray(Object... elements) {
		return addInstanceOf(__inject__(CollectionHelper.class).instanciate(elements));
	}
	
	@Override
	public CollectionInstance<T> addAt(T element, Integer index) {
		__inject__(CollectionHelper.class).addElementAt(__getCollection__(Boolean.TRUE), index, element);
		return this;
	}
	
	@Override
	public T getAt(Object index) {
		return __inject__(CollectionHelper.class).getElementAt(collection, index);
	}
	
	@Override
	public T getFirst() {
		return __inject__(CollectionHelper.class).getFirst(collection);
	}
	
	@Override
	public T getLast() {
		return __inject__(CollectionHelper.class).getLast(collection);
	}
	
	@Override
	public Integer getSize() {
		return __inject__(CollectionHelper.class).getSize(collection);
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
	public T getByIdentifier(Object identifier) {
		if(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isNotEmpty(collection))) {
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
		return __inject__(CollectionHelper.class).getFirst(getIsInstanceOfOne(elementClass));
	}
	
	@Override
	public CollectionInstance<T> removeAll() {
		if(collection!=null)
			collection.clear();
		return this;
	}
	
	@Override
	public String toString() {
		return collection == null ? super.toString() : collection.toString();
	}
	
	public static final String FIELD_COLLECTION = "collection";
}
