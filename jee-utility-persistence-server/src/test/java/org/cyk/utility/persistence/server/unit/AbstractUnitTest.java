package org.cyk.utility.persistence.server.unit;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.persistence.server.query.RuntimeQueryBuilder;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;

public class AbstractUnitTest extends org.cyk.utility.test.weld.AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {		
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("default");
		Initializer.initialize();
		DependencyInjection.setQualifierClassTo(Test.class, RuntimeQueryBuilder.class,RuntimeQueryStringBuilder.class,DynamicManyExecutor.class);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		QueryManager.getInstance().clear();
	}
	
}