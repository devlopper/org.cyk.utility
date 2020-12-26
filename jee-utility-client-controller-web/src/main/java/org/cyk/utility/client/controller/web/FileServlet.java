package org.cyk.utility.client.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

@WebServlet(name = "File Web Servlet", urlPatterns = { FileServlet.PATH_PATTERN })
public class FileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		super.doGet(httpServletRequest, httpServletResponse);
		HttpServletListener listener = LISTENER;
		if(listener == null)
			listener = new ListenerImpl();
		listener.doGet(httpServletRequest, httpServletResponse);
		/*
		httpServletResponse.reset();
		httpServletResponse.setBufferSize(Helper.DEFAULT_BUFFER_SIZE);	
		
		byte[] bytes = FileServletController.getInstance().getBytes(httpServletRequest, httpServletResponse);
		FileServletController.getInstance().write(httpServletRequest, httpServletResponse, bytes);
		FileServletController.getInstance().setHeaders(httpServletRequest, httpServletResponse, bytes);
		*/
	}
	
	/**/
	
	public static String buildRelativeURI(String path,String classSimpleName,String instanceIdentifier,String type) {
		return String.format(RELATIVE_URI_FORMAT,path, PARAMETER_CLASS_SIMPLE_NAME,classSimpleName
				, PARAMETER_INSTANCE_IDENTIFIER,instanceIdentifier
				, PARAMETER_TYPE,type);
	}
	
	public static String buildRelativeURI(String classSimpleName,String instanceIdentifier,String type) {
		return buildRelativeURI(PATH,classSimpleName, instanceIdentifier, type);
	}

	public static String buildRelativeURI(Object instance,String type) {
		if(instance == null)
			return null;
		return buildRelativeURI(instance.getClass().getSimpleName(), StringHelper.get(FieldHelper.readSystemIdentifier(instance)), type);
	}
	
	/**/

	public static final String PATH = "/cyk/utility/controller/web/file/";
	public static final String PATH_PATTERN = PATH + "*";
	public static final String RELATIVE_URI_FORMAT = "%s?%s=%s&%s=%s&%s=%s";
	
	public static final String PARAMETER_CLASS_SIMPLE_NAME = "c";
	public static final String PARAMETER_INSTANCE_IDENTIFIER = "i";
	public static final String PARAMETER_TYPE = "t";
	
	/**/
	
	public static class ListenerImpl extends HttpServletListener.AbstractImpl{
		
		@Override
		public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)throws ServletException, IOException {
			super.doGet(httpServletRequest, httpServletResponse);
			httpServletResponse.reset();
			httpServletResponse.setBufferSize(Helper.DEFAULT_BUFFER_SIZE);	
			
			byte[] bytes = getBytes(httpServletRequest, httpServletResponse);
			write(httpServletRequest, httpServletResponse, bytes);
			setHeaders(httpServletRequest, httpServletResponse, bytes);
		}
		
		public byte[] getBytes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return getBytes(httpServletRequest, httpServletResponse, httpServletRequest.getParameter(PARAMETER_CLASS_SIMPLE_NAME)
					, httpServletRequest.getParameter(PARAMETER_INSTANCE_IDENTIFIER), httpServletRequest.getParameter(PARAMETER_TYPE));
		}
		
		protected byte[] getBytes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String classSimpleName,String instanceIdentifier,String type) {
			return null;
		}
		
		public void setHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {
			httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, getContentType(httpServletRequest, httpServletResponse, bytes));
			httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, getContentLenght(httpServletRequest, httpServletResponse, bytes));
			httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(httpServletRequest, httpServletResponse, bytes));			
		}
		
		public void write(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) throws IOException {
			//httpServletResponse.reset();
			//httpServletResponse.setBufferSize(Helper.DEFAULT_BUFFER_SIZE);		
			Helper.write(bytes, httpServletResponse.getOutputStream());
		}
		
		public String getFileName(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes,String extension) {
			return "file_"+System.currentTimeMillis()+"."+extension;
		}
		
		public String getFileExtension(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return null;
		}
		
		public String getContentType(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {			
			return FileHelper.getMimeTypeByExtension(getFileExtension(httpServletRequest, httpServletResponse));
		}
		
		public String getContentDisposition(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, byte[] bytes) {
			String isInline = getIsInline(httpServletRequest, httpServletResponse);
			if(StringHelper.isBlank(isInline))
		    	isInline = Boolean.TRUE.toString();
			String extension = getFileExtension(httpServletRequest, httpServletResponse);
			String fileName = getFileName(httpServletRequest, httpServletResponse, bytes, extension);
		    return (Boolean.parseBoolean(isInline) ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME+"="+fileName;
		}
		
		public String getContentLenght(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {
			if(bytes == null)
				return ConstantEmpty.STRING;
			return String.valueOf(bytes.length);
		}
		
		public String getIsInline(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return null;
		}
	}
	
	public static HttpServletListener LISTENER = new ListenerImpl();
}