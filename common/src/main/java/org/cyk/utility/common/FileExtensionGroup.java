package org.cyk.utility.common;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

public enum FileExtensionGroup {
	
	IMAGE("bmp","jpg","jpeg","png"),
	WORD("doc","docx"),
	TEXT("txt"),
	XML("xml"),
	JASPER("jrxml"),
	RICH_TEXT("pdf"),
	;
	@Getter
	private Set<String> extensions = new LinkedHashSet<String>();
	
	private FileExtensionGroup(String...extensions) {
		if(extensions!=null)
			for(String extension : extensions)
				this.extensions.add(extension.toLowerCase());
	}
}