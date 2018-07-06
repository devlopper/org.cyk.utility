package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionRead;
import org.junit.Test;

public class PersistenceFunctionReaderIntegrationTest extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@Inject private PersistenceFunctionCreator persistenceFunctionCreator;
	@Inject private PersistenceFunctionReader persistenceFunctionReader;
	
	@Test
	public void readOneExisting() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistenceFunctionCreator.setAction(__inject__(SystemActionCreate.class)).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) persistenceFunctionReader.setAction(__inject__(SystemActionRead.class)).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(getLastLogEventMessage()).contains("identifier="+myEntity.getIdentifier());
		assertThat(getLastLogEventMessage()).contains("code=mc001");
	}
	
	@Test
	public void readOneNotExisting() throws Exception{
		MyEntity myEntity = (MyEntity) persistenceFunctionReader.setAction(__inject__(SystemActionRead.class)).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(getLastLogEventMessage()).contains("not found");
		assertThat(getLastLogEventMessage()).contains("identifier=-1");
	}
	
}
