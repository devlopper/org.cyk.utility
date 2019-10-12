package org.cyk.utility.__kernel__.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.IOHelper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.number.ByteHelper;
import org.cyk.utility.__kernel__.number.Intervals;
import org.cyk.utility.__kernel__.runnable.RunnableHelper;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.jboss.weld.exceptions.IllegalArgumentException;

public interface FileHelper {

	static String getString(java.io.File file) {
		try {
			return IOHelper.getString(new FileInputStream(file));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static String getString(String filePath) {
		if(filePath == null || filePath.isBlank())
			throw new IllegalArgumentException("File path is required");
		java.io.File file = new java.io.File(filePath);
		if(!file.exists())
			throw new RuntimeException("we cannot get string from File "+filePath+".It does not exist.");
		return getString(file);
	}
	
	static String getName(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}
	
	static String getExtension(String string) {
		if(StringHelper.isBlank(string))
			return null;
		return StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}
	
	static String getMimeTypeByExtension(String extension) {
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
	
	static String getMimeTypeByNameAndExtension(String nameAndExtension) {
		if(StringHelper.isBlank(nameAndExtension))
			return null;
		return getMimeTypeByExtension(getExtension(nameAndExtension));
	}
	
	static String concatenateNameAndExtension(String name,String extension) {
		if(StringHelper.isBlank(name) && StringHelper.isBlank(extension))
			return null;
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(StringHelper.isNotBlank(extension))
			nameAndExtension += "."+extension;
		return nameAndExtension;
	}
	
	static byte[] getBytes(java.io.File file) {
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
	
	static Collection<Path> getPaths(Collection<String> directories,String nameRegularExpression,Boolean isDirectoryGettable,Boolean isFileGettable,Integer maximalNumberOfPath) {
		if(CollectionHelper.isEmpty(directories))
			return null;
		final List<String> directoriesFinal = new ArrayList<>(directories);
		final Boolean isDirectoryGettableFinal = ValueHelper.defaultToIfNull(isDirectoryGettable,Boolean.TRUE);
		final Boolean isFileGettableFinal = ValueHelper.defaultToIfNull(isFileGettable,Boolean.TRUE);
		Collection<Path> paths = new LinkedHashSet<>();
		for(Integer index = 0; index < directoriesFinal.size();) {
			Path path = java.nio.file.Paths.get(directoriesFinal.remove(0));
			if(path.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || path.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal))
				if(!directories.contains(path.toFile().toString())) {
					if(addAndReturn(paths, path, nameRegularExpression, maximalNumberOfPath))
						return paths;
				}
			
			//filter children
			if(path.toFile().isDirectory()) {
				try {
					DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path,x -> x.toFile().isDirectory() || x.toFile().isFile());
					for(Path child : directoryStream) {
						if(child.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || child.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal)) {
							if(addAndReturn(paths, child, nameRegularExpression, maximalNumberOfPath))
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
	
	static Boolean addAndReturn(Collection<Path> paths,Path path,String nameRegularExpression,Integer maximalNumberOfPath) {
		if(nameRegularExpression == null || RegularExpressionHelper.match(path.toFile().getName(), nameRegularExpression))
			paths.add(path);
		if(maximalNumberOfPath != null && maximalNumberOfPath == paths.size())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	static Collection<Path> getPaths(Strings directories,String nameRegularExpression,Boolean isDirectoryGettable,Boolean isFileGettable,Integer maximalNumberOfPath) {
		if(CollectionHelper.isEmpty(directories))
			return null;
		return getPaths(directories.get(), nameRegularExpression, isDirectoryGettable, isFileGettable, maximalNumberOfPath);
	}
	
	static Files get(Collection<Path> paths,Intervals sizeIntervals) {	
		if(CollectionHelper.isEmpty(paths))
			return null;
		if(CollectionHelper.isEmpty(sizeIntervals))
			sizeIntervals = null;
		Files files = null;
		for(Path path : paths) {
			java.io.File javaFile = path.toFile();
			if(sizeIntervals!=null && !sizeIntervals.contains(javaFile.length()))
				continue;
			if(files == null)
				files = DependencyInjection.inject(Files.class);
			files.add(build(path.toUri().toString(), path.getParent().toString(), path.getFileName().toString(), null, null, null, null, null, path.toFile().length(), null, null));		
		}
		return files;
	}
	
	static File build(String uniformResourceLocator,String path,String name,String extension,String mimeType,byte[] bytes,InputStream inputStream,Class<?> klass,Long size,String checksum,Boolean isChecksumComputable) {
		File file = DependencyInjection.inject(File.class);
		file.setUniformResourceLocator(uniformResourceLocator);
		if(StringHelper.isNotBlank(path))
			path = StringHelper.addToEndIfDoesNotEndWith(path, "/");
		file.setPath(path);
		file.setName(getName(name));
		if(StringHelper.isBlank(extension))
			extension = getExtension(name);
		file.setExtension(extension);		
		if(StringHelper.isBlank(mimeType))
			mimeType = getMimeTypeByExtension(file.getExtension());
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
	
	static File build(FileAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		return build(parameter.getUniformResourceLocator() == null ? null : UniformResourceIdentifierHelper.build(parameter.getUniformResourceLocator())
				, parameter.getPath(), parameter.getName(), parameter.getExtension(), parameter.getMimeType(), parameter.getBytes(), parameter.getInputStream()
				, parameter.getClassToGetResourceAsStream(), parameter.getSize(), parameter.getChecksum(), parameter.getIsChecksumComputable());
	}
	
	static String computeChecksum(byte[] bytes) {
		return ByteHelper.buildMessageDigest(bytes);
	}
	
	static void computeChecksum(Collection<File> files) {
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
							bytes = getBytesByUniformResourceIdentifier(file.getUniformResourceLocator());
						file.setChecksum(computeChecksum(bytes));	
					}
				});
			}
		}
		if(runnables == null)
			return;
		RunnableHelper.run(runnables, "compute checksum");
	}
	
	static void computeChecksum(Files files) {
		if(CollectionHelper.isEmpty(files))
			return;
		computeChecksum(files.get());
	}
	
	static void computeChecksum(File...files) {
		if(ArrayHelper.isEmpty(files))
			return;
		computeChecksum(List.of(files));
	}
	
	static String extractText(InputStream inputStream) {
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

	static String extractText(java.io.File file) {
		try {
			return extractText(new BufferedInputStream(new FileInputStream(file)));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static java.io.File getJavaFileByUniformResourceIdentifier(String uniformResourceIdentifier) {
		return new java.io.File(URI.create(uniformResourceIdentifier));
	}
	
	static InputStream getInputStreamByUniformResourceIdentifier(String uniformResourceIdentifier) {
		try {
			return new BufferedInputStream(new FileInputStream(getJavaFileByUniformResourceIdentifier(uniformResourceIdentifier)));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static byte[] getBytesByUniformResourceIdentifier(String uniformResourceIdentifier) {
		try {
			return IOUtils.toByteArray(getInputStreamByUniformResourceIdentifier(uniformResourceIdentifier));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static Collection<URI> getPathsURIs(Collection<Path> paths) {
		if(CollectionHelper.isEmpty(paths))
			return null;
		Collection<URI> uris = new ArrayList<>();
		for(Path path : paths)
			uris.add(path.toFile().toURI());
		return uris;
	}
	
	static void removePathsByUniformResourceIdentifiers(Collection<Path> paths,Collection<String> uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(paths) || CollectionHelper.isEmpty(uniformResourceIdentifiers))
			return;		
		Collection<URI> uris = UniformResourceIdentifierHelper.getURIs(uniformResourceIdentifiers);
		Collection<Path> elements = null;
		for(Path path : paths)
			if(uris.contains(path.toFile().toURI())) {
				if(elements == null)
					elements = new ArrayList<>();
				elements.add(path);
			}
		if(elements != null)
			paths.removeAll(elements);
		
	}

	static void removePathsByUniformResourceIdentifiers(Collection<Path> paths,String... uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(paths) || ArrayHelper.isEmpty(uniformResourceIdentifiers))
			return;		
		removePathsByUniformResourceIdentifiers(paths,List.of(uniformResourceIdentifiers));
	}

	static void removePathsByUniformResourceIdentifiers(Collection<Path> paths,Strings uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(paths) || CollectionHelper.isEmpty(uniformResourceIdentifiers))
			return;		
		removePathsByUniformResourceIdentifiers(paths,uniformResourceIdentifiers.get());
	}
}
