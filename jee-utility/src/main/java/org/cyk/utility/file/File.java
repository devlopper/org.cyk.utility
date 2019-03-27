package org.cyk.utility.file;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface File extends Objectable {

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
	
	Boolean isImage();
}
