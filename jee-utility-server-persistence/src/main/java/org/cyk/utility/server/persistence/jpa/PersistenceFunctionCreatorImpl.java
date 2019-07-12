package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

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
	
	@Override
	protected void __execute__(Collection<Object> entities,Integer batchSize) {
		EntityManager entityManager = __inject__(JavaPersistenceApiHelper.class).getEntityManager(getProperties());
		Integer count = 0;
		for(Object index : entities) {
			entityManager.persist(index);
			if(batchSize != null) {
				count++;
				if(count % batchSize == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
	}
	
}
