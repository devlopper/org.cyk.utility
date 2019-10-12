package org.cyk.utility.client.controller.web.jsf;
import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.JavaServerFaces;
import org.cyk.utility.client.controller.component.file.FileImage;

@JavaServerFaces
public class FileImageBuilderImpl extends org.cyk.utility.client.controller.component.file.AbstractFileImageBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(FileImage fileImage) {
		/*
		FileBuilder file = getFile();
		if(file!=null && file.getValue()!=null) {
			String uniformResourceLocator = file.getValue().getUniformResourceLocator();
			if(StringHelper.isBlank(uniformResourceLocator)) {
				//Is it a resource file ?
				String library = file.getValue().getPath();
				if(StringHelper.isNotBlank(library)) {
					String contract = getResourcesFolderName();
					if(StringHelper.isNotBlank(contract)) {
						file.getValue().setUniformResourceLocator(Constant.formatResourceRelativeUrl(getRequest(), file.getValue().getName(), library, contract));
					}	
				}	
			}	
		}
		*/
		super.__execute__(fileImage);
	}
	
}
