package org.cyk.utility.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.string.StringHelper;

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
		
		try {
			checksum = calcSHA1(new java.io.File(getPathAndNameAndExtension()));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		setChecksum(checksum);
		return checksum;
	}
	
	/**
	 * Read the file and calculate the SHA-1 checksum
	 * 
	 * @param file
	 *            the file to read
	 * @return the hex representation of the SHA-1 using uppercase chars
	 * @throws FileNotFoundException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws NoSuchAlgorithmException
	 *             should never happen
	 */
	private static String calcSHA1(java.io.File file) throws FileNotFoundException, IOException, NoSuchAlgorithmException {

	    MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
	    try (InputStream input = new FileInputStream(file)) {

	        byte[] buffer = new byte[8192];
	        int len = input.read(buffer);

	        while (len != -1) {
	            sha1.update(buffer, 0, len);
	            len = input.read(buffer);
	        }

	        return new HexBinaryAdapter().marshal(sha1.digest());
	    }
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
		if(__inject__(StringHelper.class).isNotBlank(extension))
			nameAndExtension = nameAndExtension + "." + extension;
		return nameAndExtension;
	}
	
	@Override
	public String getPathAndNameAndExtension() {
		String pathNameAndExtension = getNameAndExtension();
		String path = getPath();
		if(__inject__(StringHelper.class).isNotBlank(path)) {
			path = __inject__(StringHelper.class).addToEndIfDoesNotEndWith(path, "/");
			pathNameAndExtension = path + pathNameAndExtension;
		}
		return pathNameAndExtension;
	}
	
	@Override
	public String toString() {
		return StringUtils.defaultIfBlank(getPath(),ConstantEmpty.STRING)+getNameAndExtension()+","+getMimeType()+"|"+getSize();
	}

}
