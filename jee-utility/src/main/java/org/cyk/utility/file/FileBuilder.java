package org.cyk.utility.file;

import java.io.InputStream;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface FileBuilder extends FunctionWithPropertiesAsInput<File> {

	InputStream getInputStream();
	FileBuilder setInputStream(InputStream inputStream);
	
	byte[] getBytes();
	FileBuilder setBytes(byte[] bytes);
	
	Long getSize();
	FileBuilder setSize(Long size);
	
	Class<?> getClazz();
	FileBuilder setClazz(Class<?> clazz);
	
	String getPath();
	FileBuilder setPath(String path);
	
	String getName();
	FileBuilder setName(String name);
	
	String getExtension();
	FileBuilder setExtension(String extension);
	
	String getMimeType();
	FileBuilder setMimeType(String mimeType);
	
	String getUniformResourceLocator();
	FileBuilder setUniformResourceLocator(String uniformResourceLocator);
	
	String getChecksum();
	FileBuilder setChecksum(String checksum);
	
	Boolean getIsChecksumComputable();
	FileBuilder setIsChecksumComputable(Boolean isChecksumComputable);
}
