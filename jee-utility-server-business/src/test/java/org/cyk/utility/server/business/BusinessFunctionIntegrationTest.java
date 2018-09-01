package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class BusinessFunctionIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Inject private Business business;
	
	/* Create */
	
	@Test
	public void create() throws Exception{
		business.create(new MyEntity().setCode(__getRandomCode__()));
	}
	
	@Test
	public void createOneMyEntity(){
		MyEntity myEntity = new MyEntity().setCode("mc001").setTimestamp(1l);
		__inject__(BusinessFunctionCreator.class).setEntity(myEntity).execute();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionCreate.class),MyEntity.class))
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier())
			.assertContainsLastLogEventMessage("code=mc001")
			;
	}
	
	/* Find */
	
	@Test
	public void findOneMyEntityBySystemIdentifierExisting() {
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code).setTimestamp(1l);
		__inject__(BusinessFunctionCreator.class).setEntity(myEntity).execute();
		myEntity = (MyEntity) __inject__(BusinessFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(code);
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionRead.class), MyEntity.class))
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code="+code);
	}
	
	@Test
	public void findOneMyEntityByBusinessIdentifierExisting() {
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code).setTimestamp(1l);
		__inject__(BusinessFunctionCreator.class).setEntity(myEntity).execute();
		myEntity = (MyEntity) __inject__(BusinessFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(code).setEntityIdentifierValueUsageType(ValueUsageType.BUSINESS).execute().getProperties().getEntity();
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(code);
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionRead.class), MyEntity.class))
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code="+code);
	}
	/*
	@Test
	public void readOneMyEntityByIdentifierNotExisting() {
		MyEntity myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier=-1");
	}
	
	@Test
	public void readOneMyEntityByCodeExisting() {
		String code = getRandomCode();
		MyEntity myEntity = new MyEntity().setCode(code);
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(code).setEntityIdentifierValueUsageType(ValueUsageType.BUSINESS).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(code);
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code="+code);
	}
	
	@Test
	public void readOneMyEntityByCodeNotExisting() {
		MyEntity myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier=-1");
	}
	*/
}
