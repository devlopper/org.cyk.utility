package org.cyk.utility.__kernel__;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public interface IOHelper {

	static String getString(InputStream inputStream) {
		try {
			return IOUtils.toString(inputStream,UTF_8);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/**/
	
	String UTF_8 = "UTF-8";
}
