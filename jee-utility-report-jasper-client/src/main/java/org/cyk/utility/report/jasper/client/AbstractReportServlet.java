package org.cyk.utility.report.jasper.client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.client.controller.web.jsf.FileServlet;
import org.cyk.utility.report.GenericReportService;

public abstract class AbstractReportServlet extends org.omnifaces.servlet.FileServlet implements Serializable {

	@Override
	protected File getFile(HttpServletRequest request) {
		try {
			return getListener().getFile(request);
		} catch (IOException exception) {
			LogHelper.log(exception, getClass());
			return null;
		}
	}

	@Override
	protected long getExpireTime(HttpServletRequest request, File file) {
		return getListener().getExpireTime(request, file);
	}

	protected abstract Listener getListener();

	public static interface Listener extends FileServlet.Listener {

		public static abstract class AbstractImpl extends FileServlet.Listener.AbstractImpl
				implements Listener, Serializable {

			@Override
			protected byte[] getBytes(HttpServletRequest httpServletRequest, String classSimpleName,String instanceIdentifier, String type) {
				String identifier = httpServletRequest.getParameter(GenericReportService.PARAMETER_IDENTIFIER);
				String parametersAsJson = httpServletRequest.getParameter(GenericReportService.PARAMETER_PARAMETERS_AS_JSON);
				String fileType = httpServletRequest.getParameter(GenericReportService.PARAMETER_FILE_TYPE);
				//Content must be in line in order to read its bytes
				Boolean isContentInline = Boolean .TRUE; //ValueHelper.convertToBoolean(httpServletRequest.getParameter(GenericReportService.PARAMETER_IS_CONTENT_INLINE));
				Response response = getResponse(identifier, parametersAsJson, fileType, isContentInline);
				if (response == null) {
					LogHelper.logSevere("No response got", getClass());
					return null;
				}
				if (Response.Status.OK.getStatusCode() != response.getStatus()) {
					LogHelper.logSevere("Not OK : " + response.getStatusInfo(), getClass());
					return null;
				}
				if (!response.hasEntity()) {
					LogHelper.logSevere("Not entity got : " + response.getStatusInfo(), getClass());
					return null;
				}
				byte[] bytes = response.readEntity(byte[].class);
				FileServlet.log(httpServletRequest, getClass(),String.format("Number of bytes is %s", bytes == null ? null : bytes.length));
				return bytes;
			}
			
			protected Response getResponse(String identifier,String parametersAsJson,String fileType,Boolean isContentInline) {
				return ProxyGetter.getInstance().get(GenericReportService.class).get(identifier, parametersAsJson,fileType, isContentInline);
			}

			/**/

			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {

			}
		}
	}
}