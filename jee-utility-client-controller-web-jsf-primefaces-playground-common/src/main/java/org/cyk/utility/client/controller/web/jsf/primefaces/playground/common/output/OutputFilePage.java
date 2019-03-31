package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.output;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputFile;
import org.cyk.utility.client.controller.component.output.OutputFileBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
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
		
		form.getData().setImageStreamed(__inject__(FileBuilder.class).setClazz(getClass()).setName("imageStreamed.png").execute().getOutput());
		form.getData().setImageEmbedded(__inject__(FileBuilder.class).setClazz(getClass()).setName("imageEmbedded.png").execute().getOutput());
		form.getData().setImageInLibrary(__inject__(FileBuilder.class).setPath("image").setName("icon.png").execute().getOutput());
		
		/*form.getData().setImageInSession(setFileInSessionAttribute("image02.png",729));
		form.getData().setText(setFileInSessionAttribute("text.txt",1));
		form.getData().setAudio(setFileInSessionAttribute("audio.txt",11).setMimeType("audio/audio"));
		form.getData().setVideo(setFileInSessionAttribute("video.txt",12).setMimeType("video/video"));
		form.getData().setApplication(setFileInSessionAttribute("application.txt",13).setMimeType("application/application"));
		
		form.getData().setImageNotRenderedHere(setFileInSessionAttribute("image02.png",14));
		*/
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		OutputFileBuilder outputFileBuilder = null;
		outputFileBuilder = (OutputFileBuilder) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageStreamed");
		outputFileBuilder = (OutputFileBuilder) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageEmbedded");
		outputFileBuilder.getFile(Boolean.TRUE).setIsEmbeddable(Boolean.TRUE);
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInLibrary");
		/*//viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInDatabase");
		//viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageInFolder");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "text");
		OutputFileBuilder outputFileBuilder = (OutputFileBuilder) viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "imageNotRenderedHere");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "audio");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "video");
		viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.FALSE, "application");
		*/
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
		private File imageEmbedded;
		
		@Input @InputFile
		private File imageInLibrary;
		
		@Input @InputFile
		private File imageInFolder;
		
		@Input @InputFile
		private File imageNotRenderedHere;
		
		@Input @InputFile
		private File text;
		
		@Input @InputFile
		private File audio;
		
		@Input @InputFile
		private File video;
		
		@Input @InputFile
		private File application;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
