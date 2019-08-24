package org.cyk.utility.client.controller.data.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;

public abstract class AbstractDataIdentifiedByStringImpl<NODE> extends org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringImpl implements DataIdentifiedByString<NODE>,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Collection<NODE> __parents__;
	
	@Override
	public Collection<NODE> get__parents__() {
		return __parents__;
	}
	
	@Override
	public Collection<NODE> get__parents__(Boolean instanciateIfNull) {
		Collection<NODE> parents = get__parents__();
		if(parents == null && Boolean.TRUE.equals(instanciateIfNull))
			set__parents__(parents = new ArrayList<>());
		return parents;
	}
	
	@Override
	public DataIdentifiedByString<NODE> set__parents__(Collection<NODE> __parents__) {
		this.__parents__ = __parents__;
		return this;
	}
	
	@Override
	public DataIdentifiedByString<NODE> add__parents__(Collection<NODE> __parents__) {
		get__parents__(Boolean.TRUE).addAll(__parents__);
		return this;
	}
	
	@Override
	public DataIdentifiedByString<NODE> add__parents__(NODE...__parents__) {
		add__parents__(__inject__(CollectionHelper.class).instanciate(__parents__));
		return this;
	}
}
