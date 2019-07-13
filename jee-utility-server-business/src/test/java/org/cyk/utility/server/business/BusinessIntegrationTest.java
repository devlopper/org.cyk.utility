package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.business.test.TestBusinessCreate;
import org.cyk.utility.server.business.test.TestBusinessRead;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class BusinessIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		//AbstractPersistenceFunctionImpl.LOG_LEVEL = LogLevel.INFO;
		//AbstractBusinessFunctionImpl.LOG_LEVEL = LogLevel.INFO;
	}
	
	@Test
	public void create_myEntity_one() throws Exception{
		__inject__(TestBusinessCreate.class).addObjects(new MyEntity().setCode("a")).execute();
	}
	
	@Test
	public void create_myEntity_many_sequential() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code1));
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code2));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
		__inject__(MyEntityBusiness.class).deleteByIdentifier(code1, ValueUsageType.BUSINESS);
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
		__inject__(MyEntityBusiness.class).deleteByIdentifier(code2, ValueUsageType.BUSINESS);
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
	}
	
	@Test
	public void create_myEntity_many_simultanous() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(new MyEntity().setCode(code1),new MyEntity().setCode(code2)));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
		
		__inject__(MyEntityBusiness.class).deleteByIdentifier(code1, ValueUsageType.BUSINESS);
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNotNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
		__inject__(MyEntityBusiness.class).deleteByIdentifier(code2, ValueUsageType.BUSINESS);
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code1, ValueUsageType.BUSINESS));
		assertionHelper.assertNull(__inject__(MyEntityBusiness.class).findByIdentifier(code2, ValueUsageType.BUSINESS));
	}
	
	
	@Test
	public void create_myEntity_WithLong1Null(){
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(1l);
		__inject__(MyEntityBusiness.class).create(myEntity);
		__deleteEntitiesAll__(MyEntity.class);
	}
	
	@Test
	public void create_myEntity_WithAssertionFail(){
		TestBusinessCreate test = __inject__(TestBusinessCreate.class);
		test.addObjects(new MyEntity().setCode("c01").setLong1(-11l))
			.setExpectedThrowableCauseClass(RuntimeException.class)
			.execute();
	}
	
	/* Find */
	
	@Test
	public void find_OneMyEntityExistingBySystemIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setIdentifier(identifier).setCode(identifier)).setObjectClass(MyEntity.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.SYSTEM).execute();
	}
	
	@Test
	public void find_OneMyEntityNonExistingBySystemIdentifier() throws Exception{
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setIdentifier("a").setCode("a")).setObjectClass(MyEntity.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.SYSTEM).execute();
	}
	
	@Test
	public void find_OneMyEntityExistingByBusinessIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode(identifier)).setObjectClass(MyEntity.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void find_OneMyEntityNonExistingByBusinessIdentifier() throws Exception{
		__inject__(TestBusinessRead.class).addObjectsToBeCreatedArray(new MyEntity().setCode("a")).setObjectClass(MyEntity.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void find_myEntity_many_by_identifier_system() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		Collection<Object> identifiers = __inject__(MyEntityBusiness.class).findSystemIdentifiers();
		assertThat(identifiers).containsOnly(id1,id2,id3);
		assertThat(__inject__(MyEntityBusiness.class).findBySystemIdentifiers(identifiers).stream().map(MyEntity::getCode).collect(Collectors.toList()))
			.containsOnly(code1,code2,code3);
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(Arrays.asList(id1,id2,id3));
	}
	
	
	@Test
	public void find_myEntity_many_by_identifier_business() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		Collection<Object> identifiers = __inject__(MyEntityBusiness.class).findBusinessIdentifiers();
		assertThat(identifiers).containsOnly(code1,code2,code3);
		assertThat(__inject__(MyEntityBusiness.class).findByBusinessIdentifiers(identifiers).stream().map(MyEntity::getIdentifier).collect(Collectors.toList()))
			.containsOnly(id1,id2,id3);
		__inject__(MyEntityBusiness.class).deleteByBusinessIdentifiers(Arrays.asList(code1,code2,code3));
	}
	
	/* Update */
	
	@Test
	public void update_myEntity_one() throws Exception{
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code).setIntegerValue(123);
		__inject__(MyEntityBusiness.class).create(myEntity);
		assertionHelper.assertEquals(123, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(code).getIntegerValue());
		myEntity = __inject__(MyEntityBusiness.class).findByIdentifier(code, ValueUsageType.BUSINESS);
		myEntity.setIntegerValue(789);
		__inject__(MyEntityBusiness.class).update(myEntity);
		assertionHelper.assertEquals(789, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(code).getIntegerValue());		
		__inject__(MyEntityBusiness.class).delete(myEntity);
	}
	
	@Test
	public void update_myEntity_many() throws Exception{
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__()).setIntegerValue(123);
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__()).setIntegerValue(456);
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(123, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(myEntity01.getCode()).getIntegerValue());
		assertionHelper.assertEquals(456, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(myEntity02.getCode()).getIntegerValue());
		myEntity01 = __inject__(MyEntityBusiness.class).findByIdentifier(myEntity01.getCode(), ValueUsageType.BUSINESS);
		myEntity02 = __inject__(MyEntityBusiness.class).findByIdentifier(myEntity02.getCode(), ValueUsageType.BUSINESS);
		myEntity01.setIntegerValue(26);
		myEntity02.setIntegerValue(48);
		__inject__(MyEntityBusiness.class).updateMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(26, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(myEntity01.getCode()).getIntegerValue());
		assertionHelper.assertEquals(48, __inject__(MyEntityBusiness.class).findByBusinessIdentifier(myEntity02.getCode()).getIntegerValue());
		__inject__(MyEntityBusiness.class).deleteMany(Arrays.asList(myEntity01,myEntity02));
	}

	/* Delete */
	
	@Test
	public void delete_myEntity_one() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).create(myEntity);
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).delete(myEntity);
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_many() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_system() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).create(myEntity);
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifier(myEntity.getIdentifier());
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_system() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(Arrays.asList(myEntity01.getIdentifier(),myEntity02.getIdentifier()));
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_business() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).create(myEntity);
		assertionHelper.assertEquals(1l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteByBusinessIdentifier(myEntity.getCode());
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_business() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteByBusinessIdentifiers(Arrays.asList(myEntity01.getCode(),myEntity02.getCode()));
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	/* Page */
	
	@Test
	public void findManyByPage() {
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			__inject__(MyEntityBusiness.class).create(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()));
				
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find()))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(null)))
		.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		Properties properties = new Properties();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(1);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(1);
		properties.setQueryNumberOfTuple(1);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("1");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(3);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0","1","2");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(4);
		properties.setQueryNumberOfTuple(3);
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, __inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("4","5","6");
		
		__inject__(MyEntityBusiness.class).deleteAll();
	}
	
	/* Save */
	
	@Test
	public void save_many() {
		List<MyEntity> list = (List<MyEntity>) __inject__(CollectionHelper.class).instanciate(List.class,new MyEntity().setCode("a").setTimestamp(1l));
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
		__inject__(MyEntityBusiness.class).deleteAll();
	}
	
	@Test
	public void save_many_twice() {
		List<MyEntity> list = (List<MyEntity>) __inject__(CollectionHelper.class).instanciate(List.class,new MyEntity().setCode("a").setTimestamp(1l));
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
		__inject__(MyEntityBusiness.class).deleteAll();
	}
	
	@Test
	public void save_many_bybatch() {
		List<MyEntity> list = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1) {
			list.add(new MyEntity().setCode(index.toString()).setTimestamp(index.longValue()));
		}
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(100);
		__inject__(MyEntityBusiness.class).deleteAll();
	}
	
	@Test
	public void save_many_bybatch_twice() {
		List<MyEntity> list = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1) {
			list.add(new MyEntity().setCode(index.toString()).setTimestamp(index.longValue()));
		}
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(10);
		__inject__(MyEntityBusiness.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(10);
		__inject__(MyEntityBusiness.class).deleteAll();
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
