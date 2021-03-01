package org.cyk.utility.__kernel__.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbProperty;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.JsonHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.protocol.http.HttpClientGetter;
import org.cyk.utility.__kernel__.protocol.http.HttpHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class ResourceGetterUnitTest extends AbstractWeldUnitTest {
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
	
	@Test
	public void getFilters() throws IOException{
		/*HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder(URI.create("http://10.3.4.17:32701/sib/filters/api/v1/filters"))
				.header("Content-Type", "application/json").GET().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());
		response.thenAccept(res -> System.out.println(res));
		try {
			if (response.get().statusCode() == 500)
				System.out.println("Error");
			else {
				System.out.println(response.get().body());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.join();
		*/
		
		String responseBody =  IOUtils.toString(getClass().getResourceAsStream("filters.txt"),"UTF-8");
		/*System.out.println(responseBody);
		*/
		List<Filter> filters = ResourceGetter.getInstance().get(new ResourceGetter.Arguments<Filter>().setKlass(Filter.class)
				.setUri("http://10.3.4.17:32701/sib/filters/api/v1/filters/custom_all")
				.setType(new ArrayList<Filter>(){private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass()));
		filters.forEach(filter -> {
			System.out.println("Filtre "+filter.getCode()+" : "+filter.getLibelle()+" : "+filter.getQuery());
		});
		//System.out.println(filters);
		/*
		List<Filter> maps = JsonbBuilder.create().fromJson(responseBody, new ArrayList<Filter>(){
			private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass());
		System.out.println();
		
		System.out.println(responseBody);
		List<Filter> l = JsonbBuilder.create().fromJson(responseBody, new ArrayList<Filter>(){
			private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass());
		
		System.out.println(l);
		*/
		//ResourceGetter.Arguments<Filter> arguments = new ResourceGetter.Arguments<Filter>().setKlass(Filter.class).setUri("http://10.3.4.17:32701/sib/filters/api/v1/filters");
		//System.out.println(ResourceGetter.getInstance().get(arguments));
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
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Filter {
		private String uuid;
		private String code;
		private String libelle;
		private String query;
	}
}
