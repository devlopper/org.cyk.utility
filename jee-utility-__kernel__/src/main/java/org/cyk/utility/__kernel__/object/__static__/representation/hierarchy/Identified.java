package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface Identified<IDENTIFIER,IDENTIFIED> extends org.cyk.utility.__kernel__.object.__static__.representation.Identified<IDENTIFIER> {

	@SuppressWarnings("unchecked")
	default java.util.Collection<IDENTIFIED> getParents() {
		return (java.util.Collection<IDENTIFIED>) FieldHelper.read(this, PROPERTY_PARENTS);
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> setParents(java.util.Collection<IDENTIFIED> parents) {
		FieldHelper.write(this, PROPERTY_PARENTS, parents);
		return this;
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> addParents(java.util.Collection<IDENTIFIED> parents) {
		if(parents == null || parents.isEmpty())
			return this;	
		java.util.Collection<IDENTIFIED> __parents__ = getParents();
		if(__parents__ == null)
			setParents(__parents__ = new ArrayList<IDENTIFIED>());
		__parents__.addAll(parents);
		return this;
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> addParents(@SuppressWarnings("unchecked") IDENTIFIED...parents) {
		if(parents == null || parents.length == 0)
			return this;
		addParents(List.of(parents));
		return this;
	}
	
	default Integer getNumberOfParents() {
		return (Integer) FieldHelper.read(this, PROPERTY_NUMBER_OF_PARENTS);
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> setNumberOfParents(Integer numberOfParents) {
		FieldHelper.write(this, PROPERTY_NUMBER_OF_PARENTS, numberOfParents);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default java.util.Collection<IDENTIFIED> getChildren() {
		return (java.util.Collection<IDENTIFIED>) FieldHelper.read(this, PROPERTY_CHILDREN);
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> setChildren(java.util.Collection<IDENTIFIED> children) {
		FieldHelper.write(this, PROPERTY_CHILDREN, children);
		return this;
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> addChildren(java.util.Collection<IDENTIFIED> children) {
		if(children == null || children.isEmpty())
			return this;	
		java.util.Collection<IDENTIFIED> __children__ = getChildren();
		if(__children__ == null)
			setChildren(__children__ = new ArrayList<IDENTIFIED>());
		__children__.addAll(children);
		return this;
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> addChildren(@SuppressWarnings("unchecked") IDENTIFIED...children) {
		if(children == null || children.length == 0)
			return this;
		addChildren(List.of(children));
		return this;
	}
	
	default Integer getNumberOfChildren() {
		return (Integer) FieldHelper.read(this, PROPERTY_NUMBER_OF_CHILDREN);
	}
	
	default Identified<IDENTIFIER,IDENTIFIED> setNumberOfChildren(Integer numberOfChildren) {
		FieldHelper.write(this, PROPERTY_NUMBER_OF_CHILDREN, numberOfChildren);
		return this;
	}
	
	String PROPERTY_PARENTS = "parents";
	String PROPERTY_NUMBER_OF_PARENTS = "numberOfParents";
	String PROPERTY_CHILDREN = "children";
	String PROPERTY_NUMBER_OF_CHILDREN = "numberOfChildren";
}
