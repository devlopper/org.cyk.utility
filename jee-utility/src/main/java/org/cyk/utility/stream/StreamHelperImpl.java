package org.cyk.utility.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class StreamHelperImpl extends AbstractHelper implements StreamHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getString(InputStream inputStream) {
		return __getString__(inputStream);
	}

	@Override
	public String getString(File file) {
		return __getString__(file);
	}
	
	@Override
	public String getStringFromFile(String filePath) {
		return __getStringFromFile__(filePath);
	}
	
	/**/
	
	public static String __getString__(InputStream inputStream) {
		try {
			return IOUtils.toString(inputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String __getString__(File file) {
		try {
			return __getString__(new FileInputStream(file));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static String __getStringFromFile__(String filePath) {
		String strings;
		File file = new File(filePath);
		if(file.exists()){
			strings = __getString__(file);
		}else{
			strings = null;
			//TODO log warning
		}
		return strings;
	}
}
