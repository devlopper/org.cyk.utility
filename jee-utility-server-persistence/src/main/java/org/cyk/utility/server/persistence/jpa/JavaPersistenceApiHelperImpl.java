package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.server.persistence.query.PersistenceQuery;

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

	@Override
	public JavaPersistenceApiHelper executeQuery(PersistenceQuery persistenceQuery,Properties properties) {
		EntityManager entityManager = getEntityManager(properties, Boolean.TRUE);
		//Instantiate query
		Query query = entityManager.createNamedQuery(persistenceQuery.getIdentifier().toString());
		//Set query parameters
		Properties parameters = (Properties) properties.getParameters();
		if(parameters != null && parameters.__getMap__()!=null)
			for(Map.Entry<Object, Object> entry : parameters.__getMap__().entrySet())
				query.setParameter(entry.getKey().toString(), entry.getValue());
		//Execute query statement
		Integer count = query.executeUpdate();
		getProperties().setCount(count);
		//TODO This is required when doing batch processing. is it flush not required ?
		entityManager.clear();
		return this;
	}
}
