package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.file.FileBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FilePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "File";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("imageStreamed.png"));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("imageEmbedded.png").setIsEmbeddable(Boolean.TRUE));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValuePath("image").setValueName("icon.png"));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("textStreamed.txt"));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("textEmbedded.txt").setIsEmbeddable(Boolean.TRUE));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("pdf01.pdf"));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("audio01.mp3"));
		
		viewBuilder.addComponentBuilder(__inject__(FileBuilder.class).setValueClazz(getClass()).setValueName("video01.mp4"));
		
		return viewBuilder;
	}
	
}
