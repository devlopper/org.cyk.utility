package org.cyk.utility.file;

import java.io.Serializable;

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
