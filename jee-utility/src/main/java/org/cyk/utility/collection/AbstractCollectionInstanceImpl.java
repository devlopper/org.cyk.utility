package org.cyk.utility.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractCollectionInstanceImpl<T> extends AbstractObject implements CollectionInstance<T>, Serializable {
	private static final long serialVersionUID = -1888054558236819369L;

	private Collection<T> collection;

	@Override
	public Collection<T> get() {
		return collection;
	}

	@Override
	public CollectionInstance<T> set(Collection<T> collection) {
		this.collection = collection;
		return this;
	}

	@Override
	public CollectionInstance<T> add(Collection<T> collection) {
		if(__inject__(CollectionHelper.class).isNotEmpty(collection)) {
			if(this.collection == null)
				this.collection = new ArrayList<>();
			this.collection.addAll(collection);
		}
		return this;
	}

	@Override
	public CollectionInstance<T> add(T... elements) {
		add(__inject__(CollectionHelper.class).instanciate(elements));
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
}
