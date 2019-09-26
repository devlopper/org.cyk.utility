package org.cyk.utility.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.activation.MimetypesFileTypeMap;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.byte_.ByteHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.number.Intervals;
import org.cyk.utility.runnable.RunnableHelperImpl;
import org.cyk.utility.__kernel__.value.ValueHelper;

@ApplicationScoped
public class FileHelperImpl extends AbstractHelper implements FileHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getName(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	@Override
	public String getExtension(String string) {
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	@Override
	public String getMimeTypeByExtension(String extension) {
		return __inject__(MimeTypeGetter.class).setExtension(extension).execute().getOutput();
	}
	
	@Override
	public String getMimeTypeByNameAndExtension(String nameAndExtension) {
		return getMimeTypeByExtension(getExtension(nameAndExtension));
	}
	
	@Override
	public String concatenateNameAndExtension(String name, String extension) {
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(StringHelper.isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	@Override
	public byte[] getBytes(java.io.File file) {
		byte[] buffer = new byte[1024 * 8];
		byte[] bytes = null;
		try {
			try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(file.getPath()))) {
		        int numberOfBytesRead = inputStream.read(buffer);
		        if(numberOfBytesRead > -1) {
		        	if (numberOfBytesRead < buffer.length) {
			        	bytes = Arrays.copyOf(buffer, numberOfBytesRead);
			        }else {
			        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 16);
				        while (numberOfBytesRead != -1) {
				        	outputStream.write(buffer, 0, numberOfBytesRead);
				        	numberOfBytesRead = inputStream.read(buffer);
				        }
				        bytes = outputStream.toByteArray();
				        outputStream.close();
			        }	
		        }		        
		    }
		    buffer = null;    
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		return bytes;
	}
	
	/**/
	
	public static String __getName__(String string) {
		return StringHelper.isBlank(string) ? null : StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	public static String __getExtension__(String string) {
		return StringHelper.isBlank(string) ? null : StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	public static String __getMimeTypeByExtension__(String extension) {
		if(StringHelper.isBlank(extension))
			return null;
		String mime = null;
        String fileName = ConstantSeparator.FILE_NAME_EXTENSION+extension;        
        try {
            mime = java.nio.file.Files.probeContentType(java.nio.file.Paths.get(fileName));
        } catch (IOException e) {}        
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        if(StringHelper.isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = URLConnection.guessContentTypeFromName(fileName);        
        return mime;
	}
	
	public static String __getMimeTypeByNameAndExtension__(String nameAndExtension) {
		if(StringHelper.isBlank(nameAndExtension))
			return null;
		return __getMimeTypeByExtension__(__getExtension__(nameAndExtension));
	}
	
	public static String __concatenateNameAndExtension__(String name, String extension) {
		if(StringHelper.isBlank(name) && StringHelper.isBlank(extension))
			return null;
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(StringHelper.isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	public static byte[] __getBytes__(File file) {
		byte[] buffer = new byte[1024 * 8];
		byte[] bytes = null;
		try {
			try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(file.getPath()))) {
		        int numberOfBytesRead = inputStream.read(buffer);
		        if(numberOfBytesRead > -1) {
		        	if (numberOfBytesRead < buffer.length) {
			        	bytes = Arrays.copyOf(buffer, numberOfBytesRead);
			        }else {
			        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 16);
				        while (numberOfBytesRead != -1) {
				        	outputStream.write(buffer, 0, numberOfBytesRead);
				        	numberOfBytesRead = inputStream.read(buffer);
				        }
				        bytes = outputStream.toByteArray();
				        outputStream.close();
			        }	
		        }		        
		    }
		    buffer = null;    
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		return bytes;
	}
	
	public static Paths __getPaths__(Collection<String> directories,String nameRegularExpression,Boolean isDirectoryGettable,Boolean isFileGettable,Integer maximalNumberOfPath) {
		if(CollectionHelper.isEmpty(directories))
			return null;
		final List<String> directoriesFinal = new ArrayList<>(directories);
		final Boolean isDirectoryGettableFinal = ValueHelper.defaultToIfNull(isDirectoryGettable,Boolean.TRUE);
		final Boolean isFileGettableFinal = ValueHelper.defaultToIfNull(isFileGettable,Boolean.TRUE);
		Paths paths = __inject__(Paths.class);
		paths.setCollectionClass(Set.class);
		for(Integer index = 0; index < directoriesFinal.size();) {
			Path path = java.nio.file.Paths.get(directoriesFinal.remove(0));
			if(path.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || path.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal))
				if(!directories.contains(path.toFile().toString())) {
					if(__addAndReturn__(paths, path, nameRegularExpression, maximalNumberOfPath))
						return paths;
				}
			
			//filter children
			if(path.toFile().isDirectory()) {
				try {
					DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path,x -> x.toFile().isDirectory() || x.toFile().isFile());
					for(Path child : directoryStream) {
						if(child.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || child.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal)) {
							if(__addAndReturn__(paths, child, nameRegularExpression, maximalNumberOfPath))
								return paths;
						}
						
						if(child.toFile().isDirectory()) {
							directoriesFinal.add(child.toString());
						}
					}
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}
			}
		}
		return paths;
	}
	
	private static Boolean __addAndReturn__(Paths paths,Path path,String nameRegularExpression,Integer maximalNumberOfPath) {
		if(nameRegularExpression == null || RegularExpressionHelper.match(path.toFile().getName(), nameRegularExpression))
			paths.add(path);
		if(maximalNumberOfPath != null && maximalNumberOfPath == paths.getSize())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public static Paths __getPaths__(Strings directories,String nameRegularExpression,Boolean isDirectoryGettable,Boolean isFileGettable,Integer maximalNumberOfPath) {
		if(CollectionHelper.isEmpty(directories))
			return null;
		return __getPaths__(directories.get(), nameRegularExpression, isDirectoryGettable, isFileGettable, maximalNumberOfPath);
	}
	
	public static Files __get__(Paths paths,Intervals sizeIntervals) {	
		if(CollectionHelper.isEmpty(paths))
			return null;
		if(CollectionHelper.isEmpty(sizeIntervals))
			sizeIntervals = null;
		Files files = null;
		for(Path path : paths.get()) {
			java.io.File javaFile = path.toFile();
			if(sizeIntervals!=null && !sizeIntervals.contains(javaFile.length()))
				continue;
			if(files == null)
				files = __inject__(Files.class);
			files.add(__build__(path.toUri().toString(), path.getParent().toString(), path.getFileName().toString(), null, null, null, null, null, path.toFile().length(), null, null));		
		}
		return files;
	}
	
	public static File __build__(String uniformResourceLocator,String path,String name,String extension,String mimeType,byte[] bytes,InputStream inputStream,Class<?> klass,Long size,String checksum,Boolean isChecksumComputable) {
		File file = __inject__(File.class);
		file.setUniformResourceLocator(uniformResourceLocator);
		if(StringHelper.isNotBlank(path))
			path = StringHelper.addToEndIfDoesNotEndWith(path, "/");
		file.setPath(path);
		file.setName(__getName__(name));
		if(StringHelper.isBlank(extension))
			extension = __getExtension__(name);
		file.setExtension(extension);		
		if(StringHelper.isBlank(mimeType))
			mimeType = __getMimeTypeByExtension__(file.getExtension());
		file.setMimeType(mimeType);
		file.setBytes(bytes);
		if(file.getBytes() == null) {
			if(inputStream == null) {
				if(klass!=null)
					inputStream = klass.getResourceAsStream(name);
			}
			if(inputStream!=null)
				try {
					file.setBytes(IOUtils.toByteArray(inputStream));
				} catch (IOException exception) {
					throw new RuntimeException(extension);
				}
		}
		if(size == null) {
			if(file.getBytes()!=null)
				size = Long.valueOf(file.getBytes().length);
		}
		file.setSize(size);
		if(StringHelper.isBlank(checksum)) {
			if(Boolean.TRUE.equals(isChecksumComputable)) {
				byte[] __bytes__ = file.getBytes();
				if(__bytes__ == null && file.getUniformResourceLocator()!=null) {
					//__bytes__ = 
				}
				if(file.getBytes()!=null && file.getBytes().length>0) {
					MessageDigest messageDigest;
					try {
						messageDigest = MessageDigest.getInstance("SHA1");
					} catch (NoSuchAlgorithmException exception) {
						throw new RuntimeException(extension);
					}
					messageDigest.update(file.getBytes());
					byte[] checksumBytes = messageDigest.digest();
					if(checksumBytes!=null && checksumBytes.length>0)
						checksum = new String(checksumBytes);
				}	
			}	
		}
		if(StringHelper.isNotBlank(checksum))
			file.setChecksum(new String(checksum));		
		return file;
	}
	
	public static String __computeChecksum__(byte[] bytes) {
		return ByteHelperImpl.__buildMessageDigest__(bytes, null);
	}
	
	public static void __computeChecksum__(Collection<File> files) {
		if(CollectionHelper.isEmpty(files))
			return;
		Collection<Runnable> runnables = null;
		for(File file : files) {
			if(file.getChecksum() == null) {
				if(runnables == null)
					runnables = new ArrayList<>();
				runnables.add(new Runnable() {
					@Override
					public void run() {
						byte[] bytes = file.getBytes();
						if(bytes == null && file.getUniformResourceLocator()!=null)
							bytes = __getBytesByUniformResourceIdentifier__(file.getUniformResourceLocator());
						file.setChecksum(__computeChecksum__(bytes));	
					}
				});
			}
		}
		if(runnables == null)
			return;
		RunnableHelperImpl.__run__(runnables, "compute checksum");
	}
	
	public static void __computeChecksum__(Files files) {
		if(CollectionHelper.isEmpty(files))
			return;
		__computeChecksum__(files.get());
	}
	
	public static void __computeChecksum__(File...files) {
		if(ArrayHelperImpl.__isEmpty__(files))
			return;
		__computeChecksum__(List.of(files));
	}
	
	public static String __extractText__(InputStream inputStream) {
		/*BodyContentHandler handler = new BodyContentHandler();
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    ParseContext parseContext = new ParseContext();
	    PDFParserConfig pdfParserConfig = new PDFParserConfig();
	    parseContext.set(PDFParserConfig.class, pdfParserConfig);
	    try {
			parser.parse(inputStream, handler, metadata,parseContext);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	    return handler.toString();
	    */
		return null;
	}

	public static String __extractText__(java.io.File file) {
		try {
			return __extractText__(new BufferedInputStream(new FileInputStream(file)));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static java.io.File __getJavaFileByUniformResourceIdentifier__(String uniformResourceIdentifier) {
		return new java.io.File(URI.create(uniformResourceIdentifier));
	}
	
	public static InputStream __getInputStreamByUniformResourceIdentifier__(String uniformResourceIdentifier) {
		try {
			return new BufferedInputStream(new FileInputStream(__getJavaFileByUniformResourceIdentifier__(uniformResourceIdentifier)));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static byte[] __getBytesByUniformResourceIdentifier__(String uniformResourceIdentifier) {
		try {
			return IOUtils.toByteArray(__getInputStreamByUniformResourceIdentifier__(uniformResourceIdentifier));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static Collection<URI> __getPathsURIs__(Collection<Path> paths) {
		if(CollectionHelper.isEmpty(paths))
			return null;
		Collection<URI> uris = new ArrayList<>();
		for(Path path : paths)
			uris.add(path.toFile().toURI());
		return uris;
	}
}
