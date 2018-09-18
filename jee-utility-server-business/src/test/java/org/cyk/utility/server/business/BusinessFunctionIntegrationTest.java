package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.test.TestBusinessCreate;
import org.cyk.utility.server.business.test.TestBusinessDelete;
import org.cyk.utility.server.business.test.TestBusinessRead;
import org.cyk.utility.server.business.test.TestBusinessUpdate;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.server.persistence.test.FunctionRunnableTest;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class BusinessFunctionIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void createOneMyEntity() throws Exception{
		__inject__(TestBusinessCreate.class).addObjects(new MyEntity().setCode("a")).execute();
	}
	
	@Test
	public void createManyMyEntity() throws Exception{
		__inject__(TestBusinessCreate.class).addObjects(new MyEntity().setCode("a"),new MyEntity().setCode("b")).execute();
	}
	
	/* Find */
	
	@Test
	public void findOneMyEntityExistingByBusinessIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode(identifier)).setObjectClass(MyEntity.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void findOneMyEntityNonExistingByBusinessIdentifier() throws Exception{
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode("a")).setObjectClass(MyEntity.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	/* Update */
	
	@Test
	public void updateOneMyEntity() throws Exception{
		String code = "a";
		FunctionRunnableTest functionRunnable = __inject__(FunctionRunnableTest.class);
		functionRunnable.setRunnable(new Runnable() {
			@Override
			public void run() {
				MyEntity myEntity = __inject__(Business.class).findOne(MyEntity.class, code, new Properties().setValueUsageType(ValueUsageType.BUSINESS));
				myEntity.setIntegerValue(33);
				((TestBusinessUpdate)functionRunnable.getFunction()).addObjects(myEntity);
			}
		});
		
		__inject__(TestBusinessUpdate.class).addObjectsToBeCreatedArray(new MyEntity().setCode(code))
			.try_().begin().addFunctionRunnables(functionRunnable).getParent().getParentAs(TestBusinessUpdate.class)
			.try_().end().addRunnables(new Runnable() {
				@Override
				public void run() {
					MyEntity myEntity = __inject__(MyEntityBusiness.class).findOneByBusinessIdentifier(code);
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
		__inject__(TestBusinessDelete.class).addObjectsToBeCreatedArray(myEntity).addObjects(myEntity).execute();
	}
	
	/* Rules */
	
	@Test
	public void isMyEntityCodeMustBeUnique() throws Exception{
		String code = "a";
		__inject__(TestBusinessCreate.class).addObjects(new MyEntity().setCode(code),new MyEntity().setCode(code)).setName("MyEntity.code unicity")
		.setExpectedThrowableCauseClassIsSqlException().execute();
	}
	
	@Test
	public void isMyEntityCodeMustBeNotNull() throws Exception{
		TestBusinessCreate test = __inject__(TestBusinessCreate.class);
		test.addObjects(new MyEntity()).setName("MyEntity.code notnull")
		.setExpectedThrowableCauseClassIsConstraintViolationException().execute();
		
		assertThat(__inject__(ThrowableHelper.class).getInstanceOf(test.getThrowable(), ConstraintViolationException.class).getMessage())
			.contains("propertyPath=code");
	}
	
}
