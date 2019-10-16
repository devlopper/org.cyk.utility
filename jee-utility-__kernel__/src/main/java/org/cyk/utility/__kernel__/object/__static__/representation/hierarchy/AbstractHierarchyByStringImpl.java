package org.cyk.utility.__kernel__.object.__static__.representation.hierarchy;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractHierarchyByStringImpl<IDENTIFIED> extends AbstractHierarchyImpl<String,IDENTIFIED> implements HierarchyByString< IDENTIFIED>,Serializable {
	private static final long serialVersionUID = 1L;
	
}
