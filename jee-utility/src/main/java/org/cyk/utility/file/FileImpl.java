package org.cyk.utility.file;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class FileImpl extends AbstractObject implements File,Serializable {
	private static final long serialVersionUID = 1L;

	private byte[] bytes;
	private String name,extension,mimeType;
	private Long size;
	
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
	public Boolean isImage() {
		return StringUtils.startsWithIgnoreCase(getMimeType(), "image/");
	}
	
	@Override
	public String toString() {
		return getName()+"."+getExtension()+","+getMimeType()+"|"+getSize();
	}

}
