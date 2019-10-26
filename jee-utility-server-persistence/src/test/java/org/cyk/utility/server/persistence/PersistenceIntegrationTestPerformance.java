package org.cyk.utility.server.persistence;

import java.util.List;

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
