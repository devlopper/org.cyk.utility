package org.cyk.utility.__kernel__.protocol.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.http.HttpResponse;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;

public class HttpHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private ClientAndServer server;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		server =  ClientAndServer.startClientAndServer(10000);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		server.stop();
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
	
}
