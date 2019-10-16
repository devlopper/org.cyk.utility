package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractIdentifiedImpl<IDENTIFIER> extends AbstractObjectImpl implements Identified<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;

	
	
}
