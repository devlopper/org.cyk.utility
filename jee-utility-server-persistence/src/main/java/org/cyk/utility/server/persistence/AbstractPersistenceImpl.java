package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractObject implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

}
