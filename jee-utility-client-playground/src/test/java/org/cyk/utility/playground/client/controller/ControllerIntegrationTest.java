package org.cyk.utility.playground.client.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.ControllerFunctionReaderImpl;
import org.cyk.utility.client.controller.test.arquillian.AbstractControllerArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.playground.client.controller.api.MyEntityController;
import org.cyk.utility.playground.client.controller.entities.MyEntity;
import org.cyk.utility.playground.client.controller.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.server.representation.RepresentationFunctionReaderImpl;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class ControllerIntegrationTest extends AbstractControllerArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void create_myEntity() throws Exception{
		String identifier = __getRandomIdentifier__();
		String code = __getRandomCode__();
		MyEntity myEntity = __inject__(MyEntity.class).setIdentifier(identifier).setCode(code).setName(__getRandomName__());
		Properties properties = new Properties();
		__inject__(MyEntityController.class).create(myEntity,properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
	}
	
	@Test
	public void create_myEntity_many() throws Exception{
		String identifier = __getRandomIdentifier__();
		String code = __getRandomCode__();
		MyEntity myEntity = __inject__(MyEntity.class).setIdentifier(identifier).setCode(code).setName(__getRandomName__());
		Properties properties = new Properties();
		__inject__(MyEntityController.class).createMany(Arrays.asList(myEntity),properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
	}
	
	@Test
	public void read_myEntity() throws Exception{
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(__inject__(MyEntity.class).setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityController.class).createMany(collection);
		
		ControllerFunctionReaderImpl.COUNT = 5l;
		Properties properties = new Properties();
		collection = __inject__(MyEntityController.class).read(properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(ControllerFunctionReaderImpl.COUNT.intValue());
		assertThat(__inject__(ResponseHelper.class).getHeaderXTotalCount(response)).isEqualTo(numberOfEntities.longValue());
		
		ControllerFunctionReaderImpl.COUNT = 30l;
		properties = new Properties();
		collection = __inject__(MyEntityController.class).read(properties);
		response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(ControllerFunctionReaderImpl.COUNT.intValue());
		assertThat(__inject__(ResponseHelper.class).getHeaderXTotalCount(response)).isEqualTo(numberOfEntities.longValue());
	}
	
	@Test
	public void count_myEntity() throws Exception{
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(0l);
		Properties properties = new Properties();
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("0").setCode("0").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(1l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("1").setCode("1").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(2l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("2").setCode("2").setName(__getRandomName__())
				,__inject__(MyEntity.class).setIdentifier("3").setCode("3").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(4l);
	}
	
	@Test
	public void read_myEntity_filter_identifier_system() throws Exception{
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(__inject__(MyEntity.class).setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityController.class).createMany(collection);
		
		Properties properties = new Properties().setFilters(new FilterDto().addField("identifier", Arrays.asList("0"), ValueUsageType.SYSTEM));
		collection = __inject__(MyEntityController.class).read(properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(1);
		assertThat(collection.stream().map(MyEntity::getIdentifier).collect(Collectors.toList())).containsOnly("0");
		assertThat(__inject__(ResponseHelper.class).getHeaderXTotalCount(response)).isEqualTo(1l);
	}
	
	//@Test
	public void count_myEntity_filter() throws Exception{
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(0l);
		Properties properties = new Properties();
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("abc").setCode("012").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(1l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("bcd").setCode("123").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(2l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				__inject__(MyEntity.class).setIdentifier("cde").setCode("345").setName(__getRandomName__())
				,__inject__(MyEntity.class).setIdentifier("abcdef").setCode("0123456").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(4l);
		
		FilterDto filter = new FilterDto().setKlass(org.cyk.utility.playground.server.persistence.entities.MyEntity.class)
				.addField(org.cyk.utility.playground.server.persistence.entities.MyEntity.FIELD_IDENTIFIER, Arrays.asList("abc"));
		
	}
	
	/**/

}
