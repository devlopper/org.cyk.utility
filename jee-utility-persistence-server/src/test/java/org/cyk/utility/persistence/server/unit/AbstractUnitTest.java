package org.cyk.utility.persistence.server.unit;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.object.__static__.persistence.EntityLifeCycleListener;
import org.cyk.utility.persistence.EntityManagerFactoryGetter;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.query.EntityCreator;
import org.cyk.utility.persistence.query.EntityDeletor;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.EntityUpdater;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.persistence.server.MetricsManager;
import org.cyk.utility.persistence.server.TransientFieldsProcessor;
import org.cyk.utility.persistence.server.integration.ApplicationScopeLifeCycleListener;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.junit.jupiter.api.BeforeAll;

public class AbstractUnitTest extends org.cyk.utility.test.weld.AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@BeforeAll
	public static void listenBeforeAll() {
		ApplicationScopeLifeCycleListener.INTEGRATION = Boolean.FALSE;
	}
	
	@Override
	protected void __listenBefore__() {		
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(getUnitName());
		Initializer.initialize();
		DependencyInjection.setQualifierClassTo(Test.class,RuntimeQueryStringBuilder.class,TransientFieldsProcessor.class);
	}
	
	protected String getUnitName() {
		return "default";
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		QueryManager.INSTANCE.set(null);
		EntityManagerFactoryGetter.INSTANCE.set(null);
		EntityManagerGetter.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
		EntityReader.INSTANCE.set(null);
		EntityUpdater.INSTANCE.set(null);
		EntityDeletor.INSTANCE.set(null);
		MetricsManager.INSTANCE.set(null);
		EntityLifeCycleListener.INSTANCE.set(null);
	}
	
}