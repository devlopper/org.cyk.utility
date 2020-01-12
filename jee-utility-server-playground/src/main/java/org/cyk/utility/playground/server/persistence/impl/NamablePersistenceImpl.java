package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.persistence.api.NamablePersistence;
import org.cyk.utility.playground.server.persistence.entities.Namable;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class NamablePersistenceImpl extends AbstractPersistenceEntityImpl<Namable> implements NamablePersistence,Serializable {
	private static final long serialVersionUID = 1L;
	
}
