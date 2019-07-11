package org.cyk.utility.server.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.test.FunctionRunnableTest;
import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.cyk.utility.server.persistence.test.TestPersistenceDelete;
import org.cyk.utility.server.persistence.test.TestPersistenceRead;
import org.cyk.utility.server.persistence.test.TestPersistenceUpdate;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class PersistenceFunctionIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void createOneMyEntity() {
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode("a")).execute();
	}
	
	@Test
	public void createManyMyEntity() {
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode("a"),new MyEntity().setCode("b")).execute();
	}
	
	@Test
	public void createOneMyEntityIdentifiedByString() {
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntityIdentifiedByString()).setIsCatchThrowable(Boolean.FALSE).execute();
	}
	
	/* Read */
	
	@Test
	public void readOneMyEntityExistingByBusinessIdentifier() {
		String identifier = "a";
		__inject__(TestPersistenceRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode(identifier)).setObjectClass(MyEntity.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void readOneMyEntityNonExistingByBusinessIdentifier() {
		__inject__(TestPersistenceRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode("a")).setObjectClass(MyEntity.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void readOneByIdentifierExisting() throws Exception{
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code);
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(code);
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code=mc001");
	}
	
	@Test
	public void readOneByIdentifierNotExisting() throws Exception{
		MyEntity myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setEntityIdentifier("azerty").execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity").assertContainsLastLogEventMessage(PersistenceFunctionReader.MESSAGE_NOT_FOUND)
			.assertContainsLastLogEventMessage("identifier=-1");
	}
	
	@Test
	public void readManyByPage() throws Exception{
		userTransaction.begin();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			__inject__(MyEntityPersistence.class).create(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()));
		userTransaction.commit();
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany()))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(null)))
		.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).read()))
		.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).read(null)))
		.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		Properties properties = new Properties();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(properties)))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		properties = new Properties();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).read(properties)))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(1);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(properties)))
			.containsExactly("0");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(1);
		properties.setQueryNumberOfTuple(1);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(properties)))
			.containsExactly("1");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(3);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(properties)))
			.containsExactly("0","1","2");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(4);
		properties.setQueryNumberOfTuple(3);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityPersistence.class).readMany(properties)))
			.containsExactly("4","5","6");
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
	
	@Test
	public void isMyEntityFieldIntegerValueValueNotLoaded() throws Exception{
		String identifier = __getRandomIdentifier__();
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setIdentifier(identifier).setCode(code).setIntegerValue(159);
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		//assertionHelper.assertNull("field integer value has been loaded", myEntity.getIntegerValue());
	}
}
