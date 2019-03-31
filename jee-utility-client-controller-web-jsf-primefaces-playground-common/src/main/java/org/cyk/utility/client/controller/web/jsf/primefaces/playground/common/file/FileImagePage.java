package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FileImagePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "File Image";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		FileImageBuilder fileImageBuilder = __inject__(FileImageBuilder.class);
		fileImageBuilder.getFile(Boolean.TRUE).setValueClazz(getClass()).setValueName("imageStreamed.png");
		viewBuilder.addComponentBuilder(fileImageBuilder);
		
		fileImageBuilder = __inject__(FileImageBuilder.class);
		fileImageBuilder.getFile(Boolean.TRUE).setValueClazz(getClass()).setValueName("imageEmbedded.png").setIsEmbeddable(Boolean.TRUE);
		viewBuilder.addComponentBuilder(fileImageBuilder);
		
		fileImageBuilder = __inject__(FileImageBuilder.class);
		fileImageBuilder.getFile(Boolean.TRUE).setValuePath("image").setValueName("icon.png");
		viewBuilder.addComponentBuilder(fileImageBuilder);
		
		return viewBuilder;
	}
	
}
