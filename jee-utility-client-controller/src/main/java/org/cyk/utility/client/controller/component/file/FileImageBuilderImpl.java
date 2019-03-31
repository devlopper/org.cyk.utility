package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class FileImageBuilderImpl extends AbstractVisibleComponentBuilderImpl<FileImage> implements FileImageBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private FileBuilder file;
	private Integer width,height;
	
	@Override
	protected void __execute__(FileImage image) {
		super.__execute__(image);
		FileBuilder file = getFile();
		if(file != null) {
			if(file.getContext()==null)
				file.setContext(getContext());
			if(file.getUniformResourceLocatorMap()==null)
				file.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			if(file.getRequest()==null)
				file.setRequest(getRequest());
			image.setFile(file.execute().getOutput());
		}
		
		Integer width = getWidth();
		image.setWidth(width);
		
		Integer height = getHeight();
		image.setHeight(height);
	}
	
	@Override
	public FileBuilder getFile() {
		return file;
	}
	
	@Override
	public FileBuilder getFile(Boolean injectIfNull) {
		return (FileBuilder) __getInjectIfNull__(FIELD_FILE, injectIfNull);
	}

	@Override
	public FileImageBuilder setFile(FileBuilder file) {
		this.file = file;
		return this;
	}
	
	@Override
	public Integer getWidth() {
		return width;
	}
	
	@Override
	public FileImageBuilder setWidth(Integer width) {
		this.width = width;
		return this;
	}

	@Override
	public Integer getHeight() {
		return height;
	}
	
	@Override
	public FileImageBuilder setHeight(Integer height) {
		this.height = height;
		return this;
	}
	
	/**/
	
	private static final String FIELD_FILE = "file";
}
