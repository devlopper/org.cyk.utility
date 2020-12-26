package org.cyk.utility.client.controller.web;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

@Deprecated
public interface FileServletController {

	byte[] getBytes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);	
	void setHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes);
	void write(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) throws IOException;
	
	String getIsInline(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
	String getFileExtension(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
	String getFileName(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes,String extension);	
	String getContentType(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes);
	String getContentDisposition(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes);
	String getContentLenght(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes);
	
	public static abstract class AbstractImpl extends AbstractObject implements FileServletController,Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		public byte[] getBytes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return getBytes(httpServletRequest, httpServletResponse, httpServletRequest.getParameter(PARAMETER_CLASS_SIMPLE_NAME)
					, httpServletRequest.getParameter(PARAMETER_INSTANCE_IDENTIFIER), httpServletRequest.getParameter(PARAMETER_TYPE));
		}
		
		protected byte[] getBytes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String classSimpleName,String instanceIdentifier,String type) {
			return null;
		}
		
		@Override
		public void setHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {
			httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, getContentType(httpServletRequest, httpServletResponse, bytes));
			httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, getContentLenght(httpServletRequest, httpServletResponse, bytes));
			httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(httpServletRequest, httpServletResponse, bytes));			
		}
		
		@Override
		public void write(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) throws IOException {
			//httpServletResponse.reset();
			//httpServletResponse.setBufferSize(Helper.DEFAULT_BUFFER_SIZE);		
			Helper.write(bytes, httpServletResponse.getOutputStream());
		}
		
		@Override
		public String getFileName(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes,String extension) {
			return "file_"+System.currentTimeMillis()+"."+extension;
		}
		
		@Override
		public String getFileExtension(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return null;
		}
		
		@Override
		public String getContentType(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {			
			return FileHelper.getMimeTypeByExtension(getFileExtension(httpServletRequest, httpServletResponse));
		}
		
		@Override
		public String getContentDisposition(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, byte[] bytes) {
			String isInline = getIsInline(httpServletRequest, httpServletResponse);
			if(StringHelper.isBlank(isInline))
		    	isInline = Boolean.TRUE.toString();
			String extension = getFileExtension(httpServletRequest, httpServletResponse);
			String fileName = getFileName(httpServletRequest, httpServletResponse, bytes, extension);
		    return (Boolean.parseBoolean(isInline) ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME+"="+fileName;
		}
		
		@Override
		public String getContentLenght(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,byte[] bytes) {
			if(bytes == null)
				return ConstantEmpty.STRING;
			return String.valueOf(bytes.length);
		}
		
		@Override
		public String getIsInline(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
			return null;
		}
	}
	
	/**/
	
	static FileServletController getInstance() {
		return Helper.getInstance(FileServletController.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String PARAMETER_CLASS_SIMPLE_NAME = "c";
	String PARAMETER_INSTANCE_IDENTIFIER = "i";
	String PARAMETER_TYPE = "t";
}