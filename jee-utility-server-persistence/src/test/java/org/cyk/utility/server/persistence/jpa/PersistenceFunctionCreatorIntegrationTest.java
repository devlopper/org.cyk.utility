package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class PersistenceFunctionCreatorIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void createOne() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Create MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc001")
			;
	}
	
	
}
