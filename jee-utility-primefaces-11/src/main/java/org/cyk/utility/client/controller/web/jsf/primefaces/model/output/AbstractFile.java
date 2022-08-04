package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Event;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.Button;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.javascript.OpenWindowScriptBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractFile extends AbstractOutput<Object> implements Serializable {

	protected CommandButton /*createCommandButton,*/readCommandButton,updateCommandButton,deleteCommandButton;
	protected Button readButton,updateButton;
	protected OutputText labelOutputText;
	
	/**/
	
	/**/
	
	//public static final String FIELD_CREATE_COMMAND_BUTTON = "createCommandButton";
	public static final String FIELD_READ_COMMAND_BUTTON = "readCommandButton";
	public static final String FIELD_READ_BUTTON = "readButton";
	public static final String FIELD_LABEL_OUTPUT_TEXT = "labelOutputText";
	public static final String FIELD_UPDATE_COMMAND_BUTTON = "updateCommandButton";
	public static final String FIELD_DELETE_COMMAND_BUTTON = "deleteCommandButton";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<FILE extends AbstractFile> extends AbstractOutput.AbstractConfiguratorImpl<FILE> implements Serializable {

		@Override
		public void configure(FILE file, Map<Object, Object> arguments) {
			super.configure(file, arguments);
			String uri = StringHelper.get(MapHelper.readByKey(arguments, FIELD_READ_URI));
			
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_IS_IMAGE))) {
				
			}else {
				
			}
			
			if(file.readButton == null && StringHelper.isNotBlank(uri)) {
				file.readButton = Button.build(Button.FIELD_TITLE,"Afficher",Button.FIELD_ICON,"fa fa-eye");
				file.readButton.setEventScript(Event.CLICK, OpenWindowScriptBuilder.getInstance().build(uri));
			}
			
			if(file.readCommandButton == null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_READ_OUTCOME))) {
				String readOutcome = (String) MapHelper.readByKey(arguments, FIELD_READ_OUTCOME);
				file.readCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,"Afficher",CommandButton.FIELD_ICON,"fa fa-eye",CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.OPEN_VIEW_IN_DIALOG
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
						protected String getOutcome(AbstractAction action) {
							return readOutcome;
						}
						
						protected Map<String,List<String>> getViewParameters(AbstractAction action) {
							Map<String,List<String>> map = super.getViewParameters(action);
							if(map == null)
								map = new HashMap<>();
							map.put(ParameterName.ENTITY_IDENTIFIER.getValue(), List.of((String)MapHelper.readByKey(arguments, FIELD_ENTITY_IDENTIFIER)));
							return map;
						}
					}
				);
			}
			
			if(file.updateCommandButton == null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_UPDATE_OUTCOME))) {
				String updateOutcome = (String) MapHelper.readByKey(arguments, FIELD_UPDATE_OUTCOME);
				file.updateCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,"Modifier",CommandButton.FIELD_ICON,"fa fa-edit"
						,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.NAVIGATE_TO_VIEW
						,CommandButton.FIELD_IMMEDIATE,Boolean.TRUE
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
						protected String getOutcome(AbstractAction action) {
							return updateOutcome;
						}
						
						protected Map<String,List<String>> getViewParameters(AbstractAction action) {
							Map<String,List<String>> map = super.getViewParameters(action);
							if(map == null)
								map = new HashMap<>();
							String identifier = (String)MapHelper.readByKey(arguments, FIELD_ENTITY_IDENTIFIER);
							if(StringHelper.isBlank(identifier))
								identifier = (String)MapHelper.readByKey(arguments, FIELD_MASTER_IDENTIFIER);
							map.put(ParameterName.ENTITY_IDENTIFIER.getValue(), List.of(identifier));
							String uri = WebController.getInstance().getRequestedURI().toString();
							uri = URLEncoder.encode(uri, Charset.defaultCharset());
							map.put(ParameterName.URL.getValue(), List.of(uri));
							return map;
						}
					}
				);
			}
			
			Boolean deletable = (Boolean) MapHelper.readByKey(arguments, FIELD_DELETABLE);
			if(deletable == null)
				deletable = MapHelper.readByKey(arguments, FIELD_UPDATE_OUTCOME) != null && MapHelper.readByKey(arguments, FIELD_ENTITY_IDENTIFIER) != null;
			//if(deletable == null)
			//	deletable = MapHelper.readByKey(arguments, FIELD_ENTITY_IDENTIFIER) != null;
			if(file.deleteCommandButton == null && Boolean.TRUE.equals(deletable)) {
				file.deleteCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,"Supprimer",CommandButton.FIELD_ICON,"fa fa-remove"
						,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
						,CommandButton.FIELD_IMMEDIATE,Boolean.TRUE
						,CommandButton.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
						@SuppressWarnings("unchecked")
						protected Object __runExecuteFunction__(AbstractAction action) {
							if(file.listener instanceof Listener)
								((Listener<FILE>)file.listener).listenDelete(file);
							return null;
						}
					}
				);
			}
			
			if(file.labelOutputText == null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_LABEL_OUTPUT_TEXT_VALUE))) {
				file.labelOutputText = OutputText.buildFromValue((String) MapHelper.readByKey(arguments, FIELD_LABEL_OUTPUT_TEXT_VALUE));
				file.labelOutputText.addStyleClasses("cyk-bold");
			}
		}
		
		/**/
		
		public static final String FIELD_LABEL_OUTPUT_TEXT_VALUE = "labelOutputTextValue";
		public static final String FIELD_MASTER_IDENTIFIER = "configurator.masteridentifier";
		public static final String FIELD_ENTITY_IDENTIFIER = "configurator.entityidentifier";
		public static final String FIELD_READ_URI = "configurator.readURI";
		public static final String FIELD_READ_OUTCOME = "configurator.readOutcome";
		public static final String FIELD_UPDATE_OUTCOME = "configurator.updateOutcome";
		public static final String FIELD_DELETABLE = "configurator.deletable";
		public static final String FIELD_IS_IMAGE = "configurator.isimage";
	}
	
	/**/
	
	public static interface Listener<FILE extends AbstractFile> {
		void listenDelete(FILE file);
		public static abstract class AbstractImpl<FILE extends AbstractFile> extends AbstractObject implements Listener<FILE>,Serializable {
			@Override
			public void listenDelete(FILE file) {
				
			}
		}
	}
}