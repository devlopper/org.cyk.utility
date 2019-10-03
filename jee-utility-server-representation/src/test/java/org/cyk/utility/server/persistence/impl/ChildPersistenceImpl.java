package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.api.ChildPersistence;
import org.cyk.utility.server.persistence.entities.Child;

@ApplicationScoped
public class ChildPersistenceImpl extends AbstractPersistenceEntityImpl<Child> implements ChildPersistence,Serializable {
	private static final long serialVersionUID = 1L;

}
