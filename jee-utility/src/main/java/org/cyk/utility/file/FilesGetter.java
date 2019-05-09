package org.cyk.utility.file;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.number.Interval;
import org.cyk.utility.number.Intervals;
import org.cyk.utility.string.Strings;

public interface FilesGetter extends FunctionWithPropertiesAsInput<Files> {

	PathsGetter getPathsGetter();
	PathsGetter getPathsGetter(Boolean injectIfNull);
	FilesGetter setPathsGetter(PathsGetter pathsGetter);
	
	Paths getPaths();
	Paths getPaths(Boolean injectIfNull);
	FilesGetter setPaths(Paths paths);
	
	Boolean getIsFileBytesComputable();
	FilesGetter setIsFileBytesComputable(Boolean isFileBytesComputable);
	
	Boolean getIsFileChecksumComputable();
	FilesGetter setIsFileChecksumComputable(Boolean isFileChecksumComputable);
	
	Boolean getIsFilterByFileChecksum();
	FilesGetter setIsFilterByFileChecksum(Boolean isFilterByFileChecksum);
	
	Strings getFileExtensions();
	Strings getFileExtensions(Boolean injectIfNull);
	FilesGetter setFileExtensions(Strings fileExtensions);
	FilesGetter addFileExtensions(Collection<String> fileExtensions);
	FilesGetter addFileExtensions(String...fileExtensions);
	
	Intervals getFileSizeIntervals();
	Intervals getFileSizeIntervals(Boolean injectIfNull);
	FilesGetter setFileSizeIntervals(Intervals fileSizeIntervals);
	FilesGetter addFileSizeIntervals(Collection<Interval> fileSizeIntervals);
	FilesGetter addFileSizeIntervals(Interval...fileSizeIntervals);
}


