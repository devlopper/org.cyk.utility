package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.apache.commons.io.FilenameUtils;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.input.AbstractInputFileImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.__kernel__.file.File;
import org.primefaces.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;

@Primefaces
public class InputFileImpl extends AbstractInputFileImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private UploadedFile uploadedFile;
	
	@Override
	public InputOutput<File> setFieldValueFromValue() {		
		UploadedFile uploadedFile = getUploadedFile();
		if(uploadedFile!=null) {
			File file = getValue();
			if(file == null)
				setValue(file = __inject__(File.class));
			file.setName(FilenameUtils.getBaseName(uploadedFile.getFileName())).setExtension(FilenameUtils.getExtension(uploadedFile.getFileName()))
				.setMimeType(uploadedFile.getContentType()).setBytes(uploadedFile.getContents()).setSize(uploadedFile.getSize());
		}
		return super.setFieldValueFromValue();
	}
	
}
