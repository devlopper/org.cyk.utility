package org.cyk.utility.server.persistence;

import java.util.Arrays;
import java.util.List;

import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Assert;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PersistenceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(PersistenceQueryRepository.class).add(new PersistenceQuery().setIdentifier("MyEntity.readAll").setValue("SELECT r FROM MyEntity r")
				.setResultClass(MyEntity.class));
		__inject__(MyEntityPersistence.class).read();//to trigger initialisation
	}
	
	@Test
	public void buildQueryIdentifierStringFromName(){
		Assert.assertEquals("MyEntity.readByValue", __inject__(PersistenceQueryIdentifierStringBuilder.class)
				.setClassSimpleName("MyEntity").setName("readByValue").execute().getOutput());
	}
	
	@Test
	public void buildQueryIdentifierStringFromDerivedFromQueryIdentifier(){
		Assert.assertEquals("MyEntity.countByValue", __inject__(PersistenceQueryIdentifierStringBuilder.class)
				.setIsDerivedFromQueryIdentifier(Boolean.TRUE).setDerivedFromQueryIdentifier("MyEntity.readByValue")
				.setIsCountInstances(Boolean.TRUE).execute().getOutput());
	}
	
	@Test
	public void executeQueryReadMyEntityAllFromRepository() throws Exception{		
		userTransaction.begin();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc001")).execute();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc002")).execute();
		__inject__(PersistenceFunctionCreator.class).setEntity(new MyEntity().setCode("mc003")).execute();
		userTransaction.commit();
		
		@SuppressWarnings("unchecked")
		List<MyEntity> results = (List<MyEntity>) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class).setQueryIdentifier("MyEntity.read")
				.execute().getProperties().getEntities();
		
		Assert.assertEquals(3, results.size());
		//System.out.println(results);
		/*
		myEntity = (MyEntity) __inject__(PersistenceFunctionReader.class).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read MyEntity")
			.assertContainsLastLogEventMessage("identifier="+myEntity.getIdentifier()).assertContainsLastLogEventMessage("code=mc001");
		*/
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteAll();
		userTransaction.commit();
	}
	
	/* Create */
	
	@Test
	public void create_myEntity_one() throws Exception{
		String code1 = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code1);
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		myEntity = __inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS);
		assertionHelper.assertNotNull(myEntity);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByIdentifier(code1, ValueUsageType.BUSINESS);
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		myEntity = __inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS);
		assertionHelper.assertNull(myEntity);
	}
	
	@Test
	public void create_myEntity_many_sequential() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(code1));
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(new MyEntity().setCode(code2));
		userTransaction.commit();
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByIdentifier(code1, ValueUsageType.BUSINESS);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByIdentifier(code2, ValueUsageType.BUSINESS);
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
	}
	
	@Test
	public void create_myEntity_many_simultanous() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(new MyEntity().setCode(code1),new MyEntity().setCode(code2)));
		userTransaction.commit();
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByIdentifier(code1, ValueUsageType.BUSINESS);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByIdentifier(code2, ValueUsageType.BUSINESS);
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(code2, ValueUsageType.BUSINESS));
	}
	
	/* Read */
	
	@Test
	public void read_myEntity_one_by_identifier_system() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(myEntity.getIdentifier(), ValueUsageType.SYSTEM));
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(myEntity.getIdentifier(), ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).delete(myEntity);
		userTransaction.commit();
	}
	
	@Test
	public void read_myEntity_one_by_identifier_business() throws Exception{
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertNull(__inject__(MyEntityPersistence.class).readOne(myEntity.getCode() ,ValueUsageType.SYSTEM));
		assertionHelper.assertNotNull(__inject__(MyEntityPersistence.class).readOne(myEntity.getCode(), ValueUsageType.BUSINESS));
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).delete(myEntity);
		userTransaction.commit();
	}
	
	@Test
	public void read_myEntity_many_by_identifier_system() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		userTransaction.commit();
		assertThat(__inject__(MyEntityPersistence.class).readSystemIdentifiers()).containsOnly(id1,id2,id3);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(Arrays.asList(id1,id2,id3));
		userTransaction.commit();
	}
	
	@Test
	public void read_myEntity_many_by_identifier_business() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		userTransaction.commit();
		assertThat(__inject__(MyEntityPersistence.class).readBusinessIdentifiers()).containsOnly(code1,code2,code3);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyByBusinessIdentifiers(Arrays.asList(code1,code2,code3));
		userTransaction.commit();
	}
	
	/* Update */
	
	@Test
	public void update_myEntity_one() throws Exception{
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code).setIntegerValue(123);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(123, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(code).getIntegerValue());
		myEntity = __inject__(MyEntityPersistence.class).readOne(code, ValueUsageType.BUSINESS);
		myEntity.setIntegerValue(789);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).update(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(789, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(code).getIntegerValue());		
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).delete(myEntity);
		userTransaction.commit();
	}
	
	@Test
	public void update_myEntity_many() throws Exception{
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__()).setIntegerValue(123);
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__()).setIntegerValue(456);
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
		assertionHelper.assertEquals(123, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(myEntity01.getCode()).getIntegerValue());
		assertionHelper.assertEquals(456, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(myEntity02.getCode()).getIntegerValue());
		myEntity01 = __inject__(MyEntityPersistence.class).readOne(myEntity01.getCode(), ValueUsageType.BUSINESS);
		myEntity02 = __inject__(MyEntityPersistence.class).readOne(myEntity02.getCode(), ValueUsageType.BUSINESS);
		myEntity01.setIntegerValue(26);
		myEntity02.setIntegerValue(48);
		userTransaction.begin();
		try {
			__inject__(MyEntityPersistence.class).updateMany(Arrays.asList(myEntity01,myEntity02));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userTransaction.commit();
		assertionHelper.assertEquals(26, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(myEntity01.getCode()).getIntegerValue());
		assertionHelper.assertEquals(48, __inject__(MyEntityPersistence.class).readOneByBusinessIdentifier(myEntity02.getCode()).getIntegerValue());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
	}
	
	/* Delete */
	
	@Test
	public void delete_myEntity_one() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).delete(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
	
	@Test
	public void delete_myEntity_many() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_system() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteBySystemIdentifier(myEntity.getIdentifier());
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_system() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyBySystemIdentifiers(Arrays.asList(myEntity01.getIdentifier(),myEntity02.getIdentifier()));
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_business() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).create(myEntity);
		userTransaction.commit();
		assertionHelper.assertEquals(1l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteByBusinessIdentifier(myEntity.getCode());
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_business() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).createMany(Arrays.asList(myEntity01,myEntity02));
		userTransaction.commit();
		assertionHelper.assertEquals(2l, __inject__(MyEntityPersistence.class).count());
		userTransaction.begin();
		__inject__(MyEntityPersistence.class).deleteManyByBusinessIdentifiers(Arrays.asList(myEntity01.getCode(),myEntity02.getCode()));
		userTransaction.commit();
		assertionHelper.assertEquals(0l, __inject__(MyEntityPersistence.class).count());
	}
}
