package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractSystemServiceProviderImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
}
