package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.persistence.api.PersonPersistence;
import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class PersonPersistenceImpl extends AbstractPersistenceEntityImpl<Person> implements PersonPersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}
