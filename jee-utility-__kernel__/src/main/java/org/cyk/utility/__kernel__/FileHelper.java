package org.cyk.utility.__kernel__;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jboss.weld.exceptions.IllegalArgumentException;

public interface FileHelper {

	static String getString(File file) {
		try {
			return IOHelper.getString(new FileInputStream(file));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static String getString(String filePath) {
		if(filePath == null || filePath.isBlank())
			throw new IllegalArgumentException("File path is required");
		File file = new File(filePath);
		if(!file.exists())
			throw new RuntimeException("we cannot get string from File "+filePath+".It does not exist.");
		return getString(file);
	}
	
	
}
