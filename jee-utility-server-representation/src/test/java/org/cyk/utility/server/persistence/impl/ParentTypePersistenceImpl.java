package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.api.ParentTypePersistence;
import org.cyk.utility.server.persistence.entities.ParentType;

@ApplicationScoped
public class ParentTypePersistenceImpl extends AbstractPersistenceEntityImpl<ParentType> implements ParentTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}
