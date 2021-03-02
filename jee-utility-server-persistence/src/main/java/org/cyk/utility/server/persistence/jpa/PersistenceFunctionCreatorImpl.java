package org.cyk.utility.server.persistence.jpa;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionCreatorImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
/**
 * Default implementation will use Java Persistence Api (JPA)
 * @author CYK
 *
 */
@Dependent
public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionCreatorImpl implements PersistenceFunctionCreator {
	private static final long serialVersionUID = 1L;
	
	private EntityManager entityManager;
	
	@Override
	protected void __initialiseWorkingVariables__() {
		super.__initialiseWorkingVariables__();
		entityManager = PersistenceHelper.getEntityManager(getProperties());
	}
	
	@Override
	protected void __executeWithEntity__(Object object) {
		entityManager.persist(object);
	}
	
	@Override
	protected void __flush__() {
		entityManager.flush();
	}
	
	@Override
	protected void __clear__() {
		entityManager.clear();
	}
}
