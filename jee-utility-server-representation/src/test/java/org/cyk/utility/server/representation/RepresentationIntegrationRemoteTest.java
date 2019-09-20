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
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.entities.MyEntityDtoCollection;
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
		Response response = myEntityRepresentation.createMany(List.of(new MyEntityDto().setIdentifier("1").setCode("c01").setName("n01")
				,new MyEntityDto().setIdentifier("2").setCode("c02").setName("n02")),null);
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
	}
	
	@Test
	public void myEntity_create_many_collection_custom() throws Exception{
		MyEntityRepresentation myEntityRepresentation = __getProxy__(MyEntityRepresentation.class,url);
		myEntityRepresentation.deleteAll();
		Response response = myEntityRepresentation.createMany(new MyEntityDtoCollection().add("1", "c01", "nc01").add("2", "c02", "n02"),null);
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
