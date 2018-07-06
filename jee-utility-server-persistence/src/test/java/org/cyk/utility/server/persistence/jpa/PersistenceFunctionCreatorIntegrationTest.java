package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.system.action.SystemActionCreate;
import org.junit.Test;

public class PersistenceFunctionCreatorIntegrationTest extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@Inject private PersistenceFunctionCreator persistenceFunctionCreator;
	
	@Test
	public void createOne() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistenceFunctionCreator.setAction(__inject__(SystemActionCreate.class)).setEntity(myEntity).execute();
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Create MyEntity");
		assertThat(getLastLogEventMessage()).contains("identifier="+myEntity.getIdentifier());
		assertThat(getLastLogEventMessage()).contains("code=mc001");
	}
	
}
