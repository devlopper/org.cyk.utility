package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiedImpl;

public abstract class AbstractHierarchyImpl<IDENTIFIER,IDENTIFIED> extends AbstractIdentifiedImpl<IDENTIFIER> implements Hierarchy<IDENTIFIER, IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;
	
}
