package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractObjectImpl;

public abstract class AbstractIdentifiedImpl<IDENTIFIER, IDENTIFIED> extends AbstractObjectImpl implements Identified<IDENTIFIER, IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<IDENTIFIED> parents;
	private Integer numberOfParents;
	private Collection<IDENTIFIED> children;
	private Integer numberOfChildren;
	
	@Override
	public Collection<IDENTIFIED> getParents() {
		return parents;
	}
	
	@Override
	public Identified<IDENTIFIER, IDENTIFIED> setParents(Collection<IDENTIFIED> parents) {
		this.parents = parents;
		return this;
	}
	
	@Override
	public Integer getNumberOfParents() {
		return numberOfParents;
	}
	
	@Override
	public Identified<IDENTIFIER, IDENTIFIED> setNumberOfParents(Integer numberOfParents) {
		this.numberOfParents = numberOfParents;
		return this;
	}
	
	@Override
	public Collection<IDENTIFIED> getChildren() {
		return children;
	}
	
	@Override
	public Identified<IDENTIFIER, IDENTIFIED> setChildren(Collection<IDENTIFIED> children) {
		this.children = children;
		return this;
	}
	
	@Override
	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}
	
	@Override
	public Identified<IDENTIFIER, IDENTIFIED> setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
		return this;
	}
}
