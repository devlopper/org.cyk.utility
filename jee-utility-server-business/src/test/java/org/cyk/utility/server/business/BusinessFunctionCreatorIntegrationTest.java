package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BusinessFunctionCreatorIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void createOne(){
		MyEntity myEntity = new MyEntity().setCode("mc001");
		__inject__(BusinessFunctionCreator.class).setEntity(myEntity).execute();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Business Create MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc001")
			;
	}
	
}
