package org.cyk.utility.__kernel__.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import javax.json.bind.annotation.JsonbProperty;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.protocol.http.HttpClientGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RestHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private ClientAndServer server;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClass(HttpClientGetter.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		server =  ClientAndServer.startClientAndServer(10000);
		ConfigurationHelper.clear();
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		server.stop();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void getMany(){
		new MockServerClient("localhost", 10000)
	    .when(HttpRequest.request().withMethod("GET").withPath("/person"))
	    .respond(org.mockserver.model.HttpResponse.response().withStatusCode(200).withBody("[{\"firstName\":\"yao\"}]"));
		
		Collection<Person> persons = RestHelper.getMany(Person.class, null, URI.create("http://localhost:10000/person"), Map.of("firstName","firstName"));		
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getFirstName)).contains("yao");
	}

	
	@SuppressWarnings("resource")
	@Test
	public void getMany_deriveUri_deriveFieldsNamesMap(){
		System.setProperty(VariableName.buildClassUniformResourceIdentifier(Person.class),"http://localhost:10000/person");
		System.setProperty(VariableName.buildFieldName(Person.class, "firstName"), "fName");
		
		new MockServerClient("localhost", 10000)
	    .when(HttpRequest.request().withMethod("GET").withPath("/person"))
	    .respond(org.mockserver.model.HttpResponse.response().withStatusCode(200).withBody("[{\"fName\":\"yao\",\"lastNames\":\"jean luc\",\"uuid\":\"1\"}]"));
		
		Collection<Person> persons = RestHelper.getMany(Person.class);		
		assertThat(persons).isNotNull();
		assertThat(persons.stream().map(Person::getFirstName)).contains("yao");
		assertThat(persons.stream().map(Person::getLastNames)).contains("jean luc");
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Person {
		@JsonbProperty
		private String firstName;
		@JsonbProperty
		private String lastNames;
	}
}
