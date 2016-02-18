package org.cyk.utility.common;

import lombok.Getter;
import lombok.Setter;

public enum FileExtension {
	
	/* Text */
	TXT,XML,JRXML,
	
	/* Image */
	BMP,JPG,JPEG,PNG,
	
	/* Application */
	DOC,DOCX,PDF,
	
	;
	
	
	@Getter @Setter private String value;
	@Getter @Setter private FileContentType contentType;
	@Getter @Setter private String mime;
	
	private void constructor(String value) {
		this.value = value;
	}
	
	private FileExtension() {
		constructor(name().toLowerCase());
	}
	
	private FileExtension(String value) {
		constructor(value);
	}
	
	/**/
	
	public static FileExtension getByValue(String value,Boolean caseSensitive){
		for(FileExtension fileExtension : values())
			if(Boolean.TRUE.equals(caseSensitive) ? fileExtension.value.equals(value) : fileExtension.value.equalsIgnoreCase(value) )
				return fileExtension;
		return null;
	}
	
	public static FileExtension getByValue(String value){
		return getByValue(value, Boolean.FALSE);
	}
}