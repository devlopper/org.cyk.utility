package org.cyk.utility.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.byte_.HashFunction;


@Dependent @Deprecated
public class FileImpl extends AbstractObject implements File,Serializable {
	private static final long serialVersionUID = 1L;

	private byte[] bytes;
	private String path,name,extension,mimeType,uniformResourceLocator,checksum;
	private Long size;
	
	@Override
	public File setIdentifier(Object identifier) {
		return (File) super.setIdentifier(identifier);
	}
	
	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public File setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}
	
	@Override
	public byte[] computeBytes() {
		String pathAndNameAndExtension = getPathAndNameAndExtension();
		byte[] buffer = new byte[1024 * 8];
		byte[] bytes = null;
		try {
			try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(pathAndNameAndExtension))) {
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
	    setBytes(bytes);
		return bytes;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public File setPath(String path) {
		this.path = path;
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public File setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getExtension() {
		return extension;
	}

	@Override
	public File setExtension(String extension) {
		this.extension = extension;
		return this;
	}
	
	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public File setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}
	
	@Override
	public Long getSize() {
		return size;
	}
	
	@Override
	public File setSize(Long size) {
		this.size = size;
		return this;
	}
	
	@Override
	public String getUniformResourceLocator() {
		return uniformResourceLocator;
	}
	
	@Override
	public File setUniformResourceLocator(String uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}
	
	@Override
	public String getChecksum() {
		return checksum;
	}
	
	@Override
	public File setChecksum(String checksum) {
		this.checksum = checksum;
		return this;
	}
	
	@Override
	public String computeChecksum() {
		String checksum = null;
		/*
		try {
			checksum = new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(new java.io.File(getPathAndNameAndExtension()));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		*/
		/*
		try {
			checksum = calcSHA1(new java.io.File(getPathAndNameAndExtension()));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		*/
		byte[] bytes = getBytes();
		if(bytes == null) {
			String pathAndNameAndExtension = getPathAndNameAndExtension();
			if(StringHelper.isNotBlank(pathAndNameAndExtension))
				bytes = __inject__(FileHelper.class).getBytes(new java.io.File(pathAndNameAndExtension));
		}
		if(bytes != null)
			checksum = __inject__(HashFunction.class).setBytes(bytes).setAlgorithm("SHA-1").execute().getOutput();
		setChecksum(checksum);
		return checksum;
	}
	
	@Override
	public Boolean isImage() {
		return StringUtils.startsWithIgnoreCase(getMimeType(), "image/");
	}
	
	@Override
	public Boolean isText() {
		return StringUtils.startsWithIgnoreCase(getMimeType(), "text/");
	}
	
	@Override
	public String getNameAndExtension() {
		String nameAndExtension = getName();
		String extension = getExtension();
		if(StringHelper.isNotBlank(extension))
			nameAndExtension = nameAndExtension + "." + extension;
		return nameAndExtension;
	}
	
	@Override
	public String getPathAndNameAndExtension() {
		String pathNameAndExtension = getNameAndExtension();
		String path = getPath();
		if(StringHelper.isNotBlank(path)) {
			path =StringHelper.addToEndIfDoesNotEndWith(path, "/");
			pathNameAndExtension = path + pathNameAndExtension;
		}
		return pathNameAndExtension;
	}
	
	@Override
	public String toString() {
		return StringUtils.defaultIfBlank(getPath(),ConstantEmpty.STRING)+getNameAndExtension()+","+getMimeType()+"|"+getSize();
	}

}
