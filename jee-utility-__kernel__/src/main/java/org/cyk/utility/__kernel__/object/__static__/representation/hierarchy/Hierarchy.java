package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface Hierarchy<IDENTIFIER,IDENTIFIED> extends org.cyk.utility.__kernel__.object.__static__.representation.Identified<IDENTIFIER> {

	@SuppressWarnings("unchecked")
	default IDENTIFIED getParent() {
		return (IDENTIFIED) FieldHelper.read(this, PROPERTY_PARENT);
	}
	
	default Hierarchy<IDENTIFIER,IDENTIFIED> setParent(IDENTIFIED parent) {
		FieldHelper.write(this, PROPERTY_PARENT, parent);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	default IDENTIFIED getChild() {
		return (IDENTIFIED) FieldHelper.read(this, PROPERTY_CHILD);
	}
	
	default Hierarchy<IDENTIFIER,IDENTIFIED> setChild(IDENTIFIED child) {
		FieldHelper.write(this, PROPERTY_CHILD, child);
		return this;
	}
	
	String PROPERTY_PARENT = "parent";
	String PROPERTY_CHILD = "child";
}
