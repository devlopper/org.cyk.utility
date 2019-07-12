package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;

public interface JavaPersistenceApiHelper extends Helper {

	EntityManager getEntityManager(Properties properties,Boolean injectIfNull);
	EntityManager getEntityManager(Properties properties);
}
