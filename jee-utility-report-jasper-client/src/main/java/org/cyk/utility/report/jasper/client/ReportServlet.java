package org.cyk.utility.report.jasper.client;

import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.report.GenericReportService;

@WebServlet(name = "Report Servlet",urlPatterns = {ReportServlet.PATH_PATTERN})
public class ReportServlet extends AbstractReportServlet implements Serializable {

	public static Listener LISTENER = new Listener.AbstractImpl.DefaultImpl();
	
	@Override
	protected org.cyk.utility.report.jasper.client.AbstractReportServlet.Listener getListener() {
		return LISTENER;
	}
	
	
	 /**/
	 
	 public static final String PATH = "/cyk/report/";
	 public static final String PATH_PATTERN = PATH+"*";
	 
	 public static String formatPath(String identifier,String parametersAsJson,String fileType,Boolean isContentInline) {
		 HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
		 StringBuilder stringBuilder = new StringBuilder();
		 stringBuilder.append(String.format("%s%s?%s=%s",request.getContextPath(),PATH,GenericReportService.PARAMETER_IDENTIFIER, identifier));
		 if(StringHelper.isNotBlank(parametersAsJson))
			 stringBuilder.append("&"+GenericReportService.PARAMETER_PARAMETERS_AS_JSON+"="+parametersAsJson);
		 if(StringHelper.isNotBlank(fileType))
			 stringBuilder.append("&"+GenericReportService.PARAMETER_FILE_TYPE+"="+fileType);
		 if(isContentInline != null)
			 stringBuilder.append("&"+GenericReportService.PARAMETER_IS_CONTENT_INLINE+"="+isContentInline.toString());
		 return stringBuilder.toString();
	 }
	 
	 public static String formatPath(String identifier,String parametersAsJson) {
		 return formatPath(identifier, parametersAsJson, null, null);
	 }
	 
	 /**/
	 
	 public static interface Listener extends AbstractReportServlet.Listener {
		 
		 public static abstract class AbstractImpl extends AbstractReportServlet.Listener.AbstractImpl implements Listener,Serializable {
			
			/**/
				
			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {
				
			}
		 }
	 }
}