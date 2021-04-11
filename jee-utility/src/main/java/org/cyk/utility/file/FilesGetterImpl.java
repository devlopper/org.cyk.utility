package org.cyk.utility.file;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Consumer;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.number.Interval;
import org.cyk.utility.number.Intervals;

@Dependent @Deprecated
public class FilesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Files> implements FilesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private PathsGetter pathsGetter;
	private Paths paths;
	private Strings fileExtensions;
	private Intervals fileSizeIntervals;
	private Boolean isFileChecksumComputable,isFilterByFileChecksum,isFileBytesComputable;
	
	@Override
	protected Files __execute__() throws Exception {
		//get files paths
		Paths paths = getPaths();
		if(paths == null) {
			PathsGetter pathsGetter = ValueHelper.returnOrThrowIfBlank("files getter paths getter", getPathsGetter());
			paths = pathsGetter.execute().getOutput();
		}
		Files files = __inject__(Files.class);
		Strings fileExtensions = getFileExtensions();
		Intervals fileSizeIntervals = getFileSizeIntervals();
		//build files
		if(CollectionHelper.isNotEmpty(paths)) {			
			paths.get().forEach(new Consumer<Path>() {
				@Override
				public void accept(Path path) {
					java.io.File javaFile = path.toFile();
					Boolean isAddable = Boolean.TRUE;
					if(Boolean.TRUE.equals(isAddable) && CollectionHelper.isNotEmpty(fileExtensions))
						isAddable = CollectionHelper.contains(fileExtensions, __inject__(FileHelperToDel.class).getExtension(javaFile.getName()).toLowerCase());
					if(Boolean.TRUE.equals(isAddable) && CollectionHelper.isNotEmpty(fileSizeIntervals))
						isAddable = fileSizeIntervals.contains(javaFile.length());
					
					if(Boolean.TRUE.equals(isAddable)) {
						FileBuilder fileBuilder = __inject__(FileBuilder.class).setPath(path.getParent().toString()).setName(path.getFileName().toString())
								.setSize(path.toFile().length()).setUniformResourceLocator(path.toUri().toString());
						files.add(fileBuilder.execute().getOutput());		
					}
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
			Boolean isFilterByFileChecksum = ValueHelper.defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
			if(Boolean.TRUE.equals(isFilterByFileChecksum))
				files.removeDuplicateByChecksum();
		}
		
		return files;
		
		/*
		Files files =  __inject__(Files.class);
		Strings directories = ValueHelper.returnOrThrowIfBlank("file directories", getDirectories());
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
		Boolean isFilterByFileChecksum = ValueHelper.defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
		if(Boolean.TRUE.equals(isFilterByFileChecksum))
			files.removeDuplicateByChecksum();
		return files;
		*/
	}
	
	/*
	@Override
	protected Files __execute__() throws Exception {
		Boolean isFilterByFileChecksum = ValueHelper.defaultToIfNull(getIsFilterByFileChecksum(),Boolean.FALSE);
		Files filesAll =  __inject__(Files.class);
		Strings directories = ValueHelper.returnOrThrowIfBlank("file directories", getDirectories());
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
		Boolean isChecksumComputable = Boolean.TRUE.equals(isFileChecksumComputable) && getParent() == null && CollectionHelper.isNotEmpty(filesAll);
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
	
	@Override
	public Strings getFileExtensions() {
		return fileExtensions;
	}
	
	@Override
	public Strings getFileExtensions(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_FILE_EXTENSIONS, injectIfNull);
	}
	
	@Override
	public FilesGetter setFileExtensions(Strings fileExtensions) {
		this.fileExtensions = fileExtensions;
		return this;
	}
	
	@Override
	public FilesGetter addFileExtensions(Collection<String> fileExtensions) {
		getFileExtensions(Boolean.TRUE).add(fileExtensions);
		return this;
	}
	
	@Override
	public FilesGetter addFileExtensions(String... fileExtensions) {
		getFileExtensions(Boolean.TRUE).add(fileExtensions);
		return this;
	}
	
	@Override
	public Intervals getFileSizeIntervals() {
		return fileSizeIntervals;
	}
	
	@Override
	public Intervals getFileSizeIntervals(Boolean injectIfNull) {
		return (Intervals) __getInjectIfNull__(FIELD_FILE_SIZE_INTERVALS, injectIfNull);
	}
	
	@Override
	public FilesGetter setFileSizeIntervals(Intervals fileSizeIntervals) {
		this.fileSizeIntervals = fileSizeIntervals;
		return this;
	}
	
	@Override
	public FilesGetter addFileSizeIntervals(Collection<Interval> fileSizeIntervals) {
		getFileSizeIntervals(Boolean.TRUE).add(fileSizeIntervals);
		return this;
	}
	
	@Override
	public FilesGetter addFileSizeIntervals(Interval... fileSizeIntervals) {
		getFileSizeIntervals(Boolean.TRUE).add(fileSizeIntervals);
		return this;
	}
	
	/**/
	
	public static final String FIELD_PATHS = "paths";
	public static final String FIELD_PATHS_GETTER = "pathsGetter";
	public static final String FIELD_FILE_EXTENSIONS = "fileExtensions";
	public static final String FIELD_FILE_SIZE_INTERVALS = "fileSizeIntervals";
	
}
