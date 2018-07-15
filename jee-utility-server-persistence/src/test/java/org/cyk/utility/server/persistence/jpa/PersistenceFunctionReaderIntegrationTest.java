package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.junit.Test;

public class PersistenceFunctionReaderIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
		
	@Test
	public void readOneExisting() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code=mc001");
	}
	
	@Test
	public void readOneNotExisting() throws Exception{
		MyEntity myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier=-1");
	}
	
}