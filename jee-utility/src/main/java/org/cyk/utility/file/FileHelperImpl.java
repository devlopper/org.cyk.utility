package org.cyk.utility.file;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.cyk.utility.__kernel__.constant.ConstantSeparator;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.number.Intervals;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.value.ValueHelperImpl;
import org.xml.sax.SAXException;

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
		if(__inject__(StringHelper.class).isNotBlank(extension))
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
		return StringHelperImpl.__isBlank__(string) ? null : StringUtils.defaultIfBlank(FilenameUtils.getBaseName(string),null);
	}

	public static String __getExtension__(String string) {
		return StringHelperImpl.__isBlank__(string) ? null : StringUtils.defaultIfBlank(FilenameUtils.getExtension(string),null);
	}

	public static String __getMimeTypeByExtension__(String extension) {
		if(StringHelperImpl.__isBlank__(extension))
			return null;
		String mime = null;
        String fileName = ConstantSeparator.FILE_NAME_EXTENSION+extension;        
        try {
            mime = java.nio.file.Files.probeContentType(java.nio.file.Paths.get(fileName));
        } catch (IOException e) {}        
        if(__inject__(StringHelper.class).isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        if(__inject__(StringHelper.class).isBlank(mime) || mime.equalsIgnoreCase(MediaType.APPLICATION_OCTET_STREAM))
        	mime = URLConnection.guessContentTypeFromName(fileName);        
        return mime;
	}
	
	public static String __getMimeTypeByNameAndExtension__(String nameAndExtension) {
		if(StringHelperImpl.__isBlank__(nameAndExtension))
			return null;
		return __getMimeTypeByExtension__(__getExtension__(nameAndExtension));
	}
	
	public static String __concatenateNameAndExtension__(String name, String extension) {
		if(StringHelperImpl.__isBlank__(name) && StringHelperImpl.__isBlank__(name))
			return null;
		String nameAndExtension = StringUtils.defaultIfBlank(name,"");
		if(__inject__(StringHelper.class).isNotBlank(extension))
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
	
	public static Collection<Path> __getPaths__(List<String> directories,Boolean isDirectoryGettable,Boolean isFileGettable,Integer maximalNumberOfPath) {
		if(CollectionHelperImpl.__isEmpty__(directories))
			return null;
		final List<String> directoriesFinal = new ArrayList<>(directories);
		final Boolean isDirectoryGettableFinal = ValueHelperImpl.__defaultToIfNull__(isDirectoryGettable,Boolean.TRUE);
		final Boolean isFileGettableFinal = ValueHelperImpl.__defaultToIfNull__(isFileGettable,Boolean.TRUE);
		Collection<Path> pathCollection = new LinkedHashSet<Path>();
		for(Integer index = 0; index < directoriesFinal.size();) {
			Path path = java.nio.file.Paths.get(directoriesFinal.remove(0));
			if(path.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || path.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal))
				if(!directories.contains(path.toFile().toString())) {
					pathCollection.add(path);
					if(maximalNumberOfPath != null && maximalNumberOfPath == pathCollection.size())
						return pathCollection;
				}
			
			//filter children
			if(path.toFile().isDirectory()) {
				try {
					DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(path,x -> x.toFile().isDirectory() || x.toFile().isFile());
					for(Path child : directoryStream) {
						if(child.toFile().isDirectory() && Boolean.TRUE.equals(isDirectoryGettableFinal) || child.toFile().isFile() && Boolean.TRUE.equals(isFileGettableFinal)) {
							pathCollection.add(child);
							if(maximalNumberOfPath != null && maximalNumberOfPath == pathCollection.size())
								return pathCollection;
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
		return pathCollection;
	}
	
	public static Files __get__(Collection<Path> paths,Collection<String> extensions,Intervals sizeIntervals) {	
		if(CollectionHelperImpl.__isEmpty__(paths))
			return null;
		if(CollectionHelperImpl.__isEmpty__(extensions))
			extensions = null;
		if(CollectionHelperImpl.__isEmpty__(sizeIntervals))
			sizeIntervals = null;
		Files files = null;
		for(Path path : paths) {
			java.io.File javaFile = path.toFile();
			String extension = __getExtension__(javaFile.getName()).toLowerCase();
			if(extensions!=null && !extensions.contains(extension))
				continue;			
			if(sizeIntervals!=null && !sizeIntervals.contains(javaFile.length()))
				continue;
			if(files == null)
				files = __inject__(Files.class);
			files.add(__build__(path.toUri().toString(), path.getParent().toString(), path.getFileName().toString(), null, null, null, null, null, path.toFile().length(), null, null));		
		}
		
		/*
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
		
		*/
		return files;
	}
	
	public static File __build__(String uniformResourceLocator,String path,String name,String extension,String mimeType,byte[] bytes,InputStream inputStream,Class<?> klass,Long size,String checksum,Boolean isChecksumComputable) {
		File file = __inject__(File.class);
		file.setUniformResourceLocator(uniformResourceLocator);
		if(StringHelperImpl.__isNotBlank__(path))
			path = StringHelperImpl.__addToEndIfDoesNotEndWith__(path, "/");
		file.setPath(path);
		file.setName(__getName__(name));
		if(StringHelperImpl.__isBlank__(extension))
			extension = __getExtension__(name);
		file.setExtension(extension);		
		if(StringHelperImpl.__isBlank__(mimeType))
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
				size = new Long(file.getBytes().length);
		}
		file.setSize(size);
		if(StringHelperImpl.__isBlank__(checksum)) {
			if(Boolean.TRUE.equals(isChecksumComputable)) {
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
		if(StringHelperImpl.__isNotBlank__(checksum))
			file.setChecksum(new String(checksum));		
		return file;
	}
	
	public static String __extractText__(InputStream inputStream) {
		BodyContentHandler handler = new BodyContentHandler();
	    AutoDetectParser parser = new AutoDetectParser();
	    Metadata metadata = new Metadata();
	    try {
			parser.parse(inputStream, handler, metadata);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	    return handler.toString();
	    
	}
	
	public static String __extractText__(java.io.File file) {
		try {
			return __extractText__(new FileInputStream(file));
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
	
}
