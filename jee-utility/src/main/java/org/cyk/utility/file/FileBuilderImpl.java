package org.cyk.utility.file;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class FileBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<File> implements FileBuilder,Serializable{
	private static final long serialVersionUID = 1L;

	private InputStream inputStream;
	private Class<?> clazz;
	private String path,name,uniformResourceLocator,mimeType,extension;
	private byte[] bytes;
	private Long size;
	
	@Override
 	protected File __execute__() throws Exception {
		File file = __inject__(File.class);
		String uniformResourceLocator = getUniformResourceLocator();
		file.setUniformResourceLocator(uniformResourceLocator);
		
		String path = getPath();
		file.setPath(path);
		
		String name = getName();
		file.setName(__inject__(FileHelper.class).getName(name));
		
		String extension = getExtension();
		if(__injectStringHelper__().isBlank(extension))
			extension = __inject__(FileHelper.class).getExtension(name);
		file.setExtension(extension);
		
		String mimeType = getMimeType();
		if(__injectStringHelper__().isBlank(mimeType))
			mimeType = __inject__(FileHelper.class).getMimeTypeByExtension(file.getExtension());
		file.setMimeType(mimeType);
		
		byte[] bytes = getBytes();
		file.setBytes(bytes);
		
		if(file.getBytes() == null) {
			InputStream inputStream = getInputStream();
			if(inputStream == null) {
				Class<?> clazz = getClazz();
				if(clazz!=null)
					inputStream = clazz.getResourceAsStream(name);
			}
			if(inputStream!=null)
				file.setBytes(IOUtils.toByteArray(inputStream));
		}
		
		if(file.getSize() == null) {
			if(file.getBytes()!=null)
				file.setSize(new Long(file.getBytes().length));
		}
		
		return file;
	}
	
	@Override
	public InputStream getInputStream() {
		return inputStream;
	}
	
	@Override
	public FileBuilder setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public FileBuilder setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FileBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public String getUniformResourceLocator() {
		return uniformResourceLocator;
	}
	@Override
	public FileBuilder setUniformResourceLocator(String uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}
	@Override
	public FileBuilder setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}
	
	@Override
	public String getMimeType() {
		return mimeType;
	}
	@Override
	public FileBuilder setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}
	
	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public FileBuilder setPath(String path) {
		this.path = path;
		return this;
	}
	
	@Override
	public String getExtension() {
		return extension;
	}
	
	@Override
	public FileBuilder setExtension(String extension) {
		this.extension = extension;
		return this;
	}
	
	@Override
	public Long getSize() {
		return size;
	}
	
	@Override
	public FileBuilder setSize(Long size) {
		this.size = size;
		return this;
	}
}
