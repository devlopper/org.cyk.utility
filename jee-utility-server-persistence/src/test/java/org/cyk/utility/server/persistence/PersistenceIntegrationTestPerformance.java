package org.cyk.utility.server.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.test.TestRunner;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class PersistenceIntegrationTestPerformance extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(PersistenceQueryRepository.class).add(new PersistenceQuery().setIdentifier("MyEntity.deleteByIdentifier").setValue("DELETE FROM MyEntity r WHERE r.identifier IN :identifiers"));
	}
	
	@Test
	public void create() throws Exception{
		Integer count = 100000;
		
		TestRunner.getInstance().run("create 01",new Runnable() {			
			@Override
			public void run() {
				Collection<MyEntity> collection = new ArrayList<>();
				for(Integer index = 0 ; index < count ; index ++)
					collection.add(new MyEntity().setCode("v01"+index.toString()));
				try {
					userTransaction.begin();
					__inject__(MyEntityPersistence.class).createMany(collection);
					userTransaction.commit();	
				} catch (Exception exception) {
					exception.printStackTrace();
				}					
			}
		});
		
		TestRunner.getInstance().run("create 02",new Runnable() {			
			@Override
			public void run() {
				Collection<MyEntity> collection = new ArrayList<>();
				for(Integer index = 0 ; index < count ; index ++)
					collection.add(new MyEntity().setIdentifier("v02"+index.toString()).setCode("v02"+index.toString()));
				try {
					userTransaction.begin();
					EntityManager entityManager = __inject__(EntityManager.class);
					for(MyEntity index : collection)
						entityManager.persist(index);
					userTransaction.commit();	
				} catch (Exception exception) {
					exception.printStackTrace();
				}					
			}
		});
	}
	
	@Test
	public void __listenExecuteReadAfter__one() throws Exception{
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(__getRandomCode__()));
		userTransaction.commit();
		
		__inject__(MyEntityPersistence.class).read();
	}
	
	@Test
	public void __listenExecuteReadAfter__many() throws Exception{
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(List.of(new MyEntity().setCode(__getRandomCode__()),new MyEntity().setCode(__getRandomCode__())
				,new MyEntity().setCode(__getRandomCode__()),new MyEntity().setCode(__getRandomCode__()),new MyEntity().setCode(__getRandomCode__())));
		userTransaction.commit();
		
		__inject__(MyEntityPersistence.class).read();
	}
}
