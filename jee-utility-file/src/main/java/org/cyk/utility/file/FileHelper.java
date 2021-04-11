package org.cyk.utility.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface FileHelper {

	static String getName(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}
	
	static String getExtension(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}
	
	static String getExtension(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		try {
			return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
		} catch (IOException exception) {
			LogHelper.log(exception, FileHelper.class);
			return null;
		}
	}
	
	static String getMimeTypeByExtension(String extension) {
		if(StringHelper.isBlank(extension))
			return null;
		String mime = null;
        String fileName = ConstantSeparator.FILE_NAME_EXTENSION+extension;        
        try {
            mime = java.nio.file.Files.probeContentType(java.nio.file.Paths.get(fileName));
        } catch (IOException e) {}        
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = URLConnection.guessContentTypeFromName(fileName);        
        return mime;
	}
	
	static String getMimeTypeByNameAndExtension(String nameAndExtension) {
		if(StringHelper.isBlank(nameAndExtension))
			return null;
		return getMimeTypeByExtension(getExtension(nameAndExtension));
	}
	
	static String concatenateNameAndExtension(String name,String extension) {
		if(StringHelper.isBlank(name) && StringHelper.isBlank(extension))
			return null;
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(StringHelper.isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	static byte[] getBytes(java.io.File file) {
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
	
	static String computeSha1(File file) {
		if(file == null)
			return null;
		try {
			return new String(new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(file));
		} catch (IOException exception) {
			throw new RuntimeException(exception);			
		}
	}
	
	static String computeSha1(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		try {
			return new String(new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(bytes));
		} catch (Exception exception) {
			throw new RuntimeException(exception);			
		}
	}
	
	static String computeSha1(Byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		byte[] array = new byte[bytes.length];
		for(Integer index = 0; index < bytes.length ; index = index + 1)
			array[index] = bytes[index];
		return computeSha1(array);
	}
}