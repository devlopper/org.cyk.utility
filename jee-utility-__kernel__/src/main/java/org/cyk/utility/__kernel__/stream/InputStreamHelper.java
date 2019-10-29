package org.cyk.utility.__kernel__.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface InputStreamHelper {

	static String getContentAsString(InputStream inputStream,Charset charset) {
		if(inputStream == null)
			return null;
		if(charset == null)
			charset = CHAR_SET_UTF_8;
		try {
			return IOUtils.toString(inputStream, charset);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static String getContentAsString(InputStream inputStream,String charsetName) {
		if(inputStream == null)
			return null;
		if(StringHelper.isBlank(charsetName))
			charsetName = UTF_8;
		return getContentAsString(inputStream, Charset.forName(charsetName));
	}
	
	static String getContentAsString(InputStream inputStream) {
		return getContentAsString(inputStream,CHAR_SET_UTF_8);
	}
	
	String UTF_8 = "UTF-8";
	Charset CHAR_SET_UTF_8 = Charset.forName(UTF_8);
}
