package org.cyk.utility.file;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.collection.CollectionHelperImpl;

public interface TextExtractorListener {
	
	Set<String> getInvalidLines(String text);
	
	default String processExtractedText(String text) {
		Set<String> invalidLines = getInvalidLines(text);
		if(CollectionHelperImpl.__isNotEmpty__(invalidLines))
			for(String invalidLine : invalidLines) {
				text = StringUtils.remove(text, invalidLine+"\n");		
			}
		return text;
	}
	
}