package org.cyk.utility.file;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

@Dependent @Deprecated
public class PathsGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Paths> implements PathsGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings directories;
	private Boolean isFileGettable,isDirectoryGettable;
	private Integer maximalNumberOfPath;
	
	@Override
	protected Paths __execute__() throws Exception {
		Paths paths =  __inject__(Paths.class);
		Strings directories = __injectValueHelper__().returnOrThrowIfBlank("paths getter directories", getDirectories());
		Boolean isDirectoryGettable = __injectValueHelper__().defaultToIfNull(getIsDirectoryGettable(),Boolean.TRUE);
		Boolean isFileGettable = __injectValueHelper__().defaultToIfNull(getIsFileGettable(),Boolean.TRUE);
		
		Collection<Path> pathCollection = new ArrayList<Path>();
		directories.get().forEach(new Consumer<String>() {
			@Override
			public void accept(String directory) {
				Path path = java.nio.file.Paths.get(directory);
				if(Boolean.TRUE.equals(isDirectoryGettable))
					paths.add(path);
				__execute__(path,isDirectoryGettable,isFileGettable, pathCollection);
			}
		});
		Integer maximalNumberOfPath = getMaximalNumberOfPath();
		if(maximalNumberOfPath!=null && maximalNumberOfPath>0) {
			if(maximalNumberOfPath > pathCollection.size())
				maximalNumberOfPath = pathCollection.size();
			paths.add(((List<Path>)pathCollection).subList(0, maximalNumberOfPath));
		}else
			paths.add(pathCollection);
		
		return paths;
	}
	
	private void __execute__(Path directory,Boolean isDirectoryGettable,Boolean isFileGettable,Collection<Path> paths) {
		try {
			java.nio.file.Files.newDirectoryStream(directory,path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach(new Consumer<Path>() {
				@Override
				public void accept(Path path) {
					Boolean isDirectory = Boolean.TRUE.equals(path.toFile().isDirectory());
					if(isDirectory && Boolean.TRUE.equals(isDirectoryGettable) || !isDirectory && Boolean.TRUE.equals(isFileGettable))
						paths.add(path);
					if(isDirectory)
						__execute__(path,isDirectoryGettable,isFileGettable,paths);
				}
			});
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	public Strings getDirectories() {
		return directories;
	}

	@Override
	public Strings getDirectories(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_DIRECTORIES, injectIfNull);
	}

	@Override
	public PathsGetter setDirectories(Strings directories) {
		this.directories = directories;
		return this;
	}

	@Override
	public PathsGetter addDirectories(Collection<String> directories) {
		getDirectories(Boolean.TRUE).add(directories);
		return this;
	}

	@Override
	public PathsGetter addDirectories(String... directories) {
		getDirectories(Boolean.TRUE).add(directories);
		return this;
	}
	
	@Override
	public PathsGetter addDirectories(Strings directories) {
		getDirectories(Boolean.TRUE).add(directories);
		return this;
	}
	
	@Override
	public PathsGetter addDirectoriesByJavaFiles(Collection<java.io.File> files) {
		if(__inject__(CollectionHelper.class).isNotEmpty(files))
			files.forEach(new Consumer<java.io.File>() {
				@Override
				public void accept(java.io.File file) {
					if(file.isDirectory())
						addDirectories(file.getPath());
				}
			});
		return this;
	}
	
	@Override
	public PathsGetter addDirectoriesByJavaFiles(java.io.File... files) {
		return addDirectoriesByJavaFiles(__inject__(CollectionHelper.class).instanciate(files));
	}
	
	public static final String FIELD_DIRECTORIES = "directories";

	@Override
	public Boolean getIsFileGettable() {
		return isFileGettable;
	}

	@Override
	public PathsGetter setIsFileGettable(Boolean isFileGettable) {
		this.isFileGettable = isFileGettable;
		return this;
	}

	@Override
	public Boolean getIsDirectoryGettable() {
		return isDirectoryGettable;
	}

	@Override
	public PathsGetter setIsDirectoryGettable(Boolean isDirectoryGettable) {
		this.isDirectoryGettable = isDirectoryGettable;
		return this;
	}
	
	@Override
	public Integer getMaximalNumberOfPath() {
		return maximalNumberOfPath;
	}
	
	@Override
	public PathsGetter setMaximalNumberOfPath(Integer maximalNumberOfPath) {
		this.maximalNumberOfPath = maximalNumberOfPath;
		return this;
	}
}
