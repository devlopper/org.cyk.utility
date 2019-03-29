package org.cyk.utility.client.controller.component.image;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.file.FileBuilder;

public class ImageBuilderImpl extends AbstractVisibleComponentBuilderImpl<Image> implements ImageBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private FileBuilder file;
	private Integer width,height;
	
	@Override
	protected void __execute__(Image image) {
		super.__execute__(image);
		FileBuilder file = getFile();
		if(file != null)
			image.setFile(file.execute().getOutput());
		
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
	public ImageBuilder setFile(FileBuilder file) {
		this.file = file;
		return this;
	}
	
	@Override
	public Integer getWidth() {
		return width;
	}
	
	@Override
	public ImageBuilder setWidth(Integer width) {
		this.width = width;
		return this;
	}

	@Override
	public Integer getHeight() {
		return height;
	}
	
	@Override
	public ImageBuilder setHeight(Integer height) {
		this.height = height;
		return this;
	}
	
	/**/
	
	private static final String FIELD_FILE = "file";
}
