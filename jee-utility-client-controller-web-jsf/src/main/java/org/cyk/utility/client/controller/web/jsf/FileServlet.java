package org.cyk.utility.client.controller.web.jsf;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.client.controller.web.Constant;

@WebServlet(Constant.FILE_URL_PATTERN)
public class FileServlet extends org.omnifaces.servlet.FileServlet implements Serializable {
    private static final long serialVersionUID = 1L;

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
    
}
