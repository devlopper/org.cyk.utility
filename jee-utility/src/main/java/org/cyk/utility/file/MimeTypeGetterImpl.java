package org.cyk.utility.file;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.enterprise.context.Dependent;
import javax.ws.rs.core.MediaType;

import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;


@Dependent
public class MimeTypeGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements MimeTypeGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private String extension;
	private byte[] bytes;
	
	@Override
	protected String __execute__() throws Exception {
		String mime = null;
		String extension = getExtension();
        String fileName = ConstantSeparator.FILE_NAME_EXTENSION+extension;
        
        try {
            mime = Files.probeContentType(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM)) {
        	mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        }
        
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM)) {
        	mime = URLConnection.guessContentTypeFromName(fileName);
        }
        
        return mime;
	}

	@Override
	public String getExtension() {
		return extension;
	}

	@Override
	public MimeTypeGetter setExtension(String extension) {
		this.extension = extension;
		return this;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public MimeTypeGetter setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}
	
}
