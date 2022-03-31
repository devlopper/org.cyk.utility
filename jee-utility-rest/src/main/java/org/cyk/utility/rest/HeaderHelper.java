package org.cyk.utility.rest;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface HeaderHelper {
	
	static String formatContentDisposition(Boolean inline,String fileName) {
		if(inline == null)
			inline = Boolean.FALSE;
		return (inline ? "inline" : "attachment")+(StringHelper.isNotBlank(fileName) ? " ;filename=\""+fileName+"\"" : "");
	}
	
	static String formatContentDisposition(String fileName) {
		return formatContentDisposition(null, fileName);
	}
}