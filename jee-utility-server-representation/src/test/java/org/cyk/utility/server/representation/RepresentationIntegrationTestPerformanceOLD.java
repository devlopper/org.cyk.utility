package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.instance.AbstractInstanceBuilderImpl;
import org.cyk.utility.instance.InstanceBuilder;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.object.ObjectToStringBuilder;
import org.cyk.utility.server.business.api.MyEntityAssertionsProvider;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RepresentationIntegrationTestPerformanceOLD extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, PersistableClassesGetter.class);
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
	}
	
	//@Test
	public void get_field_1(){
		Long timestamp = System.currentTimeMillis();
		__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_1() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void get_field_10(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_10() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void get_field_100(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_100() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void get_field_1000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 1000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_1000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void get_field_10000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_10000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void get_field_100000(){
		Long timestamp = System.currentTimeMillis();
		for(Integer index = 0 ; index < 100000 ; index = index + 1)
			__inject__(FieldsGetter.class).setClazz(Fields.class).addNameToken("f01").execute().getOutput().get();
		System.out.println("RepresentationIntegrationTestPerformance.get_field_100000() : "+(System.currentTimeMillis() - timestamp));
	}
	
	//@Test
	public void buildMany_10_MyEntityDto_to_MyEntity(){
		Long t = System.currentTimeMillis();
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1) {
			dtos.add(new MyEntityDto().setIdentifier(index.toString()).setCode(index.toString()).setName(index.toString()));
		}
		
		
		Collection<MyEntity> entities = __inject__(InstanceHelper.class).buildMany(MyEntity.class, dtos//,new Properties()
				//.setFields(Arrays.asList("identfier","code","name"))
				);
		
		Integer count = 0;
		for(MyEntity index  : entities) {
			assertThat(index.getIdentifier()).isEqualTo(count.toString());
			assertThat(index.getCode()).isEqualTo(count.toString());
			assertThat(index.getName()).isEqualTo(count.toString());	
			count++;
		}
		
		System.out.println("RepresentationIntegrationTestPerformance.buildMany_10_MyEntityDto_to_MyEntity() : "+(System.currentTimeMillis() - t));
	}
	
	//@Test
	public void buildMany_100_MyEntityDto_to_MyEntity_manually(){
		Long t = System.currentTimeMillis();
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1) {
			dtos.add(new MyEntityDto().setIdentifier(index.toString()).setCode(index.toString()).setName(index.toString()));
		}
		Collection<MyEntity> entities = new ArrayList<MyEntity>();
		for(MyEntityDto index : dtos) {
			MyEntity entity = new MyEntity();
			entity.setIdentifier(index.getIdentifier());
			entity.setCode(index.getCode());
			entity.setName(index.getName());
			entities.add(entity);
		}
	
		Integer count = 0;
		for(MyEntity index  : entities) {
			assertThat(index.getIdentifier()).isEqualTo(count.toString());
			assertThat(index.getCode()).isEqualTo(count.toString());
			assertThat(index.getName()).isEqualTo(count.toString());	
			count++;
		}
		System.out.println("RepresentationIntegrationTestPerformance.buildMany_100_MyEntityDto_to_MyEntity_manually() : "+(System.currentTimeMillis() - t));
	}
	
	//@Test
	public void buildMany_10000_MyEntityDto_to_MyEntity_mapper(){
		Long t = System.currentTimeMillis();
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(Integer index = 0 ; index < 10000 ; index = index + 1)
			dtos.add(new MyEntityDto().setIdentifier(index.toString()).setCode(index.toString()).setName(index.toString()));
		
		Collection<MyEntity> entities = __inject__(MappingHelper.class).getDestinations(dtos, MyEntity.class);
		
		Integer count = 0;
		for(MyEntity index  : entities) {
			assertThat(index.getIdentifier()).isEqualTo(count.toString());
			assertThat(index.getCode()).isEqualTo(count.toString());
			assertThat(index.getName()).isEqualTo(count.toString());	
			count++;
		}
		
		System.out.println("RepresentationIntegrationTestPerformance.buildMany_10000_MyEntityDto_to_MyEntity_mapStruct() : "+(System.currentTimeMillis() - t));
	}
	
	//@Test
	public void buildMany_100000_MyEntity_to_MyEntityDto_mapper(){
		Long t = System.currentTimeMillis();
		Collection<MyEntity> entities = new ArrayList<>();
		for(Integer index = 0 ; index < 100000 ; index = index + 1)
			entities.add(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()).setName(index.toString()));
		
		Collection<MyEntityDto> dtos = __inject__(MappingHelper.class).getSources(entities, MyEntityDto.class);
		
		Integer count = 0;
		for(MyEntityDto index  : dtos) {
			assertThat(index.getIdentifier()).isEqualTo(count.toString());
			assertThat(index.getCode()).isEqualTo(count.toString());
			assertThat(index.getName()).isEqualTo(count.toString());	
			count++;
		}
		
		System.out.println("RepresentationIntegrationTestPerformance.buildMany_100000_MyEntity_to_MyEntityDto_mapStruct() : "+(System.currentTimeMillis() - t));
	}
	
	@Test
	public void create_1_function() throws Exception{
		RepresentationFunctionCreator creator = __inject__(RepresentationFunctionCreator.class);
		creator.setEntity(new MyEntityDto().setCode(__getRandomCode__()));
		creator.execute();
		
		RepresentationFunctionReader reader = __inject__(RepresentationFunctionReader.class);
		reader.setEntityClass(MyEntityDto.class);
		reader.execute();
		@SuppressWarnings("unchecked")
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) reader.getResponse().getEntity();
		
		RepresentationFunctionRemover remover = __inject__(RepresentationFunctionRemover.class);
		remover.setEntityClass(MyEntityDto.class);
		remover.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()));
		remover.execute();
		
	}
	
	@Test
	public void create_10_function() throws Exception{
		Collection<MyEntityDto> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntityDto().setCode(__getRandomCode__()));
		RepresentationFunctionCreator function = __inject__(RepresentationFunctionCreator.class);
		function.setEntities(collection);
		function.execute();
		
		RepresentationFunctionReader reader = __inject__(RepresentationFunctionReader.class);
		reader.setEntityClass(MyEntityDto.class);
		reader.getProperties().setIsQueryResultPaginated(Boolean.FALSE);
		reader.execute();
		@SuppressWarnings("unchecked")
		Collection<MyEntityDto> dtos = (Collection<MyEntityDto>) reader.getResponse().getEntity();
		
		RepresentationFunctionRemover remover = __inject__(RepresentationFunctionRemover.class);
		remover.setEntityClass(MyEntityDto.class);
		remover.getAction().getEntitiesIdentifiers(Boolean.TRUE).add(dtos.stream().map(MyEntityDto::getIdentifier).collect(Collectors.toList()));
		remover.execute();
	}
	
	@Test
	public void create_100_function() throws Exception{
		Collection<MyEntityDto> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 100 ; index = index + 1)
			collection.add(new MyEntityDto().setCode(__getRandomCode__()));
		RepresentationFunctionCreator function = __inject__(RepresentationFunctionCreator.class);
		function.setEntities(collection);
		function.execute();
		
		RepresentationFunctionRemover remover = __inject__(RepresentationFunctionRemover.class);
		remover.setEntityClass(MyEntityDto.class);
		remover.execute();
	}
	
	//@Test
	public void create() throws Exception{
		Collection<MyEntityDto> collection = new ArrayList<>();
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			collection.add(new MyEntityDto().setCode(__getRandomCode__()));
		Map<String,Object> map = new HashMap<String, Object>();
		//map.put(Properties.IS_BATCHABLE, Boolean.TRUE);
		//map.put(Properties.BATCH_SIZE, 5);
		__inject__(MyEntityRepresentation.class).createMany(collection,__injectByQualifiersClasses__(ObjectToStringBuilder.class,JavaScriptObjectNotation.Class.class)
				.setObject(map).execute().getOutput());
		collection = (Collection<MyEntityDto>) __inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE, null, null, null, null).getEntity();
		__inject__(MyEntityRepresentation.class).deleteAll();
	}
	
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}
	
	/**/
	
	@org.cyk.utility.__kernel__.annotation.Test
	public static class InstanceBuilderImpl extends AbstractInstanceBuilderImpl implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __copy__(Object source, Object destination, Properties properties) {
			if(source instanceof MyEntityDto && destination instanceof MyEntity) {
				MyEntityDto representation = (MyEntityDto) source;
				MyEntity persistence = (MyEntity) destination;
				persistence.setIdentifier(representation.getIdentifier());
				persistence.setCode(representation.getCode());
				persistence.setName(representation.getName());
			}
			super.__copy__(source, destination, properties);
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Fields {
		public String f01;
	}

}
