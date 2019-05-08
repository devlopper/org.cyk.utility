package org.cyk.utility.file;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FilesGetter extends FunctionWithPropertiesAsInput<Files> {

	PathsGetter getPathsGetter();
	PathsGetter getPathsGetter(Boolean injectIfNull);
	FilesGetter setPathsGetter(PathsGetter pathsGetter);
	
	Boolean getIsFileBytesComputable();
	FilesGetter setIsFileBytesComputable(Boolean isFileBytesComputable);
	
	Boolean getIsFileChecksumComputable();
	FilesGetter setIsFileChecksumComputable(Boolean isFileChecksumComputable);
	
	Boolean getIsFilterByFileChecksum();
	FilesGetter setIsFilterByFileChecksum(Boolean isFilterByFileChecksum);
	
}


