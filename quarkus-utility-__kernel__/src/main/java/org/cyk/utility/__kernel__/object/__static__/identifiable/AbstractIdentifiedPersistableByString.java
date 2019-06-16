package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public abstract class AbstractIdentifiedPersistableByString extends AbstractIdentifiedPersistable<String> implements IdentifiedPersistableByString,Serializable {
	private static final long serialVersionUID = 1L;
	
	
}