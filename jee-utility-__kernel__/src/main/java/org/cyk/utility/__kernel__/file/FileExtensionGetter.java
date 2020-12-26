package org.cyk.utility.__kernel__.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface FileExtensionGetter {

	String get(Arguments arguments);
	String get(byte[] bytes);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements FileExtensionGetter,Serializable{		
		@Override
		public String get(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			if(arguments.bytes != null)
				return getFromBytes(arguments.bytes);
			if(StringHelper.isNotBlank(arguments.name))
				return getFromName(arguments.name);
			return null;
		}
		
		@Override
		public String get(byte[] bytes) {
			return get(new Arguments().setBytes(bytes));
		}
		
		/**/
		
		protected String getFromBytes(byte[] bytes) {
			if(bytes == null || bytes.length == 0)
				return null;
			try {
				String extension = URLConnection.guessContentTypeFromStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
				if(StringUtils.contains(extension, "/"))
					return StringUtils.substringAfter(extension, "/");
				if(StringHelper.isBlank(extension)) {
					//System.out.println("FileExtensionGetter.AbstractImpl.getFromBytes() : "+ (bytes[0])+(bytes[1])+(bytes[2])+(bytes[3]));
					if( ((char)bytes[0]) == '%' && ((char)bytes[1]) == 'P' && ((char)bytes[2]) == 'D' && ((char)bytes[3]) == 'F')
						extension = "pdf";
				}
				return extension;
			} catch (IOException exception) {
				LogHelper.log(exception, getClass());
				return null;
			}
		}
		
		protected String getFromName(String name) {
			return StringUtils.contains(name, ".") ? StringUtils.substringAfter(name, ".") : name;
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private byte[] bytes;
		private String name;
	}
	
	static FileExtensionGetter getInstance() {
		return Helper.getInstance(FileExtensionGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}
