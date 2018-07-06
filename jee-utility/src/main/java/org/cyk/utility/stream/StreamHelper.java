package org.cyk.utility.stream;

import java.io.File;
import java.io.InputStream;

import org.cyk.utility.helper.Helper;

public interface StreamHelper extends Helper {

	String getString(InputStream inputStream);
	String getString(File file);
	String getStringFromFile(String filePath);
}
