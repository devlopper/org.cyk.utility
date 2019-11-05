package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.persistence.api.PersonTypePersistence;
import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class PersonTypePersistenceImpl extends AbstractPersistenceEntityImpl<PersonType> implements PersonTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}
