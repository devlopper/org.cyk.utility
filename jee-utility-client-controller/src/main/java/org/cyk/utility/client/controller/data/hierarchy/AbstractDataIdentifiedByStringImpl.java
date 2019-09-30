package org.cyk.utility.client.controller.data.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;

public abstract class AbstractDataIdentifiedByStringImpl<NODE> extends org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringImpl implements DataIdentifiedByString<NODE>,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Collection<NODE> parents;
	private Long numberOfParents;
	private Long numberOfChildren;
	
	@Override
	public Long getNumberOfParents() {
		return numberOfParents;
	}
	
	@Override
	public DataIdentifiedByString<NODE> setNumberOfParents(Long numberOfParents) {
		this.numberOfParents = numberOfParents;
		return this;
	}
	
	@Override
	public Long getNumberOfChildren() {
		return numberOfChildren;
	}
	
	@Override
	public DataIdentifiedByString<NODE> setNumberOfChildren(Long numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
		return this;
	}
	
	@Override
	public Collection<NODE> getParents() {
		return parents;
	}
	
	@Override
	public Collection<NODE> getParents(Boolean instanciateIfNull) {
		Collection<NODE> parents = getParents();
		if(parents == null && Boolean.TRUE.equals(instanciateIfNull))
			setParents(parents = new ArrayList<>());
		return parents;
	}
	
	@Override
	public DataIdentifiedByString<NODE> setParents(Collection<NODE> parents) {
		this.parents = parents;
		return this;
	}
	
	@Override
	public DataIdentifiedByString<NODE> addParents(Collection<NODE> parents) {
		getParents(Boolean.TRUE).addAll(parents);
		return this;
	}
	
	@Override
	public DataIdentifiedByString<NODE> addParents(NODE...parents) {
		addParents(CollectionHelper.listOf(parents));
		return this;
	}
}
