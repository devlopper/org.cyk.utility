package org.cyk.utility.server.representation;

import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.type.TypeHelperImpl;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

public class RepresentationIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Test 
	public void getTime() {
		Response response = TARGET.proxy(MyRepresentation.class).getTime();
		String time = response.readEntity(String.class);
		System.out.println("TIME IS : "+time);
	}
	
	@Test
	public void getCollectionOfGeneric() {
		Response response = TARGET.proxy(MyRepresentation.class).getCollection();
		//Collection<MyEntityDto> collection = response.readEntity(new GenericType<List<MyEntityDto>>(TypeHelperImpl.__instanciateCollectionParameterizedType__(Collection.class,MyEntityDto.class)));
		Collection<MyEntityDto> collection = (Collection<MyEntityDto>) response.readEntity(TypeHelperImpl.__instanciateGenericCollectionParameterizedTypeForJaxrs__(Collection.class,MyEntityDto.class));
		System.out.println(collection);
	}
	
	@Test
	public void getOneOfGeneric() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(contextPath.toString()));
		Response response = target.proxy(MyRepresentation.class).getOne();
		MyEntityDto one = response.readEntity(MyEntityDto.class);
		System.out.println(one);
	}

	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}

}
