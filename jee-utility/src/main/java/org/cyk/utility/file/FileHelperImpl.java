package org.cyk.utility.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;

@ApplicationScoped
public class FileHelperImpl extends AbstractHelper implements FileHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getName(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	@Override
	public String getExtension(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	@Override
	public String getMimeTypeByExtension(String extension) {
		return __inject__(MimeTypeGetter.class).setExtension(extension).execute().getOutput();
	}
	
	@Override
	public String getMimeTypeByNameAndExtension(String nameAndExtension) {
		return getMimeTypeByExtension(getExtension(nameAndExtension));
	}
	
	@Override
	public String concatenateNameAndExtension(String name, String extension) {
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(__inject__(StringHelper.class).isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	@Override
	public byte[] getBytes(File file) {
		byte[] buffer = new byte[1024 * 8];
		byte[] bytes = null;
		try {
			try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(file.getPath()))) {
		        int numberOfBytesRead = inputStream.read(buffer);
		        if(numberOfBytesRead > -1) {
		        	if (numberOfBytesRead < buffer.length) {
			        	bytes = Arrays.copyOf(buffer, numberOfBytesRead);
			        }else {
			        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 16);
				        while (numberOfBytesRead != -1) {
				        	outputStream.write(buffer, 0, numberOfBytesRead);
				        	numberOfBytesRead = inputStream.read(buffer);
				        }
				        bytes = outputStream.toByteArray();
				        outputStream.close();
			        }	
		        }		        
		    }
		    buffer = null;    
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		return bytes;
	}
	
	/**/
	
	public static String __getName__(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	public static String __getExtension__(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	public static String __getMimeTypeByExtension__(String extension) {
		String mime = null;
        String fileName = ConstantSeparator.FILE_NAME_EXTENSION+extension;        
        try {
            mime = Files.probeContentType(Paths.get(fileName));
        } catch (IOException e) {}        
        if(__inject__(StringHelper.class).isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        if(__inject__(StringHelper.class).isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = URLConnection.guessContentTypeFromName(fileName);        
        return mime;
	}
	
	public static String __getMimeTypeByNameAndExtension__(String nameAndExtension) {
		return __getMimeTypeByExtension__(__getExtension__(nameAndExtension));
	}
	
	public static String __concatenateNameAndExtension__(String name, String extension) {
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(__inject__(StringHelper.class).isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	public static byte[] __getBytes__(File file) {
		byte[] buffer = new byte[1024 * 8];
		byte[] bytes = null;
		try {
			try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(file.getPath()))) {
		        int numberOfBytesRead = inputStream.read(buffer);
		        if(numberOfBytesRead > -1) {
		        	if (numberOfBytesRead < buffer.length) {
			        	bytes = Arrays.copyOf(buffer, numberOfBytesRead);
			        }else {
			        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 16);
				        while (numberOfBytesRead != -1) {
				        	outputStream.write(buffer, 0, numberOfBytesRead);
				        	numberOfBytesRead = inputStream.read(buffer);
				        }
				        bytes = outputStream.toByteArray();
				        outputStream.close();
			        }	
		        }		        
		    }
		    buffer = null;    
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		return bytes;
	}
}
