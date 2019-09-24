package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.server.business.api.MyEntityAssertionsProvider;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.business.api.NodeBusiness;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.api.NodeRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.entities.MyEntityDtoCollection;
import org.cyk.utility.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.test.TestRepresentationRead;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class RepresentationIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, PersistableClassesGetter.class);
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
		__inject__(ClassInstancesRuntime.class).get(MyEntity.class).setIsActionable(Boolean.TRUE);
	}
	
	@Test
	public void create_myEntity_one_null() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		Response response = myEntityRepresentation.createOne(null);
		__assertResponseTransaction__(response, Status.BAD_REQUEST, null, null, null);
	}
	
	@Test
	public void create_myEntity_one() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		MyEntityDto entity = new MyEntityDto().setCode("a");
		assertThat(entity.getIdentifier()).isBlank();
		assertThat(entity.getIdentifier()).isBlank();
		Response response = myEntityRepresentation.createOne(entity);
		__assertResponseTransaction__(response, Status.CREATED, 1l, List.of(entity.getIdentifier()), List.of("a"));
		assertThat(entity.getIdentifier()).isNotBlank();
		entity = (MyEntityDto) myEntityRepresentation.getOne(entity.getIdentifier(), "system", null).getEntity();
		assertThat(entity).isNotNull();
		Action action = null;
		action = entity.__get__action__byIdentifier(Action.IDENTIFIER_READ);
		assertThat(action).isNotNull();
		assertThat(action.getMethod()).isEqualTo(Action.METHOD_GET);
		assertThat(action.getUniformResourceLocator()).endsWith("/"+entity.getIdentifier());
		action = entity.__get__action__byIdentifier(Action.IDENTIFIER_UPDATE);
		assertThat(action).isNotNull();
		assertThat(action.getMethod()).isEqualTo(Action.METHOD_PUT);
		action = entity.__get__action__byIdentifier(Action.IDENTIFIER_DELETE);
		assertThat(action).isNotNull();
		assertThat(action.getMethod()).isEqualTo(Action.METHOD_DELETE);
		assertThat(action.getUniformResourceLocator()).endsWith("/delete/identifiers?identifier="+entity.getIdentifier());
	}
	
	@Test
	public void create_myEntity_many_collection_java_null() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		Response response = myEntityRepresentation.createMany((Collection<MyEntityDto>)null,null);
		__assertResponseTransaction__(response, Status.BAD_REQUEST, null, null, null);
	}
	
	@Test
	public void create_myEntity_many_collection_java_empty() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		Response response = myEntityRepresentation.createMany(List.of(),null);
		__assertResponseTransaction__(response, Status.BAD_REQUEST, null, null, null);
	}
	
	@Test
	public void create_myEntity_many_collection_java() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		List<MyEntityDto> myEntityDtos = List.of(new MyEntityDto().setCode("a"),new MyEntityDto().setCode("b"));
		Response response = myEntityRepresentation.createMany(myEntityDtos,null);
		__assertResponseTransaction__(response, Status.CREATED, 2l, myEntityDtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()), List.of("a","b"));
	}
	
	@Test
	public void create_myEntity_many_collection_custom_null() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		Response response = myEntityRepresentation.createMany((MyEntityDtoCollection)null,null);
		__assertResponseTransaction__(response, Status.BAD_REQUEST, null, null, null);
	}
	
	@Test
	public void create_myEntity_many_collection_custom_empty() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		Response response = myEntityRepresentation.createMany(new MyEntityDtoCollection(),null);
		__assertResponseTransaction__(response, Status.BAD_REQUEST, null, null, null);
	}
	
	@Test
	public void create_myEntity_many_collection_custom() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __inject__(MyEntityRepresentation.class);
		MyEntityDtoCollection myEntityDtoCollection = new MyEntityDtoCollection(); 
		myEntityDtoCollection.add(new MyEntityDto().setCode("a"),new MyEntityDto().setCode("b"));
		Response response = myEntityRepresentation.createMany(myEntityDtoCollection,null);
		__assertResponseTransaction__(response, Status.CREATED, 2l, myEntityDtoCollection.getElements().stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()), List.of("a","b"));
	}
	
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
		FilterDto filters = __inject__(FilterDto.class).addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1,id2,id3));
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList()))
			.containsOnly(code1,code2,code3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1,id3));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code1,code3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id1));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code1);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id2));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code2);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList(id3));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getCode).collect(Collectors.toList())).containsOnly(code3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, Arrays.asList());
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos).isNull();
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_IDENTIFIER, null);
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
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
		Collection<Object> identifiers = ((Collection<MyEntityDto>)__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, MyEntityDto.FIELD_IDENTIFIER, null).getEntity())
				.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList());
		assertThat(identifiers).containsOnly(id1,id2,id3);
		
		FilterDto filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList(code1,code2,code3));
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()))
			.containsOnly(id1,id2,id3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList(code1,code3));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1,id3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList(code1));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList(code2));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id2);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList(code3));
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id3);
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, Arrays.asList());
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos).isNull();
		
		filters = __inject__(FilterDto.class);
		filters.addField(MyEntityDto.FIELD_CODE, null);
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,filters).getEntity();
		assertThat(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList())).containsOnly(id1,id2,id3);
		
		__inject__(MyEntityRepresentation.class).deleteByIdentifiers(Arrays.asList(code1,code2,code3),ValueUsageType.BUSINESS.name());
	}
	
	@Test
	public void find_myEntity_use_projection() throws Exception{
		String code1 = __getRandomCode__(); 
		String code2 = __getRandomCode__();
		String code3 = __getRandomCode__();
		String id1 = __getRandomIdentifier__(); 
		String id2 = __getRandomIdentifier__();
		String id3 = __getRandomIdentifier__();
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(
				new MyEntityDto().setIdentifier(id1).setCode(code1).setName("a").setLong1(15l).setLong2(20l).setIntegerValue(7)
				,new MyEntityDto().setIdentifier(id2).setCode(code2).setName("c")
				,new MyEntityDto().setIdentifier(id3).setCode(code3).setName("b")
				),null);
		__assertReadMyEntity__(id1, null, Boolean.TRUE, "a", 15l, 20l, 7);
		__assertReadMyEntity__(id1, "name", Boolean.TRUE, "a", null, null, null);
		__assertReadMyEntity__(id1, "long1", Boolean.TRUE, null, 15l, null, null);
		__assertReadMyEntity__(id1, "long2", Boolean.TRUE, null, null, 20l, null);
		__assertReadMyEntity__(id1, "integerValue", Boolean.TRUE, null, null, null, 7);
		__assertReadMyEntity__(id1, "name,long1", Boolean.TRUE, "a", 15l, null, null);
		__assertReadMyEntity__(id1, "name,long2", Boolean.TRUE, "a", null, 20l, null);
		__assertReadMyEntity__(id1, "long1,long2", Boolean.TRUE, null, 15l, 20l, null);
	}
	
	/* Count */
	
	@Test
	public void count_myEntity_filter_identifier_system() throws Exception{
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityBusiness.class).createMany(collection);
		
		assertThat( __inject__(MyEntityRepresentation.class).count(new FilterDto().addField("identifier", Arrays.asList("0"), ValueUsageType.SYSTEM))
				.getEntity()).isEqualTo(1l);
		assertThat( __inject__(MyEntityRepresentation.class).count(new FilterDto().addField("identifier", Arrays.asList("0","10","21"), ValueUsageType.SYSTEM))
				.getEntity()).isEqualTo(3l);
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
		__inject__(MyEntityRepresentation.class).deleteAll();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
	}
	
	@Test
	public void delete_myEntity_all_generic() {
		String code1 = __getRandomCode__();
		String code2 = __getRandomCode__();
		assertionHelper.assertEquals(0l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		MyEntityDto myEntity01 = new MyEntityDto().setCode(code1);
		MyEntityDto myEntity02 = new MyEntityDto().setCode(code2);
		__inject__(MyEntityRepresentation.class).createMany(Arrays.asList(myEntity01,myEntity02),null);
		assertionHelper.assertEquals(2l, __inject__(MyEntityRepresentation.class).count(null).getEntity());
		__inject__(Representation.class).deleteAll();
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
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(dtos)).containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 0l, 1l, null, null).getEntity();
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(dtos)).containsExactly("0");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 1l, 1l, null, null).getEntity();
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(dtos)).containsExactly("1");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 0l, 3l, null, null).getEntity();
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(dtos)).containsExactly("0","1","2");
		
		dtos = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, 4l, 3l, null, null).getEntity();
		assertThat(org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifiers(dtos)).containsExactly("4","5","6");
	}
	
	/* Hierarchy */
	
	@Test
	public void create_node() throws Exception{
		NodeDto nodeModule = new NodeDto().setCode("module").setName(__getRandomName__());
		__inject__(NodeRepresentation.class).createOne(nodeModule);
		NodeDto nodeService = new NodeDto().setCode("service").setName(__getRandomName__()).addParents(new NodeDto().setCode("module"));
		__inject__(NodeRepresentation.class).createOne(nodeService);
		NodeDto nodeMenu = new NodeDto().setCode("menu").setName(__getRandomName__()).addParents(new NodeDto().setCode("service"));
		__inject__(NodeRepresentation.class).createOne(nodeMenu);
		NodeDto nodeAction = new NodeDto().setCode("action").setName(__getRandomName__()).addParents(new NodeDto().setCode("menu"));
		__inject__(NodeRepresentation.class).createOne(nodeAction);
		NodeDto node;
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("module","BUSINESS",null).getEntity();
		assertThat(node).as("node with code module not found").isNotNull();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = (NodeDto)  __inject__(NodeRepresentation.class).getOne("module","BUSINESS",Node.FIELD_PARENTS).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = (NodeDto)  __inject__(NodeRepresentation.class).getOne("module","BUSINESS",Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren().getCollection()).isNotEmpty();
		assertThat(node.getChildren().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("service");
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("module","BUSINESS",Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren().getCollection()).isNotEmpty();
		assertThat(node.getChildren().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("service");
		
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("service","BUSINESS",null).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("service","BUSINESS",Node.FIELD_PARENTS).getEntity();
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents().getCollection()).isNotEmpty();
		assertThat(node.getParents().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNull();
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("service","BUSINESS",Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren().getCollection()).isNotEmpty();
		assertThat(node.getChildren().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("menu");
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("service","BUSINESS",Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents().getCollection()).isNotEmpty();
		assertThat(node.getParents().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("module");
		assertThat(node.getChildren()).isNotNull();
		assertThat(node.getChildren().getCollection()).isNotEmpty();
		assertThat(node.getChildren().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("menu");
		
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("action","BUSINESS",null).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("action","BUSINESS",Node.FIELD_PARENTS).getEntity();
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents().getCollection()).isNotEmpty();
		assertThat(node.getParents().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("action","BUSINESS",Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNull();
		assertThat(node.getChildren()).isNull();
		node = (NodeDto) __inject__(NodeRepresentation.class).getOne("action","BUSINESS",Node.FIELD_PARENTS+","+Node.FIELD_CHILDREN).getEntity();
		assertThat(node.getParents()).isNotNull();
		assertThat(node.getParents().getCollection()).isNotEmpty();
		assertThat(node.getParents().getCollection().stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("menu");
		assertThat(node.getChildren()).isNull();
	}
	
	@Test
	public void read_node_filter_byParent_identifier_business() throws Exception{
		NodeDto nodeModule = new NodeDto().setIdentifier("MO").setCode("module").setName(__getRandomName__());
		__inject__(NodeRepresentation.class).createOne(nodeModule);
		NodeDto nodeService = new NodeDto().setIdentifier("S").setCode("service").setName(__getRandomName__()).addParents(new NodeDto().setCode("module"));
		__inject__(NodeRepresentation.class).createOne(nodeService);
		NodeDto nodeMenu = new NodeDto().setIdentifier("ME").setCode("menu").setName(__getRandomName__()).addParents(new NodeDto().setCode("service"));
		__inject__(NodeRepresentation.class).createOne(nodeMenu);
		NodeDto nodeAction = new NodeDto().setIdentifier("A").setCode("action").setName(__getRandomName__()).addParents(new NodeDto().setCode("menu"));
		__inject__(NodeRepresentation.class).createOne(nodeAction);
		
		FilterDto filters = __inject__(FilterDto.class).useKlass(Node.class);
		filters.addField(Node.FIELD_PARENTS, Arrays.asList("module"),ValueUsageType.BUSINESS);
		@SuppressWarnings("unchecked")
		Collection<NodeDto> nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null, filters).getEntity();
		assertThat(nodes).isNotEmpty();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("service");
	}
	
	@Test
	public void read_node_filter_byParent_identifier_system() throws Exception{
		NodeDto nodeModule = new NodeDto().setIdentifier("MO").setCode("module").setName(__getRandomName__());
		__inject__(NodeRepresentation.class).createOne(nodeModule);
		NodeDto nodeService = new NodeDto().setIdentifier("S").setCode("service").setName(__getRandomName__()).addParents(new NodeDto().setIdentifier("MO"));
		__inject__(NodeRepresentation.class).createOne(nodeService);
		NodeDto nodeMenu = new NodeDto().setIdentifier("ME").setCode("menu").setName(__getRandomName__()).addParents(new NodeDto().setIdentifier("S"));
		__inject__(NodeRepresentation.class).createOne(nodeMenu);
		NodeDto nodeAction = new NodeDto().setIdentifier("A").setCode("action").setName(__getRandomName__()).addParents(new NodeDto().setIdentifier("ME"));
		__inject__(NodeRepresentation.class).createOne(nodeAction);
		
		FilterDto filters = __inject__(FilterDto.class).useKlass(Node.class);
		filters.addField(Node.FIELD_PARENTS, Arrays.asList("MO"),ValueUsageType.SYSTEM);
		@SuppressWarnings("unchecked")
		Collection<NodeDto> nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null, filters).getEntity();
		assertThat(nodes).isNotEmpty();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("service");
	}
	
	@SuppressWarnings("unchecked")
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
		Collection<NodeDto> nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null, new FilterDto().addField(Node.FIELD_PARENTS, null)).getEntity();
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("0","1","2","3");
		assertThat(__inject__(NodeRepresentation.class).count(new FilterDto().addField(Node.FIELD_PARENTS, null)).getEntity()).isEqualTo(4l);
		
		nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null,new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("0"),ValueUsageType.BUSINESS)).getEntity();
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("0.0","0.1","0.2");
		assertThat(__inject__(NodeRepresentation.class).count(new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("0"))).getEntity()).isEqualTo(3l);
		
		nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null,new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("0.0"),ValueUsageType.BUSINESS)).getEntity();
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("0.0.0","0.0.1");
		assertThat(__inject__(NodeRepresentation.class).count(new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("0.0"))).getEntity()).isEqualTo(2l);
		
		nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null,new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("1"),ValueUsageType.BUSINESS)).getEntity();
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("1.0","1.1","1.2");
		
		nodes = (Collection<NodeDto>) __inject__(NodeRepresentation.class).getMany(Boolean.FALSE, null, null, null,new FilterDto().addField(Node.FIELD_PARENTS, Arrays.asList("1.1"),ValueUsageType.BUSINESS)).getEntity();
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(NodeDto::getCode).collect(Collectors.toList())).containsOnly("1.1.0","1.1.1");
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
	
	@SuppressWarnings("unchecked")
	private void __assertReadMyEntity__(String identifier,String fields,Boolean expectedIsNotNull,String expectedName,Long expectedLong1,Long expectedLong2,Integer expectedIntegerValue) {
		MyEntityDto myEntity = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(identifier, "system", fields).getEntity();
		if(expectedIsNotNull != null && expectedIsNotNull) {
			assertThat(myEntity).as(String.format("myEntity with identifier %s does not exist",identifier)).isNotNull();
			__assertReadMyEntity__(myEntity, fields, expectedIsNotNull, expectedName, expectedLong1, expectedLong2, expectedIntegerValue);
			
			if(fields != null)
				fields = fields + ",identifier";
			for(MyEntityDto index : (Collection<MyEntityDto>)__inject__(MyEntityRepresentation.class).getMany(Boolean.TRUE, null, null, fields, null).getEntity()) {
				if(index.getIdentifier().equals(identifier)) {
					myEntity = index;
					break;
				}
			}
			__assertReadMyEntity__(myEntity, fields, expectedIsNotNull, expectedName, expectedLong1, expectedLong2, expectedIntegerValue);
		}else
			assertThat(myEntity).as(String.format("myEntity with identifier %s exists",identifier)).isNull();
	}
	
	private void __assertReadMyEntity__(MyEntityDto myEntity,String fields,Boolean expectedIsNotNull,String expectedName,Long expectedLong1,Long expectedLong2,Integer expectedIntegerValue) {
		assertThat(myEntity.getName()).as("name does not match").isEqualTo(expectedName);
		assertThat(myEntity.getLong1()).as("long1 does not match").isEqualTo(expectedLong1);
		assertThat(myEntity.getLong2()).as("long2 not match").isEqualTo(expectedLong2);
		assertThat(myEntity.getIntegerValue()).as("integer value does not match").isEqualTo(expectedIntegerValue);		
	}
	
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}
	

}
