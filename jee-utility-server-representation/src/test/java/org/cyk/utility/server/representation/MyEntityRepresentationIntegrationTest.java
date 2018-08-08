package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class MyEntityRepresentationIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntity> {
	private static final long serialVersionUID = 1L;
	
	/* Global variable */
	protected static ResteasyClient CLIENT;
	protected static ResteasyWebTarget TARGET;
	
	private static MyEntityRepresentation MY_ENTITY_REPRESENTATION;
	
	@Drone protected WebDriver browser;
	@ArquillianResource protected URL contextPath;
	
	@Override
	protected void __listenBeforeFirstCall__() {
		super.__listenBeforeFirstCall__();
		CLIENT = new ResteasyClientBuilder().build();
		TARGET = CLIENT.target(UriBuilder.fromPath(contextPath.toExternalForm()));
		MY_ENTITY_REPRESENTATION = TARGET.proxy(MyEntityRepresentation.class);
		
	}
	
	@Test
	public void createOne(){
		Response response = MY_ENTITY_REPRESENTATION.createOne(new MyEntityDto().setCode("C01"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
	}
	
	@Test
	public void getMany(){
		MY_ENTITY_REPRESENTATION.createOne(new MyEntityDto().setCode("CA01"));
		MY_ENTITY_REPRESENTATION.createOne(new MyEntityDto().setCode("CA02"));
		Collection<MyEntityDto> dtos = MY_ENTITY_REPRESENTATION.getMany();
		assertThat(dtos).isNotEmpty();
		
	}
	
	/*@Override
	protected MyEntity __instanciateEntity__(Object action) throws Exception {
		return super.__instanciateEntity__(action).setLong1(2l);
	}
	
	@Test
	public void createWithLong1Null(){
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(1l);
		__createEntity__(myEntity);
	}*/
}
