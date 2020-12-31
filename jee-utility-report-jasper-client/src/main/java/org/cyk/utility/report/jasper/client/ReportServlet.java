package org.cyk.utility.report.jasper.client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.client.controller.web.jsf.FileServlet;
import org.cyk.utility.report.ReportRepresentation;

@WebServlet(name = "Report File Jsf Servlet",urlPatterns = {ReportServlet.PATH_PATTERN})
public class ReportServlet extends org.omnifaces.servlet.FileServlet implements Serializable {

	public static Listener LISTENER = new Listener.AbstractImpl.DefaultImpl();
	
	@Override
    protected File getFile(HttpServletRequest request) {
    	try {
			return LISTENER.getFile(request);
		} catch (IOException exception) {
			LogHelper.log(exception, getClass());
			return null;
		}
    }
       
    @Override
    protected long getExpireTime(HttpServletRequest request, File file) {
    	return LISTENER.getExpireTime(request, file);
    }
	
	/*
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	
	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		super.doGet(httpServletRequest, httpServletResponse);
		String identifier = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_IDENTIFIER);
		String parametersNamesValuesAsJson = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_PARAMETERS_NAMES_VALUES_AS_JSON);
		String fileType = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_FILE_TYPE);
		String isInline = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_IS_INLINE);
		Response response = ProxyGetter.getInstance().get(ReportRepresentation.class).get(identifier, parametersNamesValuesAsJson, fileType, isInline);	
		if(response == null) {
			LogHelper.logSevere("No response got", getClass());
			return;
		}		
		if(Response.Status.OK.getStatusCode() != response.getStatus()) {
			LogHelper.logSevere("Not OK : "+response.getStatusInfo(), getClass());
			return;
		}
		byte[] bytes = response.readEntity(byte[].class);
		httpServletResponse.reset();
		httpServletResponse.setBufferSize(DEFAULT_BUFFER_SIZE);
		httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, response.getHeaderString(HttpHeaders.CONTENT_LENGTH));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, response.getHeaderString(HttpHeaders.CONTENT_DISPOSITION));
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(new ByteArrayInputStream(bytes), DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(httpServletResponse.getOutputStream(), DEFAULT_BUFFER_SIZE); byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
         } finally {
        	 Helper.close(output,input);
         }		
	}
	 */
	 /**/
	 
	 public static final String PATH = "/cyk/utility/report/file/";
	 public static final String PATH_PATTERN = PATH+"*";
	 
	 /**/
	 
	 public static interface Listener extends FileServlet.Listener {
		 
		 public static abstract class AbstractImpl extends FileServlet.Listener.AbstractImpl implements Listener,Serializable {
			
			@Override
			protected byte[] getBytes(HttpServletRequest httpServletRequest, String classSimpleName,String instanceIdentifier, String type) {
				String identifier = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_IDENTIFIER);
				String parametersNamesValuesAsJson = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_PARAMETERS_NAMES_VALUES_AS_JSON);
				String fileType = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_FILE_TYPE);
				String isInline = httpServletRequest.getParameter(ReportRepresentation.PARAMETER_IS_INLINE);
				Response response = ProxyGetter.getInstance().get(ReportRepresentation.class).get(identifier, parametersNamesValuesAsJson, fileType, isInline);	
				if(response == null) {
					LogHelper.logSevere("No response got", getClass());
					return null;
				}		
				if(Response.Status.OK.getStatusCode() != response.getStatus()) {
					LogHelper.logSevere("Not OK : "+response.getStatusInfo(), getClass());
					return null;
				}
				if(!response.hasEntity()) {
					LogHelper.logSevere("Not entity got : "+response.getStatusInfo(), getClass());
					return null;
				}
				return response.readEntity(byte[].class);
			}
			
			/**/
				
			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {
				
			}
		 }
	 }
}