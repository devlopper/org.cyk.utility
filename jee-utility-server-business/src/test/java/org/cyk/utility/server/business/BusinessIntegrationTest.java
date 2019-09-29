package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.server.business.api.MyEntityAssertionsProvider;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.business.api.NodeBusiness;
import org.cyk.utility.server.business.test.TestBusinessCreate;
import org.cyk.utility.server.business.test.TestBusinessRead;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.throwable.ThrowableHelper;
import org.junit.Ignore;
import org.junit.Test;

public class BusinessIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, PersistableClassesGetter.class);
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
	}
	
	@Test
	public void create_myEntity_one() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("a");
		assertThat(myEntity.getIdentifier()).isNull();
		__inject__(MyEntityBusiness.class).create(myEntity);
		assertThat(myEntity.getIdentifier()).isNotNull();
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
	public void create_myEntity_long1_1(){
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(1l);
		__inject__(MyEntityBusiness.class).create(myEntity);
	}
	
	@Test
	public void create_myEntity_long1_minus11(){
		TestBusinessCreate test = __inject__(TestBusinessCreate.class);
		test.addObjects(new MyEntity().setCode("c01").setLong1(-11l))
			.setExpectedThrowableCauseClass(RuntimeException.class)
			//.setIsCatchThrowable(Boolean.FALSE)
			;
		test.execute();
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
	
	@Test
	public void find_myEntity_many_by_identifier_system_filter() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		Collection<Object> identifiers = __inject__(MyEntityBusiness.class).find().stream().map(MyEntity::getIdentifier).collect(Collectors.toList());
		assertThat(identifiers).containsOnly(id1,id2,id3);
		Filter filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, Arrays.asList(id1));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id1);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, Arrays.asList(id2));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id2);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, Arrays.asList(id3));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, Arrays.asList(id1,id3));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id1,id3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, Arrays.asList());
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).isEmpty();
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_IDENTIFIER, null);
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id1,id2,id3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getIdentifier).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(id1,id2,id3);
		
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifiers(Arrays.asList(id1,id2,id3));
	}
	
	@Test
	public void find_myEntity_many_by_identifier_business_filter() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(new MyEntity().setIdentifier(id1).setCode(code1),new MyEntity().setIdentifier(id2).setCode(code2)
				,new MyEntity().setIdentifier(id3).setCode(code3)));
		Collection<Object> identifiers = __inject__(MyEntityBusiness.class).find().stream().map(MyEntity::getCode).collect(Collectors.toList());
		assertThat(identifiers).containsOnly(code1,code2,code3);
		Filter filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, Arrays.asList(code1));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code1);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, Arrays.asList(code2));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code2);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, Arrays.asList(code3));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, Arrays.asList(code1,code3));
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code1,code3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, Arrays.asList());
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).isEmpty();
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		filters.addField(MyEntity.FIELD_CODE, null);
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code1,code2,code3);
		
		filters = __inject__(Filter.class).setKlass(MyEntity.class);
		identifiers = __inject__(MyEntityBusiness.class).find(new Properties().setQueryFilters(filters)).stream().map(MyEntity::getCode).collect(Collectors.toList());		
		assertThat(identifiers).containsOnly(code1,code2,code3);
		
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
	
	@Test
	public void delete_myEntity_all_specific() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(MyEntityBusiness.class).deleteAll();
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_all_generic_byClass() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(Business.class).deleteByClasses(MyEntity.class);
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	@Test
	public void delete_myEntity_all_generic() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
		MyEntity myEntity01 = new MyEntity().setCode(__getRandomCode__());
		MyEntity myEntity02 = new MyEntity().setCode(__getRandomCode__());
		__inject__(MyEntityBusiness.class).createMany(Arrays.asList(myEntity01,myEntity02));
		assertionHelper.assertEquals(2l, __inject__(MyEntityBusiness.class).count());
		__inject__(Business.class).deleteAll();
		assertionHelper.assertEquals(0l, __inject__(MyEntityBusiness.class).count());
	}
	
	/* Page */
	
	@Test
	public void findManyByPage() {
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			__inject__(MyEntityBusiness.class).create(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()));
				
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find()))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(null)))
		.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		Properties properties = new Properties();
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(1);
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(1);
		properties.setQueryNumberOfTuple(1);
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("1");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(0);
		properties.setQueryNumberOfTuple(3);
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("0","1","2");
		
		properties = new Properties();
		properties.setQueryFirstTupleIndex(4);
		properties.setQueryNumberOfTuple(3);
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(__inject__(MyEntityBusiness.class).find(properties)))
			.containsExactly("4","5","6");
	}
	
	/* Save */
	
	@Test
	public void save_many() {
		List<MyEntity> list = List.of(new MyEntity().setCode("a").setTimestamp(1l));
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
	}
	
	@Test
	public void save_many_twice() {
		List<MyEntity> list = List.of(new MyEntity().setCode("a").setTimestamp(1l));
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(0);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
		__inject__(MyEntityBusiness.class).saveMany(list);
		assertThat(__inject__(MyEntityBusiness.class).count()).isEqualTo(1);
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
	
	/* Hierarchy */
	
	@Test
	public void create_node() throws Exception{
		Node nodeModule = new Node().setCode("module").setName(__getRandomName__());
		Node nodeService = new Node().setCode("service").setName(__getRandomName__()).addParents(nodeModule);
		Node nodeMenu = new Node().setCode("menu").setName(__getRandomName__()).addParents(nodeService);
		Node nodeAction = new Node().setCode("action").setName(__getRandomName__()).addParents(nodeMenu);
		__inject__(NodeBusiness.class).createMany(List.of(nodeModule,nodeService,nodeMenu
				,nodeAction));
		Node node;
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
		
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
		
		__inject__(NodeBusiness.class).delete(nodeModule);
	}
	
	@Test @Ignore
	public void update_node() throws Exception{
		Node nodeModule = new Node().setCode("module").setName(__getRandomName__());
		Node nodeService = new Node().setCode("service").setName(__getRandomName__()).addParents(nodeModule);
		Node nodeMenu = new Node().setCode("menu").setName(__getRandomName__()).addParents(nodeService);
		Node nodeAction = new Node().setCode("action").setName(__getRandomName__()).addParents(nodeMenu);
		__inject__(NodeBusiness.class).createMany(List.of(nodeModule,nodeService,nodeMenu
				,nodeAction));
		Node node;
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("module",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
		
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("service",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren()).isNotEmpty();
		assertThat(node.getChildren().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action");
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_PARENTS));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = __inject__(NodeBusiness.class).findByBusinessIdentifier("action",new Properties().setFields(Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN));
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents()).isNotEmpty();
		assertThat(node.getParents().stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
	}
	
	@Test
	public void read_node_filter_byParent_identifier_business() throws Exception{
		Node nodeModule = new Node().setIdentifier("MO").setCode("module").setName(__getRandomName__());
		Node nodeService = new Node().setIdentifier("S").setCode("service").setName(__getRandomName__()).addParents(nodeModule);
		Node nodeMenu = new Node().setIdentifier("ME").setCode("menu").setName(__getRandomName__()).addParents(nodeService);
		Node nodeAction = new Node().setIdentifier("A").setCode("action").setName(__getRandomName__()).addParents(nodeMenu);
		__inject__(NodeBusiness.class).createMany(List.of(nodeModule,nodeService,nodeMenu
				,nodeAction));
		Filter filters = __inject__(Filter.class).setKlass(Node.class);
		filters.addField(Node.FIELD_PARENTS, Arrays.asList("module"),ValueUsageType.BUSINESS);
		Collection<Node> nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(filters));
		assertThat(nodes).isNotEmpty();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
	}
	
	@Test
	public void read_node_filter_byParent_identifier_system() throws Exception{
		Node nodeModule = new Node().setIdentifier("MO").setCode("module").setName(__getRandomName__());
		Node nodeService = new Node().setIdentifier("S").setCode("service").setName(__getRandomName__()).addParents(nodeModule);
		Node nodeMenu = new Node().setIdentifier("ME").setCode("menu").setName(__getRandomName__()).addParents(nodeService);
		Node nodeAction = new Node().setIdentifier("A").setCode("action").setName(__getRandomName__()).addParents(nodeMenu);
		__inject__(NodeBusiness.class).createMany(List.of(nodeModule,nodeService,nodeMenu
				,nodeAction));
		Filter filters = __inject__(Filter.class).setKlass(Node.class);
		filters.addField(Node.FIELD_PARENTS, Arrays.asList("MO"),ValueUsageType.SYSTEM);
		Collection<Node> nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(filters));
		assertThat(nodes).isNotEmpty();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("service");
	}
	
	@Test
	public void read_nodes() {
		Integer numberOfNodesLevel0 = 4;
		Integer numberOfNodesLevel1 = 3;
		Integer numberOfNodesLevel2 = 2;
		for(Integer indexNumberOfNodesLevel0 = 0 ; indexNumberOfNodesLevel0 < numberOfNodesLevel0 ; indexNumberOfNodesLevel0 = indexNumberOfNodesLevel0 + 1) {
			Node nodeLevel0 = __inject__(Node.class).setCode(indexNumberOfNodesLevel0.toString()).setName(__getRandomName__());
			__inject__(NodeBusiness.class).create(nodeLevel0);
			for(Integer indexNumberOfNodesLevel1 = 0 ; indexNumberOfNodesLevel1 < numberOfNodesLevel1 ; indexNumberOfNodesLevel1 = indexNumberOfNodesLevel1 + 1) {
				Node nodeLevel1 = __inject__(Node.class).setCode(nodeLevel0.getCode()+"."+indexNumberOfNodesLevel1.toString()).setName(__getRandomName__()).addParents(nodeLevel0);
				__inject__(NodeBusiness.class).create(nodeLevel1);
				for(Integer indexNumberOfNodesLevel2 = 0 ; indexNumberOfNodesLevel2 < numberOfNodesLevel2 ; indexNumberOfNodesLevel2 = indexNumberOfNodesLevel2 + 1) {
					Node nodeLevel2 = __inject__(Node.class).setCode(nodeLevel1.getCode()+"."+indexNumberOfNodesLevel2.toString()).setName(__getRandomName__()).addParents(nodeLevel1);
					__inject__(NodeBusiness.class).create(nodeLevel2);
				}	
			}	
		}
		Collection<Node> nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, null)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0","1","2","3");
		assertThat(__inject__(NodeBusiness.class).count(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, null)))).isEqualTo(4l);
		
		nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("0"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0.0","0.1","0.2");
		assertThat(__inject__(NodeBusiness.class).count(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("0"))))).isEqualTo(3l);
		
		nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("0.0"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0.0.0","0.0.1");
		assertThat(__inject__(NodeBusiness.class).count(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("0.0"))))).isEqualTo(2l);
		
		nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("1"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("1.0","1.1","1.2");
		
		nodes = __inject__(NodeBusiness.class).find(new Properties().setQueryFilters(__inject__(Filter.class).setKlass(Node.class).addField(Node.FIELD_PARENTS, Arrays.asList("1.1"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("1.1.0","1.1.1");
	}
	
}
