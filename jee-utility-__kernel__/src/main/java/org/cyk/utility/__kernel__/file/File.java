package org.cyk.utility.__kernel__.file;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface File extends Objectable {

	@Override File setIdentifier(Object identifier);
	
	String getPath();
	File setPath(String path);
	
	String getName();
	File setName(String name);
	
	String getExtension();
	File setExtension(String extension);
	
	String getMimeType();
	File setMimeType(String mimeType);
	
	Long getSize();
	File setSize(Long size);
	
	byte[] getBytes();
	File setBytes(byte[] bytes);
	
	String getUniformResourceLocator();
	File setUniformResourceLocator(String uniformResourceLocator);
	
	String getChecksum();
	File setChecksum(String checksum);
	
	/**/
	
	Boolean isImage();
	Boolean isText();
	
	/**/
	
	String getNameAndExtension();
	String getPathAndNameAndExtension();
}
