package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractObjectImpl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractIdentifiedImpl<IDENTIFIER, IDENTIFIED> extends AbstractObjectImpl implements Identified<IDENTIFIER, IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer numberOfParents;
	private Integer numberOfChildren;
	
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
	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}
	
	@Override
	public Identified<IDENTIFIER, IDENTIFIED> setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
		return this;
	}
}
