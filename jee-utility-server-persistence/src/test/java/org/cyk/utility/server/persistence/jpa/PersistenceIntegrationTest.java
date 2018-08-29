package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.test.FunctionRunnableTest;
import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.cyk.utility.server.persistence.test.TestPersistenceDelete;
import org.cyk.utility.server.persistence.test.TestPersistenceRead;
import org.cyk.utility.server.persistence.test.TestPersistenceUpdate;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class PersistenceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
		
	@Test
	public void create() throws Exception{
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode("a")).execute();
	}
	
	@Test
	public void read() throws Exception{
		String code = "a";
		__inject__(TestPersistenceRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode(code)).setObjectClass(MyEntity.class).addObjectIdentifiers(code).execute();
	}
	
	@Test
	public void update() throws Exception{
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
			.addExecutionPhaseFunctionRunnables(Boolean.TRUE, functionRunnable)
			.addExecutionPhaseRunnables(Boolean.FALSE, new Runnable() {
				@Override
				public void run() {
					MyEntity myEntity = __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(code);
					assertionHelper.assertNotNull("object with business identifier <"+code+"> not found",myEntity);
					assertionHelper.assertEqualsNumber("integerValue("+myEntity.getIntegerValue()+") is not equal to 33", 33, myEntity.getIntegerValue());
				}
			})
			.execute();
	}
	
	@Test
	public void delete() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("a");
		__inject__(TestPersistenceDelete.class).addObjectsToBeCreatedArray(myEntity).addObjects(myEntity).execute();
	}
	
	@Test
	public void isCodeMustBeUnique() throws Exception{
		String code = "a";
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode(code),new MyEntity().setCode(code)).setName("MyEntity.code unicity")
		.setExpectedThrowableCauseClassIsSqlException().execute();
	}
	
	@Test
	public void isCodeMustBeNotNull() throws Exception{
		TestPersistenceCreate test = __inject__(TestPersistenceCreate.class);
		test.addObjects(new MyEntity()).setName("MyEntity.code notnull")
		.setExpectedThrowableCauseClassIsConstraintViolationException().execute();
		
		assertThat(__inject__(ThrowableHelper.class).getInstanceOf(test.getThrowable(), ConstraintViolationException.class).getMessage())
			.contains("propertyPath=code");
	}
	
}
