package org.cyk.utility.report.jasper.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.report.ReportRepresentation;

@WebServlet(name = "Report File Servlet",urlPatterns = {ReportServlet.PATH_PATTERN})
public class ReportServlet extends HttpServlet {

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
	 
	 /**/
	 
	 public static final String PATH = "/cyk/utility/report/file/";
	 public static final String PATH_PATTERN = PATH+"*";
}