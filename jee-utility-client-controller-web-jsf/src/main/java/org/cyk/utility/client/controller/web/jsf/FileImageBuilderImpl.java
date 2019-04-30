package org.cyk.utility.client.controller.web.jsf;
import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.JavaServerFaces;
import org.cyk.utility.client.controller.component.file.FileBuilder;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.string.StringHelper;

@JavaServerFaces
public class FileImageBuilderImpl extends org.cyk.utility.client.controller.component.file.AbstractFileImageBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(FileImage fileImage) {
		FileBuilder file = getFile();
		if(file!=null && file.getValue()!=null) {
			String uniformResourceLocator = file.getValue().getUniformResourceLocator();
			if(__inject__(StringHelper.class).isBlank(uniformResourceLocator)) {
				//Is it a resource file ?
				String library = file.getValue().getPath();
				if(__inject__(StringHelper.class).isNotBlank(library)) {
					String contract = getResourcesFolderName();
					if(__inject__(StringHelper.class).isNotBlank(contract)) {
						file.getValue().setUniformResourceLocator(Constant.formatResourceRelativeUrl(getRequest(), file.getValue().getName(), library, contract));
					}	
				}	
			}	
		}
		super.__execute__(fileImage);
	}
	
}
