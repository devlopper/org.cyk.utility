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
		
		FileBuilder fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("imageStreamed.png");
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("imageEmbedded.png");
		fileBuilder.setIsEmbeddable(Boolean.TRUE);
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("textStreamed.txt");
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("textEmbedded.txt");
		fileBuilder.setIsEmbeddable(Boolean.TRUE);
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("pdf01.pdf");
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("audio01.mp3");
		viewBuilder.addComponentBuilder(fileBuilder);
		
		fileBuilder = __inject__(FileBuilder.class);
		fileBuilder.getValue(Boolean.TRUE).setClazz(getClass()).setName("video01.mp4");
		viewBuilder.addComponentBuilder(fileBuilder);
		
		return viewBuilder;
	}
	
}
