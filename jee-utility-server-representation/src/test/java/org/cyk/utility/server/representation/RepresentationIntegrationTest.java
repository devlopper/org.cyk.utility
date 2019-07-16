package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.object.ObjectToStringBuilder;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.TestRepresentationRead;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class RepresentationIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		//__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		//AbstractPersistenceFunctionImpl.LOG_LEVEL = LogLevel.INFO;
		//AbstractBusinessFunctionImpl.LOG_LEVEL = LogLevel.INFO;
	}
	
	@Test
	public void create_myEntity_one() throws Exception{
		__inject__(TestRepresentationCreate.class).addObjects(new MyEntityDto().setCode("a")).execute();
	}
	
	@Test
	public void create_myEntity_many_sequential() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode(code1));
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode(code2));
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1), ValueUsageType.BUSINESS.name());
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code2), ValueUsageType.BUSINESS.name());
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
	}
	
	@Test
	public void create_myEntity_many_simultanous() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(new MyEntityDto().setCode(code1),new MyEntityDto().setCode(code2)),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
		
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1), ValueUsageType.BUSINESS.name());
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code2), ValueUsageType.BUSINESS.name());
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code1, ValueUsageType.BUSINESS.name(),null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code2, ValueUsageType.BUSINESS.name(),null).getEntity());
	}
	
	/*
	@Test
	public void create_myEntity_WithLong1Null(){
		MyEntity myEntity = new MyEntityDto().setCode("c01").setLong1(1l);
		__inject__(MyEntityRepresentation.class).createOne(myEntity);
		__deleteEntitiesAll__(MyEntity.class);
	}
	
	@Test
	public void create_myEntity_WithAssertionFail(){
		TestBusinessCreate test = __inject__(TestBusinessCreate.class);
		test.addObjects(new MyEntityDto().setCode("c01").setLong1(-11l))
			.setExpectedThrowableCauseClass(RuntimeException.class)
			.execute();
	}
	*/
	/* Find */
	
	@Test
	public void find_OneMyEntityExistingBySystemIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(new MyEntityDto().setIdentifier(identifier).setCode(identifier)).setObjectClass(MyEntityDto.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.SYSTEM).execute();
	}
	
	@Test
	public void find_OneMyEntityNonExistingBySystemIdentifier() throws Exception{
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(new MyEntityDto().setIdentifier("a").setCode("a")).setObjectClass(MyEntityDto.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.SYSTEM).execute();
	}
	
	@Test
	public void find_OneMyEntityExistingByBusinessIdentifier() throws Exception{
		String identifier = "a";
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(new MyEntityDto().setCode(identifier)).setObjectClass(MyEntityDto.class)
			.addObjectIdentifiers(identifier).setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@Test
	public void find_OneMyEntityNonExistingByBusinessIdentifier() throws Exception{
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(new MyEntityDto().setCode("a")).setObjectClass(MyEntityDto.class)
			.addObjectIdentifiers("b").addUnexistingObjectIdentifiers("b").setIdentifierValueUsageType(ValueUsageType.BUSINESS).execute();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void find_myEntity_many_by_identifier_system() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(new MyEntityDto().setIdentifier(id1).setCode(code1),new MyEntityDto().setIdentifier(id2).setCode(code2)
				,new MyEntityDto().setIdentifier(id3).setCode(code3)),null);
		Collection<Object> identifiers = ((Collection<MyEntityDto>)__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, "identifier", null).getEntity())
				.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList());
		assertThat(identifiers).containsOnly(id1,id2,id3);
		Map<String,Object> filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1,id2,id3));
		String filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList()))
			.containsOnly(code1,code2,code3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1,id3));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code1,code3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code1);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id2));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code2);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id3));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList());
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos).isNull();
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_IDENTIFIER, null);
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code1,code2,code3);
		
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(id1,id2,id3),null);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void find_myEntity_many_by_identifier_business() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(new MyEntityDto().setIdentifier(id1).setCode(code1),new MyEntityDto().setIdentifier(id2).setCode(code2)
				,new MyEntityDto().setIdentifier(id3).setCode(code3)),null);
		Collection<Object> identifiers = ((Collection<MyEntityDto>)__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, MyEntityDto.FIELD_CODE, null).getEntity())
				.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList());
		assertThat(identifiers).containsOnly(id1,id2,id3);
		
		Map<String,Object> filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList(code1,code2,code3));
		String filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()))
			.containsOnly(id1,id2,id3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList(code1,code3));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1,id3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList(code1));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList(code2));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id2);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList(code3));
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id3);
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, Arrays.asList());
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos).isNull();
		
		filters = new HashMap<>();
		filters.put(MyEntityDto.FIELD_CODE, null);
		filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filtersAsString).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1,id2,id3);
		
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1,code2,code3),ValueUsageType.BUSINESS.name());
	}
	
	/* Update */
	
	@Test
	public void update_myEntity_one() throws Exception{
		String code = __getRandomCode__();
		MyEntityDto myEntity = new MyEntityDto().setCode(code).setIntegerValue(123);
		__inject__(MyEntityRepresentation.class).createOne(myEntity);
		assertionHelper.assertEquals(123, ((MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code,"business",null).getEntity()).getIntegerValue());
		myEntity = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(code, ValueUsageType.BUSINESS.name(),null).getEntity();
		myEntity.setIntegerValue(789);
		__inject__(MyEntityRepresentation.class).updateOne(myEntity,"integerValue");
		assertionHelper.assertEquals(789, ((MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code,"business",null).getEntity()).getIntegerValue());		
		__inject__(MyEntityRepresentation.class).deleteOne(myEntity);
	}
	
	@Test
	public void update_myEntity_many() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1).setIntegerValue(123);
		MyEntityDto myEntity02 = new MyEntityDto().setCode(code2).setIntegerValue(456);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		myEntity01 = (MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code1,"business",null).getEntity();
		myEntity02 = (MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code2,"business",null).getEntity();
		assertionHelper.assertEquals(123, myEntity01.getIntegerValue());
		assertionHelper.assertEquals(456, myEntity02.getIntegerValue());
		myEntity01.setIntegerValue(26);
		myEntity02.setIntegerValue(48);
		__inject__(MyEntityRepresentation.class).updateMany(Arrays.asList(myEntity01,myEntity02),"integerValue");
		myEntity01 = ((MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code1,"business",null).getEntity());
		myEntity02 = ((MyEntityDto)__inject__(MyEntityRepresentation.class).getOne(code2,"business",null).getEntity());
		assertionHelper.assertEquals(26, myEntity01.getIntegerValue());
		assertionHelper.assertEquals(48, myEntity02.getIntegerValue());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(myEntity01.getIdentifier(),myEntity02.getIdentifier()),null);
	}
	
	/* Delete */
	
	@Test
	public void delete_myEntity_one() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		String code = __getRandomCode__();
		MyEntityDto myEntity = new MyEntityDto().setCode(code);
		__inject__(MyEntityRepresentation.class).createOne(myEntity);
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteOne(new MyEntityDto().setCode(code));
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_many() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1);
		MyEntityDto myEntity02 = new MyEntityDto().setCode(code2);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1,code2),"business");
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_system() throws Exception{
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		String code = __getRandomCode__();
		MyEntityDto myEntity = new MyEntityDto().setIdentifier(code).setCode(code);
		__inject__(MyEntityRepresentation.class).createOne(myEntity);
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code),"system");
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_system() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setIdentifier(code1).setCode(code1);
		MyEntityDto myEntity02 = new MyEntityDto().setIdentifier(code2).setCode(code2);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1,code2),"system");
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_one_by_identifier_business() throws Exception{
		String code1 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01),null);
		assertionHelper.assertEquals(1l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1),"business");
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_many_by_identifier_business() throws Exception{
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1);
		MyEntityDto myEntity02 = new MyEntityDto().setCode(code2);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1,code2),"business");
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_all() {
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1);
		MyEntityDto myEntity02 = new MyEntityDto().setCode(code2);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		try {
			__inject__(MyEntityRepresentation.class).deleteAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	/*
	/* Page */
	
	@SuppressWarnings("unchecked")
	@Test
	public void findManyByPage() {
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			dtos.add(new MyEntityDto().setIdentifier(index.toString()).setCode(index.toString()));
		__inject__(MyEntityRepresentation.class).createMany(dtos, null);
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null).getEntity();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, dtos)).containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 0l, 1l, null, null).getEntity();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, dtos)).containsExactly("0");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 1l, 1l, null, null).getEntity();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, dtos)).containsExactly("1");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 0l, 3l, null, null).getEntity();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, dtos)).containsExactly("0","1","2");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 4l, 3l, null, null).getEntity();
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, dtos)).containsExactly("4","5","6");
		
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	
	/* Save */
	/*
	@Test
	public void save_many() {
		List<MyEntityDto> list = (List<MyEntityDto>) __inject__(CollectionHelper.class).instanciate(List.class,new MyEntityDto().setCode("a").setIntegerValue(3));
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(0);
		__inject__(MyEntityRepresentation.class).saveMany(list);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(1);
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	/*
	@Test
	public void save_many_twice() {
		List<MyEntity> list = (List<MyEntity>) __inject__(CollectionHelper.class).instanciate(List.class,new MyEntityDto().setCode("a").setTimestamp(1l));
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(0);
		__inject__(MyEntityRepresentation.class).saveMany(list);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(1);
		__inject__(MyEntityRepresentation.class).saveMany(list);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(1);
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	
	@Test
	public void save_many_bybatch() {
		List<MyEntity> list = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1) {
			list.add(new MyEntityDto().setCode(index.toString()).setTimestamp(index.longValue()));
		}
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(0);
		__inject__(MyEntityRepresentation.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(100);
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	
	@Test
	public void save_many_bybatch_twice() {
		List<MyEntity> list = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1) {
			list.add(new MyEntityDto().setCode(index.toString()).setTimestamp(index.longValue()));
		}
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(0);
		__inject__(MyEntityRepresentation.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(10);
		__inject__(MyEntityRepresentation.class).saveByBatch(list, 3);
		assertThat(__inject__(MyEntityRepresentation.class).count(null).getEntity()).isEqualTo(10);
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	*/
	/* Rules */
	/*
	@Test
	public void isMyEntityCodeMustBeUnique() throws Exception{
		String code = "a";
		__inject__(TestBusinessCreate.class).addObjects(new MyEntityDto().setCode(code),new MyEntityDto().setCode(code)).setName("MyEntity.code unicity")
		.setExpectedThrowableCauseClassIsSqlException().execute();
	}
	
	@Test
	public void isMyEntityCodeMustBeNotNull() throws Exception{
		TestBusinessCreate test = __inject__(TestBusinessCreate.class);
		test.addObjects(new MyEntityDto()).setName("MyEntity.code notnull")
		.setExpectedThrowableCauseClassIsConstraintViolationException().execute();
		
		assertThat(__inject__(ThrowableHelper.class).getInstanceOf(test.getThrowable(), ConstraintViolationException.class).getMessage())
			.contains("propertyPath=code");
	}
	*/
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}
	

}
