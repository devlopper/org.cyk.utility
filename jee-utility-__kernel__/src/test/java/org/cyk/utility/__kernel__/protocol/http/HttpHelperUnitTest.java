package org.cyk.utility.__kernel__.protocol.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.http.HttpResponse;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;

public class HttpHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private static ClientAndServer SERVER;
	
	@BeforeAll
	public static void listenBeforeAll() {
		SERVER =  ClientAndServer.startClientAndServer(10000);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		HttpHelper.clear();
		SERVER.reset();
	}
	
	@AfterAll
	protected static void listenAfterAll() {
		SERVER.stop();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void get(){
		new MockServerClient("localhost", 10000)
	    .when(HttpRequest.request().withMethod("GET").withPath("/"))
	    .respond(org.mockserver.model.HttpResponse.response().withStatusCode(200).withBody("Good job"));
		
		HttpResponse<String> response = HttpHelper.get("http://localhost:10000");
		assertThat(response.body()).isEqualTo("Good job");
	}
	
	@SuppressWarnings("resource")
	@Test
	public void get_(){
		new MockServerClient("localhost", 10000)
	    .when(HttpRequest.request().withMethod("GET").withPath("/"))
	    .respond(org.mockserver.model.HttpResponse.response().withStatusCode(200).withBody("Hi"));
		
		HttpResponse<String> response = HttpHelper.get("http://localhost:10000");
		assertThat(response.body()).isEqualTo("Hi");
	}
}
