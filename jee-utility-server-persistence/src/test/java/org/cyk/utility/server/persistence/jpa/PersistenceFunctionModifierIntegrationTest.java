package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.test.arquillian.AbstractArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.server.persistence.PersistenceFunctionModifier;
import org.junit.Test;

public class PersistenceFunctionModifierIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void updateOne() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		myEntity.setCode("mc002");
		userTransaction.begin();
		__inject__(PersistenceFunctionModifier.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Update MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc002")
			;
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc002");
		
	}
	
}
