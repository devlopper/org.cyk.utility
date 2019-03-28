package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.output;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputFile;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.file.File;
import org.cyk.utility.file.FileBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class OutputFilePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Output File";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		form.getData().setImageStreamed(__inject__(FileBuilder.class).setClazz(getClass()).setName("image01.png").execute().getOutput());
		form.getData().setImageInSession(__inject__(FileBuilder.class).setName("image02.png").execute().getOutput().setIdentifier(729));
		
		__inject__(SessionAttributeSetter.class)
			.setAttribute(form.getData().getImageInSession().getIdentifier())
			.setValue(__inject__(FileBuilder.class).setClazz(getClass()).setName("image02.png").execute().getOutput())
			.execute();
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		//viewBuilder.setRequest(__getRequest__());
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageStreamed");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInSession");
		//viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInDatabase");
		//viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInFolder");
		
		//ComponentBuilder<?> componentBuilder = viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "file03");
		//componentBuilder.setRequest(__getRequest__());
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputFile
		private File imageStreamed;
		
		@Input @InputFile
		private File imageInSession;
		
		@Input @InputFile
		private File imageInDatabase;
		
		@Input @InputFile
		private File imageInFolder;
		
		@Input @InputFile
		private File text;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
