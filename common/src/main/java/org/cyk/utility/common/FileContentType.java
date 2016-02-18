package org.cyk.utility.common;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public enum FileContentType {

	APPLICATION(FileExtension.PDF),
	IMAGE(FileExtension.BMP,FileExtension.JPG,FileExtension.JPEG,FileExtension.PNG),
	
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
				pextension.setContentType(this);
				pextension.setMime(value+Constant.CHARACTER_SLASH+pextension.getValue());
			}
		}
	}
		 
	private FileContentType(String value,FileExtension extension,FileExtension...extensions) {
		constructor(value, extension, extensions);
	}
	
	private FileContentType(FileExtension extension,FileExtension...extensions) {
		constructor(name().toLowerCase(), extension, extensions);
	}
	
	/**/
	
	public static FileContentType getByValue(String value,Boolean caseSensitive){
		for(FileContentType fileContentType : values())
			if(Boolean.TRUE.equals(caseSensitive) ? fileContentType.value.equals(value) : fileContentType.value.equalsIgnoreCase(value) )
				return fileContentType;
		return null;
	}
	
	public static FileContentType getByValue(String value){
		return getByValue(value, Boolean.FALSE);
	}
}
