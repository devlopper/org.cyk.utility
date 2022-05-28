package org.cyk.utility.report.jasper.server;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = JasperServerClient.CONFIG_KEY)
public interface JasperServerClient {
	String CONFIG_KEY = "jasper";
	String PATH_REST= "/rest_v2/";

	String QUERY_PARAMETER_USER_NAME = "j_username";
	String QUERY_PARAMETER_USER_PASS = "j_password";
	String QUERY_PARAMETER_REPORT_IDENTIFIER = "identifier";
	String QUERY_PARAMETER_FILE_TYPE = "file-type";
	String QUERY_PARAMETER_SESSION_IDENTIFIER = "session-identifier";
	
	String HEARDER_COOKIE = "Cookie";

	@GET
	@Path("j_spring_security_check")
	Response checkSecurity(@QueryParam(JasperServerClient.QUERY_PARAMETER_USER_NAME) String username, @QueryParam(JasperServerClient.QUERY_PARAMETER_USER_PASS) String password);
	
	@GET
	@Path(PATH_REST+"login")
	Response login(@QueryParam(QUERY_PARAMETER_USER_NAME) String username, @QueryParam(QUERY_PARAMETER_USER_PASS) String password);

	@GET
	@Path("logout.htm")
	Response logout(@HeaderParam(HEARDER_COOKIE) String value);

	String GET_REPORT_PATH = PATH_REST+"reports";
	@POST
	@Path(GET_REPORT_PATH)
	Response getReport(Map<String, String> map);

	static Boolean isGetReportRequest(ClientRequestContext requestContext) {
		return requestContext.getMethod().equals(HttpMethod.POST) && requestContext.getUri().getPath().endsWith(GET_REPORT_PATH) && requestContext.getEntity() instanceof Map;
	}

	static void prepareGetReportRequest(ClientRequestContext requestContext,Configuration configuration) {
		UriBuilder uriBuilder = UriBuilder.fromUri(requestContext.getUri());
		requestContext.setMethod(HttpMethod.GET);

		Map<String, String> map = (Map<String, String>) requestContext.getEntity();

		if (map.containsKey(QUERY_PARAMETER_REPORT_IDENTIFIER))
			uriBuilder.path(map.remove(QUERY_PARAMETER_REPORT_IDENTIFIER)+"."+ValueHelper.defaultToIfBlank(map.remove(QUERY_PARAMETER_FILE_TYPE),configuration.defaultFileType()));

		if (map.containsKey(QUERY_PARAMETER_SESSION_IDENTIFIER))
			requestContext.getHeaders().put(HEARDER_COOKIE,List.of(String.format("$Version=0; JSESSIONID=%s; $Path=/jasperserver",map.remove(QUERY_PARAMETER_SESSION_IDENTIFIER))));

		map.entrySet().forEach(entry -> {
			uriBuilder.queryParam(entry.getKey(), entry.getValue());
		});
		
		requestContext.setUri(uriBuilder.build());
		requestContext.setEntity(null);
	}

	/**/

	static String getJSESSIONID(String string) {
		return StringUtils.substringBetween(string, "JSESSIONID=", ";");
	}
	
	static String getJSESSIONID(Response response) {
		return getJSESSIONID(response.getHeaderString("Set-Cookie"));
	}

	static void populateMap(Map<String,String> map,String identifier, String fileType) {
		map.put(QUERY_PARAMETER_REPORT_IDENTIFIER, identifier);
		map.put(QUERY_PARAMETER_FILE_TYPE, fileType);
	}
	
	static void setSessionIdentifier(Map<String,String> map,String sessionIdentifier) {
		map.put(QUERY_PARAMETER_SESSION_IDENTIFIER, sessionIdentifier);
	}
}