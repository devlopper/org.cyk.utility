package org.cyk.utility.file;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.Strings;

@Deprecated
public interface PathsGetter extends FunctionWithPropertiesAsInput<Paths> {

	Strings getDirectories();
	Strings getDirectories(Boolean injectIfNull);
	PathsGetter setDirectories(Strings directories);
	PathsGetter addDirectories(Collection<String> directories);
	PathsGetter addDirectories(String...directories);
	PathsGetter addDirectories(Strings directories);
	PathsGetter addDirectoriesByJavaFiles(Collection<java.io.File> files);
	PathsGetter addDirectoriesByJavaFiles(java.io.File...files);
	
	Boolean getIsFileGettable();
	PathsGetter setIsFileGettable(Boolean isFileGettable);
	
	Boolean getIsDirectoryGettable();
	PathsGetter setIsDirectoryGettable(Boolean isDirectoryGettable);
	
	Integer getMaximalNumberOfPath();
	PathsGetter setMaximalNumberOfPath(Integer maximalNumberOfPath);
}
