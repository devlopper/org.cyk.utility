package org.cyk.utility.server.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.junit.Test;

public class BusinessIntegrationTestPerformance extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void create_MyEntity_one_function() throws Exception{
		BusinessFunctionCreator function = __inject__(BusinessFunctionCreator.class);
		function.addLogMessageBuilderParameter("function");
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.setEntity(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l));
		function.execute();
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void create_MyEntity_many_10_function() {
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l));
		BusinessFunctionCreator function =  __inject__(BusinessFunctionCreator.class);
		function.addLogMessageBuilderParameter("function");
		function.setEntities(collection);
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.execute();
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
					.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void create_MyEntity_many_100_function() throws Exception{
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l));
		BusinessFunctionCreator function =  __inject__(BusinessFunctionCreator.class);
		function.addLogMessageBuilderParameter("function");
		function.setEntities(collection);
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.execute();
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void create_MyEntity_one_provider() throws Exception{
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l),new Properties().setLogLevel(LogLevel.INFO));
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void create_MyEntity_many_10_provider() throws Exception{
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l));
		__inject__(MyEntityBusiness.class).createMany(collection,new Properties().setLogLevel(LogLevel.INFO));
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void create_MyEntity_many_100_provider() throws Exception{
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()).setTimestamp(1l));
		__inject__(MyEntityBusiness.class).createMany(collection,new Properties().setLogLevel(LogLevel.INFO));
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
	
	@Test
	public void delete_MyEntity_one() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code1));
		
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code2));
		
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		
		__inject__(MyEntityBusiness.class).deleteByBusinessIdentifier(code2);
		
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(__inject__(MyEntityPersistence.class).read().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
	}
}
