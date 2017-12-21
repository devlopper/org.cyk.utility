package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.command.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputFile extends Input<FileHelper.File> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		//Preview selected
		Image previewImage = new Image();
		propertiesMap.setPreviewImageComponent(previewImage);
		
		Command clearCommand = new Command();
		clearCommand.setLabelFromIdentifier("userinterface.command.clear");
		propertiesMap.setClearCommand(clearCommand);
		
		//Preview current
		Image image = new Image();
		image.getPropertiesMap().setGetter(Properties.RENDERED, new Properties.Getter() {
			@Override
			public Object execute(Properties properties, Object key,Object value, Object nullValue) {
				return getValue()!=null;
			}
		});
		propertiesMap.setImageComponent(image);
		
		Command removeCommand = new Command();
		removeCommand.setLabelFromIdentifier("userinterface.command.remove");
		removeCommand.setAction(new CommandHelper.Command.Adapter.Default(){
			private static final long serialVersionUID = 1L;

			@Override
			protected Object __execute__() {
				setValue(null);
				return null;
			}
		});
		removeCommand.getPropertiesMap().setGetter(Properties.RENDERED, new Properties.Getter() {
			@Override
			public Object execute(Properties properties, Object key,Object value, Object nullValue) {
				Image image = (Image) InputFile.this.getPropertiesMap().getImageComponent();
				return image==null ? null : image.getPropertiesMap().getRendered();
			}
		});
		propertiesMap.setRemoveCommand(removeCommand);
		
		super.listenPropertiesInstanciated(propertiesMap);
	}
	
	@Override
	public InputFile setField(Field field) {
		return (InputFile) super.setField(field);
	}
	
	@Override
	public InputFile __setField__(Object object, String fieldName) {
		return (InputFile) super.__setField__(object, fieldName);
	}
	
	public String getPreviewJavaScript(){
		return JavaScriptHelper.getInstance().getFunctionCallPreview("this","'cyk-ui-input-file-preview-img-'","'"+getPropertiesMap().getIdentifierAsStyleClass()+"'");
	}
	
	public static interface BuilderBase<OUTPUT extends InputFile> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputFile> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputFile> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputFile> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputFile> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputFile>) ClassHelper.getInstance().getByName(InputFile.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}