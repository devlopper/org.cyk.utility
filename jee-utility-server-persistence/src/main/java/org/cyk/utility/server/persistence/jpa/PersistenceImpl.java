package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractPersistenceImpl;

@ApplicationScoped
public class PersistenceImpl extends AbstractPersistenceImpl implements Serializable {
	private static final long serialVersionUID = 1L;

}
