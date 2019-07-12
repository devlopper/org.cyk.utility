package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class JavaPersistenceApiHelperImpl extends AbstractHelper implements JavaPersistenceApiHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public EntityManager getEntityManager(Properties properties, Boolean injectIfNull) {
		EntityManager entityManager = (EntityManager) (properties == null ? null : properties.getEntityManager());
		if(entityManager == null && Boolean.TRUE.equals(injectIfNull))
			entityManager = __inject__(EntityManager.class);
		return entityManager;
	}

	@Override
	public EntityManager getEntityManager(Properties properties) {
		return getEntityManager(properties, Boolean.TRUE);
	}

}
