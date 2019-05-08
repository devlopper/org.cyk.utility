package org.cyk.utility.file;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.function.Consumer;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class FilesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Files> implements FilesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private PathsGetter pathsGetter;
	private Paths paths;
	private Boolean isFileChecksumComputable,isFilterByFileChecksum,isFileBytesComputable;
	
	@Override
	protected Files __execute__() throws Exception {
		/*
		Boolean isFilterByFileChecksum = __injectValueHelper__().defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
		Files files =  __inject__(Files.class).setIsDuplicateChecksumAllowed(!isFilterByFileChecksum);
		Strings directories = __injectValueHelper__().returnOrThrowIfBlank("file directories", getDirectories());
		Boolean isFileChecksumComputable = getIsFileChecksumComputable();
		
		directories.get().forEach(new Consumer<String>() {
			@Override
			public void accept(String directory) {
				try {
					java.nio.file.Files.newDirectoryStream(Paths.get(directory),path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach(new Consumer<Path>() {
						@Override
						public void accept(Path path) {
							if(Boolean.TRUE.equals(path.toFile().isDirectory())) {
								files.add(__inject__(FilesGetter.class).addDirectories(path.toString()).setIsFileChecksumComputable(isFileChecksumComputable)
										.execute().getOutput());
							}else {
								FileBuilder fileBuilder = __inject__(FileBuilder.class).setPath(path.getParent().toString()).setName(path.getFileName().toString())
										.setSize(path.toFile().length());
								
								if(Boolean.TRUE.equals(isFileChecksumComputable)) {
									try {
										fileBuilder.setChecksum(new String(new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(path.toFile())));
									} catch (Exception exception) {
										exception.printStackTrace();
									}
								}
								files.add(fileBuilder.execute().getOutput());	
							}
						}
					});
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});
		return files;
		*/
		
		//get files paths
		Paths paths = getPaths();
		if(paths == null) {
			PathsGetter pathsGetter = __injectValueHelper__().returnOrThrowIfBlank("files getter paths getter", getPathsGetter());
			paths = pathsGetter.execute().getOutput();
		}
		Files files = __inject__(Files.class);
		//build files
		if(__injectCollectionHelper__().isNotEmpty(paths)) {			
			paths.get().forEach(new Consumer<Path>() {
				@Override
				public void accept(Path path) {
					FileBuilder fileBuilder = __inject__(FileBuilder.class).setPath(path.getParent().toString()).setName(path.getFileName().toString())
							.setSize(path.toFile().length());
					files.add(fileBuilder.execute().getOutput());				
				}
			});
			
			//compute files bytes
			Boolean isFileBytesComputable = getIsFileBytesComputable();
			if(Boolean.TRUE.equals(isFileBytesComputable)) {
				files.computeBytes(Boolean.TRUE);
			}
			
			//compute files checksums
			Boolean isFileChecksumComputable = getIsFileChecksumComputable();
			if(Boolean.TRUE.equals(isFileChecksumComputable))
				files.computeChecksum(Boolean.TRUE);
			
			//filter files by checksum
			Boolean isFilterByFileChecksum = __injectValueHelper__().defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
			if(Boolean.TRUE.equals(isFilterByFileChecksum))
				files.removeDuplicateByChecksum();
		}
		
		return files;
		
		/*
		Files files =  __inject__(Files.class);
		Strings directories = __injectValueHelper__().returnOrThrowIfBlank("file directories", getDirectories());
		directories.get().forEach(new Consumer<String>() {
			@Override
			public void accept(String directory) {
				try {
					java.nio.file.Files.newDirectoryStream(java.nio.file.Paths.get(directory),path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach(new Consumer<Path>() {
						@Override
						public void accept(Path path) {
							if(Boolean.TRUE.equals(path.toFile().isDirectory())) {
								files.add(__inject__(FilesGetter.class).addDirectories(path.toString()).execute().getOutput());
							}else {
								FileBuilder fileBuilder = __inject__(FileBuilder.class).setPath(path.getParent().toString()).setName(path.getFileName().toString())
										.setSize(path.toFile().length());
								files.add(fileBuilder.execute().getOutput());	
							}
						}
					});
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});
		Boolean isFileChecksumComputable = getIsFileChecksumComputable();
		if(Boolean.TRUE.equals(isFileChecksumComputable))
			files.computeChecksum(Boolean.TRUE);
		Boolean isFilterByFileChecksum = __injectValueHelper__().defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
		if(Boolean.TRUE.equals(isFilterByFileChecksum))
			files.removeDuplicateByChecksum();
		return files;
		*/
	}
	
	/*
	@Override
	protected Files __execute__() throws Exception {
		Boolean isFilterByFileChecksum = __injectValueHelper__().defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
		Files filesAll =  __inject__(Files.class);
		Strings directories = __injectValueHelper__().returnOrThrowIfBlank("file directories", getDirectories());
		Boolean isFileChecksumComputable = getIsFileChecksumComputable();
		
		//get all files
		System.out.println("Getting all files");
		directories.get().forEach(new Consumer<String>() {
			@Override
			public void accept(String directory) {
				try {
					java.nio.file.Files.newDirectoryStream(Paths.get(directory),path -> path.toFile().isDirectory() || path.toFile().isFile()).forEach(new Consumer<Path>() {
						@Override
						public void accept(Path path) {
							if(Boolean.TRUE.equals(path.toFile().isDirectory())) {
								filesAll.add(__inject__(FilesGetter.class).addDirectories(path.toString()).setParent(this).execute().getOutput());
							}else {
								FileBuilder fileBuilder = __inject__(FileBuilder.class).setPath(path.getParent().toString()).setName(path.getFileName().toString())
										.setSize(path.toFile().length());
								filesAll.add(fileBuilder.execute().getOutput());	
							}
						}
					});
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		//compute checksum
		Boolean isChecksumComputable = Boolean.TRUE.equals(isFileChecksumComputable) && getParent() == null && __injectCollectionHelper__().isNotEmpty(filesAll);
		if(isChecksumComputable) {
			System.out.println("Computing checksums");
			filesAll.get().stream().forEach(new Consumer<File>() {

				@Override
				public void accept(File file) {
					try {
						file.setChecksum(new String(new DigestUtils(MessageDigestAlgorithms.SHA_1).digestAsHex(new java.io.File(file.getPathAndNameAndExtension()))));
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			});
		}
		
		//filter
		Files files =  __inject__(Files.class);
		if(isChecksumComputable) {
			System.out.println("Filtering");
			files.setIsDuplicateChecksumAllowed(Boolean.FALSE);
		}
		files.add(filesAll);
		System.out.println(files.get());
		return files;
		
	}
	*/
	
	@Override
	public PathsGetter getPathsGetter() {
		return pathsGetter;
	}
	
	@Override
	public PathsGetter getPathsGetter(Boolean injectIfNull) {
		return ((PathsGetter) __getInjectIfNull__(FIELD_PATHS_GETTER, injectIfNull)).setIsDirectoryGettable(Boolean.FALSE).setIsFileGettable(Boolean.TRUE);
	}
	
	@Override
	public FilesGetter setPathsGetter(PathsGetter pathsGetter) {
		this.pathsGetter = pathsGetter;
		return this;
	}
	
	@Override
	public Paths getPaths() {
		return paths;
	}
	
	@Override
	public Paths getPaths(Boolean injectIfNull) {
		return ((Paths) __getInjectIfNull__(FIELD_PATHS, injectIfNull));
	}
	
	@Override
	public FilesGetter setPaths(Paths paths) {
		this.paths = paths;
		return this;
	}
	
	@Override
	public Boolean getIsFileChecksumComputable() {
		return isFileChecksumComputable;
	}
	
	@Override
	public FilesGetter setIsFileChecksumComputable(Boolean isFileChecksumComputable) {
		this.isFileChecksumComputable = isFileChecksumComputable;
		return this;
	}
	
	@Override
	public Boolean getIsFilterByFileChecksum() {
		return isFilterByFileChecksum;
	}
	
	@Override
	public FilesGetter setIsFilterByFileChecksum(Boolean isFilterByFileChecksum) {
		this.isFilterByFileChecksum = isFilterByFileChecksum;
		return this;
	}
	
	@Override
	public FilesGetter setIsFileBytesComputable(Boolean isFileBytesComputable) {
		this.isFileBytesComputable = isFileBytesComputable;
		return this;
	}
	
	@Override
	public Boolean getIsFileBytesComputable() {
		return isFileBytesComputable;
	}
	
	/**/
	
	public static final String FIELD_PATHS = "paths";
	public static final String FIELD_PATHS_GETTER = "pathsGetter";
	
}
