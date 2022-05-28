package org.cyk.utility.report.jasper.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.report.GenericReportService;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.service.server.AbstractServiceImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ApplicationScoped
public class GenericReportServiceImpl extends AbstractServiceImpl implements GenericReportService,Serializable {
	
	@Inject @RestClient private JasperServerClient jasperServer;
	private String sessionIdentifier;
	@Inject private Configuration configuration;
	
	@Override
	public Response get(String identifier, String parametersAsJson,String fileType, Boolean isContentInline) {
		if(StringHelper.isBlank(identifier))
			return Response.status(Status.BAD_REQUEST).entity("Report identifier is required").build();
		Map<String, String> map = new HashMap<String, String>();
		if(StringHelper.isNotBlank(parametersAsJson))
			map.putAll(new Gson().fromJson(parametersAsJson, new TypeToken<Map<String, String>>() {}.getType()));
		JasperServerClient.populateMap(map, identifier, fileType);
		
		Response response = execute(new RequestExecutor.Request.AbstractImpl() {
			@Override
			protected void __execute__() {
				responseBuilderArguments.setResponse(jasperServer.getReport(map));
			}
		}, new SessionIdentificationListener() {
			@Override
			public void set(String identifier) {
				JasperServerClient.setSessionIdentifier(map,identifier);
			}
			
			@Override
			public String get() {
				return sessionIdentifier;
			}
		});
		ResponseBuilder responseBuilder = Response.ok(response.getEntity());	
		responseBuilder.header(HttpHeaders.CONTENT_TYPE, FileHelper.getMimeTypeByExtension(fileType));
		if(isContentInline == null)
			isContentInline = configuration.defaultIsContentInline();
	    responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, (isContentInline == null || isContentInline ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME+"=file_"+System.currentTimeMillis()+"."+fileType);
		return responseBuilder.build();
	}
	
	private Response execute(RequestExecutor.Request request,SessionIdentificationListener sessionIdentificationListener) {
		sessionIdentificationListener.set(getSessionIdentifier());
		try {
			return requestExecutor.execute(request,Boolean.FALSE);
		} catch (WebApplicationException exception) {
			if(exception.getResponse().getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
				LogHelper.logWarning(String.format("Session identifier : %s -> %s",sessionIdentifier,exception.getResponse().getStatusInfo()), getClass());
				sessionIdentifier = null;
				sessionIdentificationListener.set(getSessionIdentifier());
				return requestExecutor.execute(request);
			}else
				throw new RuntimeException(exception);
		}
	}
	
	private String getSessionIdentifier() {
		if(sessionIdentifier != null)
			return sessionIdentifier;
		Integer count = 0;
		do {
			Response response = jasperServer.login(configuration.credentials().user(), configuration.credentials().pass());
			if(response.getStatus() == Response.Status.OK.getStatusCode()) {
				sessionIdentifier = JasperServerClient.getJSESSIONID(response);
				LogHelper.logWarning(String.format("Session identifier has been got %s",sessionIdentifier), getClass());
				return sessionIdentifier;
			}
			count++;
		}while(count <= 3);
		LogHelper.logWarning(String.format("Unable to get session identifier after %s try count",count), getClass());
		return null;
	}
	
	public static String buildReportParameterConfigurationPropertyName(String domain,String reportIdentifier,String parameterName) {
		return String.format("%s.%s,%s",domain, reportIdentifier,parameterName);
	}
}