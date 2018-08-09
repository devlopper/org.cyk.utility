package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;

public abstract class AbstractEntity<PERSISTENCE_ENTITY> extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract PERSISTENCE_ENTITY getPersistenceEntity();
	
}
