package org.cyk.utility.file;

import org.cyk.utility.helper.Helper;

@Deprecated
public interface FileHelperToDel extends Helper {

	String getName(String string);
	String getExtension(String string);
	String getMimeTypeByNameAndExtension(String nameAndExtension);
	String getMimeTypeByExtension(String extension);
	
	String concatenateNameAndExtension(String name,String extension);
	
	byte[] getBytes(java.io.File file);
}
