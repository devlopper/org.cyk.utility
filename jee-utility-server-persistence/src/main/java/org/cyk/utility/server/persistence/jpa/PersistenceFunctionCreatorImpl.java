package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionCreatorImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
/**
 * Default implementation will use Java Persistence Api (JPA)
 * @author CYK
 *
 */
public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionCreatorImpl implements PersistenceFunctionCreator {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __create__(Object entity) {
		__inject__(EntityManager.class).persist(entity);
	}
	
}
