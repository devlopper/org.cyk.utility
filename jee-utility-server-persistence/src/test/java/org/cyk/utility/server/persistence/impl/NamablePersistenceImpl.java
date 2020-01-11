package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityIdentifiedByStringImpl;
import org.cyk.utility.server.persistence.api.NamablePersistence;
import org.cyk.utility.server.persistence.entities.Namable;

@ApplicationScoped
public class NamablePersistenceImpl extends AbstractPersistenceEntityIdentifiedByStringImpl<Namable> implements NamablePersistence,Serializable {
	private static final long serialVersionUID = 1L;

}
