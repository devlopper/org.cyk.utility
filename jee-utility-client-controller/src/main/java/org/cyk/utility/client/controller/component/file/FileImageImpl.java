package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class FileImageImpl extends AbstractVisibleComponentImpl implements FileImage,Serializable {
	private static final long serialVersionUID = 1L;

	private File file;
	private Integer width,height;
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public FileImage setFile(File file) {
		this.file = file;
		return this;
	}

	@Override
	public FileImage setWidth(Integer width) {
		this.width = width;
		return this;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public FileImage setHeight(Integer height) {
		this.height = height;
		return this;
	}

	@Override
	public Integer getHeight() {
		return height;
	}

}
