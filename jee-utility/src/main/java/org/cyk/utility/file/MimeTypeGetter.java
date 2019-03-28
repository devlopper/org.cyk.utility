package org.cyk.utility.file;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface MimeTypeGetter extends FunctionWithPropertiesAsInput<String> {

	String getExtension();
	MimeTypeGetter setExtension(String extension);
	
	byte[] getBytes();
	MimeTypeGetter setBytes(byte[] bytes);
	
}
