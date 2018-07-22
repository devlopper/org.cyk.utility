package org.cyk.utility.server.persistence.jpa;

import java.util.List;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.server.persistence.test.arquillian.AbstractArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceQueryRepositoryJpaIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(PersistenceQueryRepository.class).add(new PersistenceQuery().setIdentifier("MyEntity.readAll").setValue("SELECT r FROM MyEntity r")
				.setResultClass(MyEntity.class));
	}
	
	@Test
	public void readAll() throws Exception{
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc001")).execute();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc002")).execute();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc003")).execute();
		userTransaction.commit();
		
		@SuppressWarnings("unchecked")
		List<MyEntity> results = (List<MyEntity>) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setNamedQueryIdentifier("MyEntity.readAll")
				.execute().getProperties().getEntities();
		
		Assert.assertEquals(3, results.size());
		//System.out.println(results);
		/*
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code=mc001");
		*/
	}
	
	
	
}
