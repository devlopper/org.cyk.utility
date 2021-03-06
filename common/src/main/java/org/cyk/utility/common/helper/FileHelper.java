package org.cyk.utility.common.helper;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class FileHelper extends AbstractHelper implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private static final String NAME_EXTENSION_SEPARATOR = Constant.CHARACTER_DOT.toString();
	private static FileHelper INSTANCE;
	
	public static FileHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FileHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getText(byte[] bytes,Boolean removeEmptyLine,Boolean trim){
		if(bytes == null)
			return null;
		org.apache.tika.sax.BodyContentHandler bodyContentHandler = new org.apache.tika.sax.BodyContentHandler();
		org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
		org.apache.tika.parser.ParseContext parseContext=new org.apache.tika.parser.ParseContext();  
		org.apache.tika.parser.Parser parser = new org.apache.tika.parser.AutoDetectParser();
		try {
			parser.parse(new ByteArrayInputStream(bytes), bodyContentHandler, metadata,parseContext);
			String string =  bodyContentHandler.toString();
			if(Boolean.TRUE.equals(removeEmptyLine))
				string = StringHelper.getInstance().removeEmptyLine(string);
			if(Boolean.TRUE.equals(trim))
				string = StringHelper.getInstance().trim(string);
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getText(byte[] bytes){
		return getText(bytes, Boolean.FALSE, Boolean.FALSE);
	}
	
	public String concatenate(String name,String extension){
		return name+NAME_EXTENSION_SEPARATOR+extension;
	}
	
	public byte[] getBytes(java.io.File file){
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public java.io.InputStream getInputStream(Class<?> aClass,String fileName){
		try {
			return aClass == null ? new FileInputStream(fileName) : aClass.getResourceAsStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public java.io.InputStream getInputStream(String fileName){
		return getInputStream(null, fileName);
	}
	
	public byte[] getBytes(java.io.InputStream inputStream){
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public byte[] getBytes(Class<?> aClass,String fileName){
		return getBytes(getInputStream(aClass, fileName));
	}
	
	public byte[] getBytes(String fileName){
		return getBytes(getInputStream(fileName));
	}
	
	
	public String getPath(String fileName){
		return FilenameUtils.getFullPath(fileName);
	}
	
	public String getName(String name){
		return FilenameUtils.getBaseName(name);
	}
	
	public String getExtension(String name){
		return FilenameUtils.getExtension(name);
	}
	
	
	public String getMime(String extension){
    	if(StringUtils.isBlank(extension))
    		return null;
        String mime = null;
        String fileName = NAME_EXTENSION_SEPARATOR+extension;
        try {
            mime = Files.probeContentType(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	if(StringUtils.isBlank(mime) || mime.equalsIgnoreCase(Mime.APPLICATION_OCTET_STREAM))
    		mime = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
    	if(StringUtils.isBlank(mime) || mime.equalsIgnoreCase(Mime.APPLICATION_OCTET_STREAM))
    		mime = URLConnection.guessContentTypeFromName(fileName);
    	
    	return mime;
    }
	
	public File get(Class<?> aClass,String fileName){
		File file = new File();
		file.setBytes(getBytes(aClass, fileName));
		file.setName(getName(fileName));
		file.setExtension(getExtension(fileName));
		file.setMime(getMime(file.getExtension()));
		return file;
	}
	
	public File get(String fileName){
		return get(null, fileName);
	}
	
	public List<String> readLines(String fileName){
		try {
			return IOUtils.readLines(getClass().getResourceAsStream(fileName));
		} catch (IOException e) {
			logThrowable(e);
			return null;
		}
	}
	
	public java.io.File createTemporary(byte[] bytes){
		java.io.File destination = null;
		try {
			destination = java.io.File.createTempFile(RandomHelper.getInstance().getAlphabetic(5), System.currentTimeMillis()+Constant.EMPTY_STRING);
			if(destination!=null && destination.exists()){
				destination.deleteOnExit();
				IOUtils.write(bytes, new FileOutputStream(destination));
			}
			else
				logError("Temporary file cannot be created.");
		} catch (IOException e) {
			logThrowable(e);
		}
		return destination;
	}
	
	public static Listener getListener(){
		return ClassHelper.getInstance().instanciateOne(Listener.class);
	}
	
	/**/
	
	public interface Read<RESULT> extends Action<java.io.File, RESULT> {
		
		public static class Adapter<RESULT> extends Action.Adapter.Default<java.io.File, RESULT> implements Read<RESULT>,Serializable {
			private static final long serialVersionUID = 1L;
		
			public Adapter(java.io.File file) {
				super("read", java.io.File.class, file, null);
			}
			
			/**/
			
			public static class Default<RESULT> extends Read.Adapter<RESULT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(java.io.File file) {
					super(file);
				}

			}
			
		}
		
	}

	/**/
	
	public interface NameTransformer extends StringHelper.Transformer {
		
		public static class Adapter extends StringHelper.Transformer.Adapter.Default implements NameTransformer , Serializable {
			private static final long serialVersionUID = 1L;

			/**/
			
			public static class Default extends NameTransformer.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static String ILLEGAL_CHARACTERS = "\\/.,;:!?";
				public static String ILLEGAL_CHARACTERS_REPLACEMENT = StringUtils.repeat(Constant.CHARACTER_UNDESCORE.charValue(), ILLEGAL_CHARACTERS.length());
				
				public Default() {
					super();
					for(Integer index = 0 ; index < ILLEGAL_CHARACTERS.length() ; index++){
						addSequenceReplacement(Character.toString(ILLEGAL_CHARACTERS.charAt(index)), Character.toString(ILLEGAL_CHARACTERS_REPLACEMENT.charAt(index)));
					}
					/*addSequenceReplacement("\\", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement("/", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement(".", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement(",", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement(";", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement(":", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement("!", Constant.CHARACTER_UNDESCORE.toString());
					addSequenceReplacement("?", Constant.CHARACTER_UNDESCORE.toString());*/
				}
			}	
		}
	}

	/**/
	
	public static interface Mime {
    	String IMAGE = "image";
    	String TEXT = "text";
    	String APPLICATION_OCTET_STREAM = "application/octet-stream";
    }
	
	@Getter @Setter @Accessors(chain=true)
	public static class File extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Object identifier;
		private String name,extension,mime;
		private byte[] bytes;
		
		@Override
		public String toString() {
			Collection<String> tokens = new ArrayList<>();
			if(identifier!=null)
				tokens.add("identifier = "+identifier);
			if(StringHelper.getInstance().isNotBlank(name))
				tokens.add("name = "+name);
			if(StringHelper.getInstance().isNotBlank(extension))
				tokens.add("extension = "+extension);
			if(StringHelper.getInstance().isNotBlank(mime))
				tokens.add("mime = "+mime);
			if(bytes!=null)
				tokens.add("bytes = "+bytes.length);
			return CollectionHelper.getInstance().isEmpty(tokens) ? super.toString() : StringHelper.getInstance().concatenate(tokens, Constant.CHARACTER_COMA);
		}
	
		public static final String FIELD_NAME = "name";
		public static final String FIELD_BYTES = "bytes";
		public static final String FIELD_MIME = "mime";
		public static final String FIELD_EXTENSION = "extension";
		
	}

	public static interface Listener {
		
		Class<?> getModelClass();
		byte[] getBytes(Object file);
		String getName(Object file);
		String getExtension(Object file);
		String getMime(Object file);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public static Class<?> FILE_CLASS = File.class;
				
				@Override
				public Class<?> getModelClass() {
					return FILE_CLASS;
				}
				
				@Override
				public byte[] getBytes(Object file) {
					return (byte[]) FieldHelper.getInstance().read(file, File.FIELD_BYTES);
				}

				@Override
				public String getName(Object file) {
					return (String) FieldHelper.getInstance().read(file, File.FIELD_NAME);
				}

				@Override
				public String getExtension(Object file) {
					return (String) FieldHelper.getInstance().read(file, File.FIELD_EXTENSION);
				}

				@Override
				public String getMime(Object file) {
					return (String) FieldHelper.getInstance().read(file, File.FIELD_MIME);
				}
				
			}
			
			@Override
			public Class<?> getModelClass() {
				return null;
			}

			@Override
			public byte[] getBytes(Object file) {
				return null;
			}

			@Override
			public String getName(Object file) {
				return null;
			}

			@Override
			public String getExtension(Object file) {
				return null;
			}

			@Override
			public String getMime(Object file) {
				return null;
			}
		}
		
	}
}
	