package org.cyk.utility.client.controller.web.jsf;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.file.FileExtensionGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.__kernel__.value.ValueHelper;

@WebServlet(name = "File Jsf Servlet", urlPatterns = { FileServlet.PATH_PATTERN })
public class FileServlet extends org.omnifaces.servlet.FileServlet implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @Override
    protected String getContentType(HttpServletRequest request, File file) {
    	return FileHelper.getMimeTypeByNameAndExtension(file.getName());
    }
    
    @Override
    protected boolean isAttachment(HttpServletRequest request, String contentType) {
    	return Boolean.FALSE;
    }
    */
    /*
	@Override
    protected File getFile(HttpServletRequest request) {
		File file = null;
		org.cyk.utility.__kernel__.file.File __file__ = null;
		String identifier = request.getParameter("identifier");
		String location = request.getParameter("location");
		if("session".equals(location)) {
			HttpSession session = request.getSession();
	        if(session!=null)
	        	__file__ = (org.cyk.utility.__kernel__.file.File) session.getAttribute(identifier);
		}else if("database".equals(location)) {
			
		}else if("folder".equals(location)) {
			
		}
		
        if(__file__!=null) {
        	try {
				//file = new File(__file__.getName()+"."+__file__.getExtension()); //File.createTempFile("img"+path, null);
        		file = File.createTempFile(__file__.getName(), "."+__file__.getExtension());
				FileUtils.writeByteArrayToFile(file, __file__.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return file;
    }
    */
	/**/
	
    public static String buildRelativeURI(String classSimpleName,String instanceIdentifier,String type) {
		return org.cyk.utility.client.controller.web.FileServlet.buildRelativeURI(PATH, classSimpleName, instanceIdentifier, type);
	}

	public static String buildRelativeURI(Object instance,String type) {
		if(instance == null)
			return null;
		return buildRelativeURI(instance.getClass().getSimpleName(), StringHelper.get(FieldHelper.readSystemIdentifier(instance)), type);
	}
    
	public static void log(HttpServletRequest httpServletRequest,Class<?> klass,String message) {
		if(Boolean.TRUE.equals(ValueConverter.getInstance().convertToBoolean(httpServletRequest.getParameter("loggable"))))
			LogHelper.logInfo(message, klass);
		
	}
	
	public static final String PATH = "/cyk/utility/controller/web/jsf/file/";
	public static final String PATH_PATTERN = PATH + "*";
	
	/**/
	
	public static interface Listener {
		File getFile(HttpServletRequest request) throws IOException;
		long getExpireTime(HttpServletRequest request, File file);
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override
			public File getFile(HttpServletRequest httpServletRequest) throws IOException {
				String classSimpleName = httpServletRequest.getParameter(org.cyk.utility.client.controller.web.FileServlet.PARAMETER_CLASS_SIMPLE_NAME);
				String instanceIdentifier = httpServletRequest.getParameter(org.cyk.utility.client.controller.web.FileServlet.PARAMETER_INSTANCE_IDENTIFIER);
				String type = httpServletRequest.getParameter(org.cyk.utility.client.controller.web.FileServlet.PARAMETER_TYPE);
				byte[] bytes = getBytes(httpServletRequest, classSimpleName, instanceIdentifier, type);
				if(bytes == null || bytes.length == 0)
					return null;
				String name = getName(httpServletRequest, classSimpleName, instanceIdentifier, type,bytes);
				String extension = ValueHelper.defaultToIfBlank(getExtension(httpServletRequest, classSimpleName, instanceIdentifier, type,bytes),"tmp");
				File file = File.createTempFile(name, "."+extension);
				FileUtils.writeByteArrayToFile(file, bytes);
				return file;
			}
			
			protected byte[] getBytes(HttpServletRequest httpServletRequest,String classSimpleName,String instanceIdentifier,String type) {
				return null;
			}
			
			protected String getName(HttpServletRequest httpServletRequest,String classSimpleName,String instanceIdentifier,String type,byte[] bytes) {
				return ValueHelper.defaultToIfBlank(type, "file");
			}
			
			protected String getExtension(HttpServletRequest httpServletRequest,String classSimpleName,String instanceIdentifier,String type,byte[] bytes) {
				return FileExtensionGetter.getInstance().get(bytes);
			}
			
			@Override
			public long getExpireTime(HttpServletRequest request, File file) {
				return -1;
			}

			/**/
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				
			}		
		}
	}
}