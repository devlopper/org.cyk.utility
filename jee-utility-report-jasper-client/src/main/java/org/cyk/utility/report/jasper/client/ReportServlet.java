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
				byte[] bytes = response.readEntity(byte[].class);
				FileServlet.log(httpServletRequest, getClass(), String.format("Number of bytes is %s", bytes == null ? null : bytes.length));
				return bytes;
			}
			
			/**/
				
			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {
				
			}
		 }
	 }
}