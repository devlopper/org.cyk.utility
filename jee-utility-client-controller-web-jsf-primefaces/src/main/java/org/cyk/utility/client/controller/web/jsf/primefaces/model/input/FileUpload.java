package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileUpload extends AbstractInput<org.primefaces.component.fileupload.FileUpload> implements Serializable {

	private String label,allowTypes,mode,invalidSizeMessage,fileLimitMessage,uploadLabel,cancelLabel,update;
	private Boolean multiple;
	private Integer sizeLimit,fileLimit;			
	
	/**/
	
	public void listenUpload(FileUploadEvent event) {
		if(listener instanceof Listener)
			((Listener)listener).listenFileUploaded(this,event);
	}
	
	/**/
	
	public static final String FIELD_LABEL = "label";
	public static final String FIELD_ALLOW_TYPES = "allowTypes";
	public static final String FIELD_MODE = "mode";
	public static final String FIELD_INVALID_SIZE_MESSAGE = "invalidSizeMessage";
	public static final String FIELD_FILE_LIMIT_MESSAGE = "fileLimitMessage";
	public static final String FIELD_UPLOAD_LABEL = "uploadLabel";
	public static final String FIELD_CANCEL_LABEL = "cancelLabel";
	public static final String FIELD_MULTIPLE = "multiple";
	public static final String FIELD_SIZE_LIMIT = "sizeLimit";
	public static final String FIELD_FILE_LIMIT = "fileLimit";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<FileUpload> implements Serializable {

		@Override
		public void configure(FileUpload fileUpload, Map<Object, Object> arguments) {
			super.configure(fileUpload, arguments);
			if(fileUpload.label == null)
				fileUpload.label = "Sélectionner un fichier";
			if(fileUpload.allowTypes == null)
				fileUpload.allowTypes = "/(\\.|\\/)(pdf|jpg|jpeg|bmp|gif|png)$/";
			if(fileUpload.mode == null)
				fileUpload.mode = "advanced";
			if(fileUpload.multiple == null)
				fileUpload.multiple = Boolean.FALSE;
			if(fileUpload.uploadLabel == null)
				fileUpload.uploadLabel = "Télécharger";
			if(fileUpload.cancelLabel == null)
				fileUpload.cancelLabel = "Annuler";
			if(fileUpload.fileLimit == null && Boolean.FALSE.equals(fileUpload.multiple))
				fileUpload.fileLimit = 1;
			if(fileUpload.fileLimitMessage == null && fileUpload.fileLimit == 1)
				fileUpload.fileLimitMessage = "Vous ne pouvez sélectionner q'un seul fichier. Veuillez télécharger celui déja sélectionner ou l'annuler pour en prendre un autre.";
			if(fileUpload.invalidSizeMessage == null && fileUpload.sizeLimit != null)
				fileUpload.invalidSizeMessage = String.format("La taille du fichier ne doit pas être supérieur à %s" , fileUpload.sizeLimit);
			/*
			label="Sélectionner un fichier au format pdf ou image"
    				oncomplete="PF('dialog').hide();"
    				*/
		}
		
		@Override
		protected String __getTemplate__(FileUpload inputText, Map<Object, Object> arguments) {
			return "/input/file/default.xhtml";
		}
		
		@Override
		protected Class<FileUpload> __getClass__() {
			return FileUpload.class;
		}
		
		/**/
		
	}
	
	public static FileUpload build(Map<Object, Object> arguments) {
		return Builder.build(FileUpload.class,arguments);
	}
	
	public static FileUpload build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static FileUpload buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(FileUpload.class, new ConfiguratorImpl());
	}
	
	/**/
	
	public static interface Listener {
		
		void listenFileUploaded(FileUpload fileUpload,FileUploadEvent event);
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener {			
			@Override
			public void listenFileUploaded(FileUpload fileUpload,FileUploadEvent event) {
				UploadedFile uploadedFile = event.getFile();
			    listenFileUploaded(event, uploadedFile.getContents());
			}
			
			protected void listenFileUploaded(FileUploadEvent event,byte[] bytes) {
				 if(bytes == null || bytes.length == 0)
				    return;
				 listenFileUploadedNotEmpty(event, bytes);
			}
			
			protected void listenFileUploadedNotEmpty(FileUploadEvent event,byte[] bytes) {
				
			}
		}
	}
}