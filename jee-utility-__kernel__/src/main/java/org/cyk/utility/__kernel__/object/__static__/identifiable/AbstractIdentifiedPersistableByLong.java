package org.cyk.utility.__kernel__.object.__static__.identifiable;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public abstract class AbstractIdentifiedPersistableByLong extends AbstractIdentifiedPersistable<Long> implements IdentifiedPersistableByLong,Serializable {
	private static final long serialVersionUID = 1L;
				
}