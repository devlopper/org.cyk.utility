package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class FileImpl extends AbstractVisibleComponentImpl implements File,Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.file.File value;
	private Boolean isEmbedded;
	
	@Override
	public org.cyk.utility.file.File getValue() {
		return value;
	}

	@Override
	public File setValue(org.cyk.utility.file.File value) {
		this.value = value;
		return this;
	} 

	@Override
	public Boolean getIsEmbedded() {
		return isEmbedded;
	}

	@Override
	public File setIsEmbedded(Boolean isEmbedded) {
		this.isEmbedded = isEmbedded;
		return this;
	}

}
