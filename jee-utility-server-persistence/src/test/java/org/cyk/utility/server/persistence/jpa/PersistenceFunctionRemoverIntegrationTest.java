package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.PersistenceFunctionRemover;
import org.junit.Test;

public class PersistenceFunctionRemoverIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void deleteOne() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		Long identifier = myEntity.getIdentifier();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		userTransaction.begin();
		__inject__(PersistenceFunctionRemover.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Delete MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc001")
			;
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier(identifier).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier="+identifier);
		
	}
	
}
