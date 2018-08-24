package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.system.action.SystemActionCreate;
import org.junit.Test;

public class BusinessFunctionCreatorIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void createOne(){
		MyEntity myEntity = new MyEntity().setCode("mc001").setTimestamp(1l);
		__inject__(BusinessFunctionCreator.class).setEntity(myEntity).execute();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionCreate.class),MyEntity.class))
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc001")
			;
	}
	
}
