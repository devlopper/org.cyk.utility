package org.cyk.utility.file;

import java.io.InputStream;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FileBuilder extends FunctionWithPropertiesAsInput<File> {

	InputStream getInputStream();
	FileBuilder setInputStream(InputStream inputStream);
	
	Class<?> getClazz();
	FileBuilder setClazz(Class<?> clazz);
	
	String getName();
	FileBuilder setName(String name);
}
