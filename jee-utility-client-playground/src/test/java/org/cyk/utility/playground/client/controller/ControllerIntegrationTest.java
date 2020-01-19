package org.cyk.utility.playground.client.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.ControllerFunctionReaderImpl;
import org.cyk.utility.client.controller.test.arquillian.AbstractControllerArquillianIntegrationTestWithDefaultDeployment;
import org.cyk.utility.playground.client.controller.api.MyEntityController;
import org.cyk.utility.playground.client.controller.api.NodeController;
import org.cyk.utility.playground.client.controller.api.NodeHierarchyController;
import org.cyk.utility.playground.client.controller.entities.MyEntity;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.playground.client.controller.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.server.representation.ResponseHelper;
import org.junit.Test;

public class ControllerIntegrationTest extends AbstractControllerArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void create_myEntity() {
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setCode(code).setName(__getRandomName__());
		Properties properties = new Properties();
		__inject__(MyEntityController.class).create(myEntity,properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		assertThat(response.getHeaderString("entity-identifier-business")).isEqualTo(code);
		assertThat(response.getHeaderString("entity-identifier-system")).isNotBlank();
	}
	
	@Test
	public void create_myEntity_many() {
		String identifier = __getRandomIdentifier__();
		String code = __getRandomCode__();
		MyEntity myEntity = new MyEntity().setIdentifier(identifier).setCode(code).setName(__getRandomName__());
		Properties properties = new Properties();
		__inject__(MyEntityController.class).createMany(Arrays.asList(myEntity),properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
	}
	
	@Test
	public void read_myEntity() {
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityController.class).createMany(collection);
		
		ControllerFunctionReaderImpl.COUNT = 5l;
		Properties properties = new Properties();
		collection = __inject__(MyEntityController.class).read(properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(ControllerFunctionReaderImpl.COUNT.intValue());
		assertThat(ResponseHelper.getHeaderXTotalCount(response)).isEqualTo(numberOfEntities.longValue());
		
		ControllerFunctionReaderImpl.COUNT = 30l;
		properties = new Properties();
		collection = __inject__(MyEntityController.class).read(properties);
		response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(ControllerFunctionReaderImpl.COUNT.intValue());
		assertThat(ResponseHelper.getHeaderXTotalCount(response)).isEqualTo(numberOfEntities.longValue());
	}
	
	@Test
	public void count_myEntity() {
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(0l);
		Properties properties = new Properties();
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				new MyEntity().setIdentifier("0").setCode("0").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(1l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				new MyEntity().setIdentifier("1").setCode("1").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(2l);
		__inject__(MyEntityController.class).createMany(Arrays.asList(
				new MyEntity().setIdentifier("2").setCode("2").setName(__getRandomName__())
				,new MyEntity().setIdentifier("3").setCode("3").setName(__getRandomName__())
				),properties);
		assertThat(__inject__(MyEntityController.class).count()).isEqualTo(4l);
	}
	
	@Test
	public void read_myEntity_filter_identifier_system() {
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityController.class).createMany(collection);
		
		Properties properties = new Properties().setFilters(new FilterDto().addField("identifier", Arrays.asList("0"), ValueUsageType.SYSTEM));
		collection = __inject__(MyEntityController.class).read(properties);
		Response response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(1);
		assertThat(collection.stream().map(MyEntity::getIdentifier).collect(Collectors.toList())).containsOnly("0");
		assertThat(ResponseHelper.getHeaderXTotalCount(response)).isEqualTo(1l);
		
		properties = new Properties().setFilters(new FilterDto().addField("identifier", Arrays.asList("0","10","21"), ValueUsageType.SYSTEM));
		collection = __inject__(MyEntityController.class).read(properties);
		response = (Response) properties.getResponse();
		assertThat(response).isNotNull();
		assertThat(collection).isNotNull();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(collection).hasSize(3);
		assertThat(collection.stream().map(MyEntity::getIdentifier).collect(Collectors.toList())).containsOnly("0","10","21");
		assertThat(ResponseHelper.getHeaderXTotalCount(response)).isEqualTo(3l);
	}
	
	@Test
	public void count_myEntity_filter_identifier_system() {
		Integer numberOfEntities = 50;
		Collection<MyEntity> collection = new ArrayList<>();
		for(Integer index = 0 ; index < numberOfEntities ; index = index + 1)
			collection.add(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()).setName(__getRandomName__()));
		__inject__(MyEntityController.class).createMany(collection);
		
		Properties properties = new Properties().setFilters(new FilterDto().addField("identifier", Arrays.asList("0"), ValueUsageType.SYSTEM));
		assertThat( __inject__(MyEntityController.class).count(properties)).isEqualTo(1l);
		
		properties = new Properties().setFilters(new FilterDto().addField("identifier", Arrays.asList("0","10","21"), ValueUsageType.SYSTEM));
		assertThat( __inject__(MyEntityController.class).count(properties)).isEqualTo(3l);
	}
	
	/* Node */
	
	@Test
	public void create_nodes() {
		assertThat( __inject__(NodeController.class).count().intValue()).isEqualTo(0);
		assertThat( __inject__(NodeHierarchyController.class).count().intValue()).isEqualTo(0);
		Integer numberOfNodesLevel0 = 4;
		Integer numberOfNodesLevel1 = 3;
		Integer numberOfNodesLevel2 = 2;
		for(Integer indexNumberOfNodesLevel0 = 0 ; indexNumberOfNodesLevel0 < numberOfNodesLevel0 ; indexNumberOfNodesLevel0 = indexNumberOfNodesLevel0 + 1) {
			Node nodeLevel0 = __inject__(Node.class).setCode(indexNumberOfNodesLevel0.toString()).setName(__getRandomName__());
			__inject__(NodeController.class).create(nodeLevel0);
			for(Integer indexNumberOfNodesLevel1 = 0 ; indexNumberOfNodesLevel1 < numberOfNodesLevel1 ; indexNumberOfNodesLevel1 = indexNumberOfNodesLevel1 + 1) {
				Node nodeLevel1 = __inject__(Node.class).setCode(nodeLevel0.getCode()+"."+indexNumberOfNodesLevel1.toString()).setName(__getRandomName__()).addParents(nodeLevel0);
				__inject__(NodeController.class).create(nodeLevel1);
				for(Integer indexNumberOfNodesLevel2 = 0 ; indexNumberOfNodesLevel2 < numberOfNodesLevel2 ; indexNumberOfNodesLevel2 = indexNumberOfNodesLevel2 + 1) {
					Node nodeLevel2 = __inject__(Node.class).setCode(nodeLevel1.getCode()+"."+indexNumberOfNodesLevel2.toString()).setName(__getRandomName__()).addParents(nodeLevel1);
					__inject__(NodeController.class).create(nodeLevel2);
				}	
			}	
		}
		Integer count = numberOfNodesLevel0 + (numberOfNodesLevel0 * numberOfNodesLevel1) + (numberOfNodesLevel0 * numberOfNodesLevel1 * numberOfNodesLevel2);
		assertThat( __inject__(NodeController.class).count().intValue()).isEqualTo(count);
		assertThat( __inject__(NodeHierarchyController.class).count().intValue()).isEqualTo((numberOfNodesLevel0 * numberOfNodesLevel1) + (numberOfNodesLevel0 * numberOfNodesLevel1 * numberOfNodesLevel2));
	}
	
	@Test
	public void read_nodes() {
		/*
		Node nodeLevel0 = __inject__(Node.class).setCode("c0").setName("n0");
		nodeLevel0.addParents( __inject__(Node.class).setCode("c1").setName("n1"));
		__inject__(NodeController.class).create(nodeLevel0);
		*/
		
		Integer numberOfNodesLevel0 = 4;
		Integer numberOfNodesLevel1 = 3;
		Integer numberOfNodesLevel2 = 2;
		for(Integer indexNumberOfNodesLevel0 = 0 ; indexNumberOfNodesLevel0 < numberOfNodesLevel0 ; indexNumberOfNodesLevel0 = indexNumberOfNodesLevel0 + 1) {
			Node nodeLevel0 = __inject__(Node.class).setCode(indexNumberOfNodesLevel0.toString()).setName(__getRandomName__());
			try {
				__inject__(NodeController.class).create(nodeLevel0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Integer indexNumberOfNodesLevel1 = 0 ; indexNumberOfNodesLevel1 < numberOfNodesLevel1 ; indexNumberOfNodesLevel1 = indexNumberOfNodesLevel1 + 1) {
				Node nodeLevel1 = __inject__(Node.class).setCode(nodeLevel0.getCode()+"."+indexNumberOfNodesLevel1.toString()).setName(__getRandomName__())
						.addParents(nodeLevel0);
				__inject__(NodeController.class).create(nodeLevel1);
				for(Integer indexNumberOfNodesLevel2 = 0 ; indexNumberOfNodesLevel2 < numberOfNodesLevel2 ; indexNumberOfNodesLevel2 = indexNumberOfNodesLevel2 + 1) {
					Node nodeLevel2 = __inject__(Node.class).setCode(nodeLevel1.getCode()+"."+indexNumberOfNodesLevel2.toString()).setName(__getRandomName__()).addParents(nodeLevel1);
					__inject__(NodeController.class).create(nodeLevel2);
				}	
			}	
		}
		
		assertThat(__inject__(NodeController.class).count()).isEqualTo(40);
		assertThat(__inject__(NodeHierarchyController.class).count()).isEqualTo(36);
		
		Collection<Node> nodes;
		try {
			nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, null)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0","1","2","3");
		assertThat(__inject__(NodeController.class).count(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, null)))).isEqualTo(4l);
		
		nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("0"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0.0","0.1","0.2");
		assertThat(__inject__(NodeController.class).count(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("0"))))).isEqualTo(3l);
		
		nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("0.0"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("0.0.0","0.0.1");
		assertThat(__inject__(NodeController.class).count(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("0.0"))))).isEqualTo(2l);
		
		nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("1"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("1.0","1.1","1.2");
		
		nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, Arrays.asList("1.1"),ValueUsageType.BUSINESS)));
		assertThat(nodes).isNotNull();
		assertThat(nodes.stream().map(Node::getCode).collect(Collectors.toList())).containsOnly("1.1.0","1.1.1");
		
	}
	
	/**/

}
