package org.cyk.utility.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTest;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

public class PersistenceIntegrationTestPerformance extends AbstractPersistenceArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(PersistenceQueryRepository.class).add(new PersistenceQuery().setIdentifier("MyEntity.deleteByIdentifier").setValue("DELETE FROM MyEntity r WHERE r.identifier IN :identifiers"));
	}
	
	@Test
	public void create_MyEntity_one_function() throws Exception{
		userTransaction.begin();
		PersistenceFunctionCreator function = __inject__(PersistenceFunctionCreator.class);
		function.addLogMessageBuilderParameter("function create");
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.setEntity(new MyEntity().setCode(__getRandomUuidAsString__())).execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function remove");
		remover.setEntities(__inject__(MyEntityPersistence.class).readMany());
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.execute();
		userTransaction.commit();
	}
	
	@Test
	public void remove_MyEntity_one_query() throws Exception{
		userTransaction.begin();
		PersistenceFunctionCreator function = __inject__(PersistenceFunctionCreator.class);
		function.addLogMessageBuilderParameter("function");
		function.setEntity(new MyEntity().setCode(__getRandomUuidAsString__())).execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function remove by query 1");
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.setQueryIdentifier("MyEntity.deleteByIdentifier");
		remover.setQueryParameters(new Properties().set("identifiers",__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier).collect(Collectors.toList())));
		remover.execute();
		userTransaction.commit();
	}
	
	@Test
	public void create_MyEntity_many_10_function() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		PersistenceFunctionCreator function =  __inject__(PersistenceFunctionCreator.class);
		function.addLogMessageBuilderParameter("function create");
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.setEntities(collection);
		function.setProperty(Properties.ENTITY_MANAGER, __inject__(EntityManager.class));
		function.execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function");
		remover.setEntities(__inject__(MyEntityPersistence.class).readMany());
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.execute();
		userTransaction.commit();
	}
	
	@Test
	public void remove_MyEntity_many_10_query() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		PersistenceFunctionCreator function =  __inject__(PersistenceFunctionCreator.class);
		function.setEntities(collection);
		function.setProperty(Properties.ENTITY_MANAGER, __inject__(EntityManager.class));
		function.execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function remove by query 10");
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.setQueryIdentifier("MyEntity.deleteByIdentifier");
		remover.setQueryParameters(new Properties().set("identifiers",__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier).collect(Collectors.toList())));
		remover.execute();
		userTransaction.commit();
	}
	
	//@Test
	public void create_MyEntity_many_1000_function() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 1000 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		PersistenceFunctionCreator function =  __inject__(PersistenceFunctionCreator.class);
		function.addLogMessageBuilderParameter("function create");
		function.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		function.setEntities(collection);
		function.setProperty(Properties.ENTITY_MANAGER, __inject__(EntityManager.class));
		function.execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function remove");
		remover.setEntities(__inject__(MyEntityPersistence.class).readMany());
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.execute();
		userTransaction.commit();
	}
	
	//@Test
	public void remove_MyEntity_many_1000_function() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 1000 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		PersistenceFunctionCreator function =  __inject__(PersistenceFunctionCreator.class);
		function.setEntities(collection);
		function.setProperty(Properties.ENTITY_MANAGER, __inject__(EntityManager.class));
		function.execute();
		userTransaction.commit();
		
		userTransaction.begin();
		PersistenceFunctionRemover remover = __inject__(PersistenceFunctionRemover.class);
		remover.addLogMessageBuilderParameter("function remove by query 1000");
		remover.getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		remover.setQueryIdentifier("MyEntity.deleteByIdentifier");
		remover.setQueryParameters(new Properties().set("identifiers",__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier).collect(Collectors.toList())));
		remover.execute();
		userTransaction.commit();
	}
	
	@Test
	public void create_MyEntity_one_provider() throws Exception{
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(__getRandomUuidAsString__()),new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
	}
	
	@Test
	public void create_MyEntity_many_10_provider() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		__inject__(MyEntityPersistence.class).createMany(collection,new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
	}
	
	//@Test
	public void create_MyEntity_many_1000_provider() throws Exception{
		userTransaction.begin();
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 1000 ; index = index + 1)
			collection.add(new MyEntity().setCode(__getRandomUuidAsString__()));
		__inject__(MyEntityPersistence.class).createMany(collection,new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
	}
	
	@Test
	public void delete_MyEntity_one_businessIdentifier_provider() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(code1));
		userTransaction.commit();
		
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(code2));
		userTransaction.commit();
		
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByBusinessIdentifier(code2);
		userTransaction.commit();
		
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(__inject__(MyEntityPersistence.class).readMany().stream().map(MyEntity::getIdentifier)
				.collect(Collectors.toList()),new Properties().setLogLevel(LogLevel.INFO));
		userTransaction.commit();
	}
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new WebArchiveBuilder().setMavenPomProfileIdentifier("org.cyk.test.performance").execute(); 
	}
	
}
