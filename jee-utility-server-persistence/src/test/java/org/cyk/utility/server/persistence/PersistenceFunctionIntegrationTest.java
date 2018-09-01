package org.cyk.utility.server.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.jpa.Persistence;
import org.cyk.utility.server.persistence.test.FunctionRunnableTest;
import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.cyk.utility.server.persistence.test.TestPersistenceDelete;
import org.cyk.utility.server.persistence.test.TestPersistenceRead;
import org.cyk.utility.server.persistence.test.TestPersistenceUpdate;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class PersistenceFunctionIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void createOneMyEntity() throws Exception{
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode("a")).execute();
	}
	
	@Test
	public void createManyMyEntity() throws Exception{
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode("a"),new MyEntity().setCode("b")).execute();
	}
	
	/* Read */
	
	@Test
	public void readOneMyEntityExistingByBusinessIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestPersistenceRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode(identifier)).setObjectClass(MyEntity.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void readOneMyEntityNonExistingByBusinessIdentifier() throws Exception{
		__inject__(TestPersistenceRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode("a")).setObjectClass(MyEntity.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void readOneByIdentifierExisting() throws Exception{
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
	public void readOneByIdentifierNotExisting() throws Exception{
		MyEntity myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier=-1");
	}
	
	/* Update */
	
	@Test
	public void updateOneMyEntity() throws Exception{
		String code = "a";
		FunctionRunnableTest functionRunnable = __inject__(FunctionRunnableTest.class);
		functionRunnable.setRunnable(new Runnable() {
			@Override
			public void run() {
				MyEntity myEntity = __inject__(Persistence.class).readOne(MyEntity.class, code, new Properties().setValueUsageType(ValueUsageType.BUSINESS));
				myEntity.setIntegerValue(33);
				((TestPersistenceUpdate)functionRunnable.getFunction()).addObjects(myEntity);
			}
		});
		
		__inject__(TestPersistenceUpdate.class).addObjectsToBeCreatedArray(new MyEntity().setCode(code))
			.try_().begin().addFunctionRunnables(functionRunnable).getParent().getParentAs(TestPersistenceUpdate.class)
			.try_().end().addRunnables(new Runnable() {
				@Override
				public void run() {
					MyEntity myEntity = __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(code);
					assertionHelper.assertNotNull("object with business identifier <"+code+"> not found",myEntity);
					assertionHelper.assertEqualsNumber("integerValue("+myEntity.getIntegerValue()+") is not equal to 33", 33, myEntity.getIntegerValue());
				}
			}).getParent().getParent()
			.execute();
	}
	
	/* Delete */
	
	@Test
	public void deleteOneMyEntity() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("a");
		__inject__(TestPersistenceDelete.class).addObjectsToBeCreatedArray(myEntity).addObjects(myEntity).execute();
	}
	
	/* Rules */
	
	@Test
	public void isMyEntityCodeMustBeUnique() throws Exception{
		String code = "a";
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode(code),new MyEntity().setCode(code)).setName("MyEntity.code unicity")
		.setExpectedThrowableCauseClassIsSqlException().execute();
	}
	
	@Test
	public void isMyEntityCodeMustBeNotNull() throws Exception{
		TestPersistenceCreate test = __inject__(TestPersistenceCreate.class);
		test.addObjects(new MyEntity()).setName("MyEntity.code notnull")
		.setExpectedThrowableCauseClassIsConstraintViolationException().execute();
		
		assertThat(__inject__(ThrowableHelper.class).getInstanceOf(test.getThrowable(), ConstraintViolationException.class).getMessage())
			.contains("propertyPath=code");
	}
}
