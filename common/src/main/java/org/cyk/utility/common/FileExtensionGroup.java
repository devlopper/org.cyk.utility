package org.cyk.utility.common;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public enum FileExtensionGroup {
	
	IMAGE(FileExtension.BMP,FileExtension.JPG,FileExtension.JPEG,FileExtension.PNG),
	WORD(FileExtension.DOC,FileExtension.DOCX),
	TEXT(FileExtension.TXT),
	XML(FileExtension.XML),
	JASPER(FileExtension.JRXML),
	RICH_TEXT(FileExtension.PDF),
	;
	
	@Getter @Setter private String value;
	@Getter private final Set<FileExtension> extensions = new HashSet<FileExtension>(){
		private static final long serialVersionUID = 1657031688080720281L;
		public boolean add(FileExtension fileExtension) {
			Boolean added = super.add(fileExtension);
			
			return added;
		};
	};
	
	private void constructor(String value,FileExtension extension,FileExtension...extensions) {
		this.value = value;
		Set<FileExtension> fileExtensions = new HashSet<>();
		fileExtensions.add(extension);
		if(extensions!=null)
			for(FileExtension pextension : extensions)
				fileExtensions.add(pextension);
			
		for(FileExtension pextension : fileExtensions){
			if(this.extensions.add(pextension)){
				
			}
		}
	}
		 
	private FileExtensionGroup(String value,FileExtension extension,FileExtension...extensions) {
		constructor(value, extension, extensions);
	}
	
	private FileExtensionGroup(FileExtension extension,FileExtension...extensions) {
		constructor(name().toLowerCase(), extension, extensions);
	}
}