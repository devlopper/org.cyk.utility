package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public abstract class AbstractFileImageBuilderImpl extends AbstractVisibleComponentBuilderImpl<FileImage> implements FileImageBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private FileBuilder file;
	private Integer width,height;
	private String resourcesFolderName;
	
	@Override
	protected void __execute__(FileImage fileImage) {
		super.__execute__(fileImage);
		FileBuilder file = getFile();
		if(file != null) {
			if(file.getContext()==null)
				file.setContext(getContext());
			if(file.getUniformResourceLocatorMap()==null)
				file.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			if(file.getRequest()==null)
				file.setRequest(getRequest());
			fileImage.setFile(file.execute().getOutput());
		}
		
		Integer width = getWidth();
		fileImage.setWidth(width);
		
		Integer height = getHeight();
		fileImage.setHeight(height);
	}
	
	@Override
	protected Class<FileImage> __getComponentClass__() {
		return FileImage.class;
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
	public String getResourcesFolderName() {
		return resourcesFolderName;
	}
	
	@Override
	public FileImageBuilder setResourcesFolderName(String resourcesFolderName) {
		this.resourcesFolderName = resourcesFolderName;
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
