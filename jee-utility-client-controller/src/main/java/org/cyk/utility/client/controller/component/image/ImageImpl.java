package org.cyk.utility.client.controller.component.image;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.file.File;

public class ImageImpl extends AbstractVisibleComponentImpl implements Image,Serializable {
	private static final long serialVersionUID = 1L;

	private File file;
	private Integer width,height;
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public Image setFile(File file) {
		this.file = file;
		return this;
	}

	@Override
	public Image setWidth(Integer width) {
		this.width = width;
		return this;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public Image setHeight(Integer height) {
		this.height = height;
		return this;
	}

	@Override
	public Integer getHeight() {
		return height;
	}

}
