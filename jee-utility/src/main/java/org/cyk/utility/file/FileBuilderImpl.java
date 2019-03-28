package org.cyk.utility.file;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class FileBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<File> implements FileBuilder,Serializable{
	private static final long serialVersionUID = 1L;

	private InputStream inputStream;
	private Class<?> clazz;
	private String name;
	
	@Override
	protected File __execute__() throws Exception {
		File file = __inject__(File.class);
		String name = getName();
		file.setName(FilenameUtils.getBaseName(name));
		file.setExtension(FilenameUtils.getExtension(name));
		file.setMimeType(__inject__(MimeTypeGetter.class).setExtension(file.getExtension()).execute().getOutput());
		
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

}
