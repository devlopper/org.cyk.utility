package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.api.NodeHierarchyRepresentation;
import org.cyk.utility.server.representation.api.NodeRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.entities.MyEntityDtoCollection;
import org.cyk.utility.server.representation.entities.NodeDto;
import org.cyk.utility.server.representation.entities.NodeDtoCollection;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RepresentationIntegrationRemoteTest  {
	
	@ArquillianResource private URL url;
	
	@Test
	public void myEntity_create_one() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __getProxy__(MyEntityRepresentation.class,url);
		myEntityRepresentation.deleteAll();
		Response response = myEntityRepresentation.createOne(new MyEntityDto().setCode("c01").setName("n01"));
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
		assertThat(ResponseHelper.getHeaderAndDisjoin(response, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_BUSINESS)).containsExactlyInAnyOrder("c01");
	}
	
	@Test
	public void myEntity_create_many_collection_java() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __getProxy__(MyEntityRepresentation.class,url);
		myEntityRepresentation.deleteAll();
		Response response = myEntityRepresentation.createMany(List.of(new MyEntityDto().setIdentifier("1a").setCode("c01a").setName("n01")
				,new MyEntityDto().setIdentifier("2a").setCode("c02a").setName("n02")),null);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	@Test
	public void myEntity_create_many_collection_custom() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __getProxy__(MyEntityRepresentation.class,url);
		myEntityRepresentation.deleteAll();
		MyEntityDtoCollection myEntityDtoCollection = new MyEntityDtoCollection();
		//myEntityDtoCollection.add(new MyEntityDto().setCode("1").setCode("c01").setName("n01"));
		//myEntityDtoCollection.add(new MyEntityDto().setCode("2").setCode("c02").setName("n02"));
		myEntityDtoCollection.add("1b", "c01b", "nc01").add("2b", "c02b", "n02");
		Response response = myEntityRepresentation.createMany(myEntityDtoCollection,null);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	@Test
	public void node_create_one() throws Exception{
		__getProxy__(NodeHierarchyRepresentation.class,url).deleteAll();
		NodeRepresentation nodeRepresentation = __getProxy__(NodeRepresentation.class,url);
		nodeRepresentation.deleteAll();
		NodeDto nodeDto = new NodeDto();
		nodeDto.setIdentifier("1000111").setCode("c00011").setName("n");
		Response response = nodeRepresentation.createOne(nodeDto);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	@Test
	public void node_create_one_with_parents() throws Exception{
		__getProxy__(NodeHierarchyRepresentation.class,url).deleteAll();
		NodeRepresentation nodeRepresentation = __getProxy__(NodeRepresentation.class,url);
		nodeRepresentation.deleteAll();
		NodeDto nodeDto = new NodeDto();
		nodeDto.setIdentifier("p1").setCode("c").setName("n");
		Response response = nodeRepresentation.createOne(nodeDto);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
		nodeDto = new NodeDto();
		nodeDto.setIdentifier("1c").setCode("c2").setName("n2");
		nodeDto.addParents(new NodeDto().setIdentifier("p1"));
		response = nodeRepresentation.createOne(nodeDto);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	@Test
	public void node_create_many_collection_custom() throws Exception{
		__getProxy__(NodeHierarchyRepresentation.class,url).deleteAll();
		NodeRepresentation nodeRepresentation = __getProxy__(NodeRepresentation.class,url);
		nodeRepresentation.deleteAll();
		NodeDtoCollection nodeDtoCollection = new NodeDtoCollection();
		//myEntityDtoCollection.add(new MyEntityDto().setCode("1").setCode("c01").setName("n01"));
		//myEntityDtoCollection.add(new MyEntityDto().setCode("2").setCode("c02").setName("n02"));
		nodeDtoCollection.add("1az", "c01az", "nc01").add("2az", "c02az", "n02");
		Response response = nodeRepresentation.createMany(nodeDtoCollection,null);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	private static <T> T __getProxy__(Class<T> klass,URL url) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget;
		try {
			webTarget = client.target(url.toURI());
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
        ResteasyWebTarget resteasyWebTarget = (ResteasyWebTarget)webTarget;
        return resteasyWebTarget.proxy(klass);
	}
	
	@org.jboss.arquillian.container.test.api.Deployment(testable = false)
	public static WebArchive createArchive(){
		return new WebArchiveBuilder().execute(); 
	}

}
