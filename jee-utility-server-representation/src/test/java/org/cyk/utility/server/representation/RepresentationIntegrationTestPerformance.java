package org.cyk.utility.server.representation;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.server.business.api.MyEntityAssertionsProvider;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class RepresentationIntegrationTestPerformance extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, PersistableClassesGetter.class);
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
	}
	
	@Test
	public void read_isActionable() {
		System.out.println("IS ACTIONABLE");
		Integer numberOfEntities = 10;
		System.out.println("Generating entities : "+numberOfEntities);
		Collection<MyEntity> entities = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1) {
			MyEntity myEntity = new MyEntity().setCode(__getRandomIdentifier__());
			entities.add(myEntity);
		}
		System.out.println("Creating entities : "+numberOfEntities);
		__inject__(MyEntityBusiness.class).createByBatch(entities,100);
		
		Long t = System.currentTimeMillis();
		__inject__(MyEntityPersistence.class).read();
		t = System.currentTimeMillis() - t;
		System.out.println("Persistence "+numberOfEntities+" : "+t);
		
		t = System.currentTimeMillis();
		__inject__(MyEntityBusiness.class).find();
		t = System.currentTimeMillis() - t;
		System.out.println("Business "+numberOfEntities+" : "+t);
		
		t = System.currentTimeMillis();
		__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null);
		t = System.currentTimeMillis() - t;
		System.out.println("Representation 01 "+numberOfEntities+" : "+t);
		
		t = System.currentTimeMillis();
		__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null);
		t = System.currentTimeMillis() - t;
		System.out.println("Representation 02 "+numberOfEntities+" : "+t);
		
		t = System.currentTimeMillis();
		__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null);
		t = System.currentTimeMillis() - t;
		System.out.println("Representation 03 "+numberOfEntities+" : "+t);
	}
	
	@Test
	public void read_isNotActionable() {
		System.out.println("IS NOT ACTIONABLE");
		__inject__(ClassInstancesRuntime.class).get(MyEntity.class).setIsActionable(Boolean.FALSE);
		Integer numberOfEntities = 1000;
		System.out.println("Generating entities : "+numberOfEntities);
		Collection<MyEntity> entities = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1) {
			MyEntity myEntity = new MyEntity().setCode(__getRandomIdentifier__());
			entities.add(myEntity);
		}
		System.out.println("Creating entities : "+numberOfEntities);
		__inject__(MyEntityBusiness.class).createByBatch(entities,100);
		
		Long t = System.currentTimeMillis();
		__inject__(MyEntityPersistence.class).read();
		t = System.currentTimeMillis() - t;
		System.out.println("Persistence "+numberOfEntities+" : "+t);
		
		t = System.currentTimeMillis();
		__inject__(MyEntityBusiness.class).find();
		t = System.currentTimeMillis() - t;
		System.out.println("Business "+numberOfEntities+" : "+t);
		
		__read__(numberOfEntities, null);
		__read__(numberOfEntities, 5);
		__read__(numberOfEntities, 10);
		__read__(numberOfEntities, 20);
		__read__(numberOfEntities, 50);
	}
	
	private void __read__(Integer numberOfEntities,Integer executorCorePoolSize) {
		AbstractMapperSourceDestinationImpl.EXECUTOR_CORE_POOL_SIZE = executorCorePoolSize;
		Long t = System.currentTimeMillis();
		__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null);
		t = System.currentTimeMillis() - t;
		System.out.println("Representation Executor "+executorCorePoolSize+" - "+numberOfEntities+" : "+t);
	}
}
