package org.cyk.utility.file;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.Strings;

public interface FilesGetter extends FunctionWithPropertiesAsInput<Files> {

	Strings getDirectories();
	Strings getDirectories(Boolean injectIfNull);
	FilesGetter setDirectories(Strings directories);
	FilesGetter addDirectories(Collection<String> directories);
	FilesGetter addDirectories(String...directories);
	FilesGetter addDirectories(Strings directories);
	FilesGetter addDirectoriesByJavaFiles(Collection<java.io.File> files);
	FilesGetter addDirectoriesByJavaFiles(java.io.File...files);
	
	Boolean getIsFileChecksumComputable();
	FilesGetter setIsFileChecksumComputable(Boolean isFileChecksumComputable);
	
	Boolean getIsFilterByFileChecksum();
	FilesGetter setIsFilterByFileChecksum(Boolean isFilterByFileChecksum);
}


