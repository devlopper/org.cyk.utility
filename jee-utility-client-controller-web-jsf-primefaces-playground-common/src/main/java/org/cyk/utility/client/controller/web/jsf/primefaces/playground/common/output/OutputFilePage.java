package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.output;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputFile;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.file.File;

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
		form.getData().setFile01(__inject__(File.class));
		form.getData().getFile01().setName("Image01");
		form.getData().getFile01().setExtension("png");
		form.getData().getFile01().setMimeType("image/png");
		form.getData().getFile01().setSize(1000l);
		try {
			form.getData().getFile01().setBytes(IOUtils.toByteArray(getClass().getResourceAsStream("image01.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		form.getData().setFile02(__inject__(File.class));
		form.getData().getFile02().setName("Text01");
		form.getData().getFile02().setExtension("txt");
		form.getData().getFile02().setMimeType("text/plain");
		form.getData().getFile02().setSize(17l);
		try {
			form.getData().getFile02().setBytes(IOUtils.toByteArray(getClass().getResourceAsStream("text01.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "file01");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "file02");
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputFile
		private File file01;
		
		@Input @InputFile
		private File file02;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
