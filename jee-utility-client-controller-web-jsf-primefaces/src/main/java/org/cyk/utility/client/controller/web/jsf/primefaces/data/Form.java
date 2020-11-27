package org.cyk.utility.client.controller.web.jsf.primefaces.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.controller.Arguments;
import org.cyk.utility.__kernel__.controller.EntitySaver;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationPhrase;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.object.__static__.controller.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputClassGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Form extends AbstractObject implements Serializable {

	private String title;
	private Action action;
	private Class<?> entityClass;
	private Object entity;
	private Boolean saverUsable;
	private ControllerEntity<Object> controllerEntity;
	private Object request;
	private Layout layout;
	private Collection<String> entityFieldsNames;
	private Collection<String> updatableEntityFieldsNames;
	private CommandButton submitCommandButton;
	private Object container;
	private Form.Listener listener;
	private Map<String,AbstractInput<?>> inputs;
	private String redirectionUrl;
	
	public void execute() {
		Listener listener = this.listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : this.listener;
		listener.listenBeforeExecute(this);
		listener.act(this);		
		if(container instanceof Dialog) {
			((Dialog)container).hideOnComplete();
			//PrimefacesHelper.updateOnComplete(":form:"+dataTable.getIdentifier());
		}else if(!WebController.getInstance().isPageRenderedAsDialog())
			listener.redirect(this, request);		
		listener.listenAfterExecute(this);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getInput(Class<T> klass,String fieldName) {
		if(klass == null || StringHelper.isBlank(fieldName) || MapHelper.isEmpty(inputs))
			return null;
		return (T) inputs.get(fieldName);
	}
	
	public Boolean isSubmitButtonShowable() {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).isSubmitButtonShowable(this);
	}
	
	/**/
	
	public static final String FIELD_SUBMIT_COMMAND_BUTTON = "submitCommandButton";
	public static final String FIELD_CONTAINER = "container";
	public static final String FIELD_REQUEST = "request";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_ENTITY_CLASS = "entityClass";
	public static final String FIELD_ENTITY = "entity";
	public static final String FIELD_SAVER_USABLE = "saverUsable";
	public static final String FIELD_CONTROLLER_ENTITY = "controllerEntity";
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_ENTITY_FIELDS_NAMES = "entityFieldsNames";
	public static final String FIELD_LISTENER = "listener";
	public static final String FIELD_REDIRECTION_URL = "redirectionUrl";
	public static final String METHOD_EXECUTE = "execute";
	
	/**/
	
	public static class ConfiguratorImpl extends Configurator.AbstractImpl<Form> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(Form form, Map<Object, Object> arguments) {
			super.configure(form, arguments);
			Listener listener = (Listener) MapHelper.readByKey(arguments, FIELD_LISTENER);
			if(listener == null)
				listener = Listener.AbstractImpl.DefaultImpl.INSTANCE;
			if(form.action == null) {
				form.action = Action.getByNameCaseInsensitive(Faces.getRequestParameter(ParameterName.ACTION_IDENTIFIER.getValue()));
			}
			
			Boolean controllerEntityInjectable = MapHelper.readByKey(arguments,FIELD_CONTROLLER_ENTITY_INJECTABLE) == null 
					|| Boolean.TRUE.equals(MapHelper.readByKey(arguments,FIELD_CONTROLLER_ENTITY_INJECTABLE));
			if(Boolean.TRUE.equals(controllerEntityInjectable) && form.controllerEntity == null && form.entityClass != null) {
				form.controllerEntity = (ControllerEntity<Object>) __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(form.entityClass);
			}
						
			if(form.entity == null) {
				if(Action.CREATE.equals(form.action))
					form.entity = ClassHelper.instanciate(form.entityClass);
				else {
					String entityIdentifier = Faces.getRequestParameter(ParameterName.ENTITY_IDENTIFIER.getValue());
					if(StringHelper.isNotBlank(entityIdentifier))
						form.entity = form.controllerEntity.readBySystemIdentifier(entityIdentifier);
				}
			}
			
			if(form.entityFieldsNames == null && form.entityClass != null) {
				form.entityFieldsNames = listener.getFieldsNames(form);
			}
			
			if(form.title == null && form.entityClass != null && form.action != null) {
				InternationalizationPhrase internationalizationPhrase = new InternationalizationPhrase().setKase(Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER);
				internationalizationPhrase.addNoun(form.action.name()).addString("of").addString(form.entityClass);
				InternationalizationHelper.processPhrases(internationalizationPhrase);
				form.title = internationalizationPhrase.getValue();
			}
			
			Boolean layoutBuildable = ValueHelper.defaultToIfNull((Boolean) MapHelper.readByKey(arguments, FIELD_LAYOUT_BUILDABLE),Boolean.TRUE);
			if(form.layout == null && layoutBuildable && CollectionHelper.isNotEmpty(form.entityFieldsNames)) {
				Collection<String> inputsFieldsNames = (Collection<String>) MapHelper.readByKey(arguments, FIELD_INPUTS_FIELDS_NAMES);
				if(CollectionHelper.isEmpty(inputsFieldsNames)) {
					inputsFieldsNames = form.entityFieldsNames;
				}
				
				Collection<AbstractInput<?>> inputs = null;
				if(MapHelper.isEmpty(form.inputs)) {
					for(String fieldName : inputsFieldsNames) {
						AbstractInput<?> input = listener.buildInput(form, fieldName);
						if(input == null)
							continue;
						if(inputs == null)
							inputs = new ArrayList<>();
						inputs.add(input);
					}
				}else {
					inputs = form.inputs.values();
				}
				if(CollectionHelper.isNotEmpty(inputs)) {
					Collection<Map<Object,Object>> cells = __getLayoutCellsArgumentsMaps__(form,inputs,listener);
					if(CollectionHelper.isNotEmpty(cells)) {
						Map<Object,Object> layoutArguments = listener.getLayoutArguments(form, cells);
						form.layout = Layout.build(layoutArguments);
						if(form.submitCommandButton != null) {
							form.submitCommandButton.addUpdatables(form.layout);
						}
					}			
				}
			}
			
			if(form.request == null) {
				form.request = __inject__(HttpServletRequest.class);
			}
			
			if(form.updatableEntityFieldsNames == null) {
				if(CollectionHelper.isNotEmpty(form.entityFieldsNames)) {
					form.updatableEntityFieldsNames = new ArrayList<String>(form.entityFieldsNames);
					Field systemIdentifierField = FieldHelper.getSystemIdentifier(form.entityClass);
					if(systemIdentifierField != null)
						form.updatableEntityFieldsNames.add(systemIdentifierField.getName());
				}
			}
			
			if(form.redirectionUrl == null)
				form.redirectionUrl = WebController.getInstance().getRequestParameter(ParameterName.URL);
		}
		
		protected Collection<Map<Object,Object>> __getLayoutCellsArgumentsMaps__(Form form,Collection<AbstractInput<?>> inputs,Listener listener) {
			if(CollectionHelper.isEmpty(inputs))
				return null;
			form.inputs = new HashMap<>();
			Collection<Map<Object,Object>> cells = new ArrayList<>();
			inputs.forEach(new Consumer<AbstractInput<?>>() {
				@Override
				public void accept(AbstractInput<?> input) {
					cells.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input.getOutputLabel()));
					cells.add(MapHelper.instantiate(Cell.FIELD_CONTROL,input));
					form.inputs.put(input.getField().getName(), input);//TODO nested wont work. use path instead
				}
			});
			if(Boolean.TRUE.equals(form.isSubmitButtonShowable())) {
				Map<Object,Object> submitCommandArguments = listener.getCommandButtonArguments(form, inputs);
				if(form.container instanceof Dialog) {
					Dialog dialog = (Dialog) form.container;
					form.submitCommandButton = CommandButton.build(submitCommandArguments);
					form.submitCommandButton.getRunnerArguments().getSuccessMessageArguments().setRenderTypes(List.of(RenderType.GROWL,RenderType.INLINE));
					CollectionHelper.setElementAt(dialog.getCommandButtons(), 0, form.submitCommandButton);
					dialog.setExecuteCommandButton(form.submitCommandButton);
				}else {
					cells.add(MapHelper.instantiate(Cell.FIELD_CONTROL,form.submitCommandButton = CommandButton.build(submitCommandArguments),Cell.FIELD_WIDTH,12));
				}
			}			
			return cells;
		}	
		
		@Override
		protected Class<Form> __getClass__() {
			return Form.class;
		}
		
		/**/
		
		public static final String FIELD_INPUTS_FIELDS_NAMES = "inputsFieldsNames";
		public static final String FIELD_METHOD_NAME = "methodName";
		public static final String FIELD_LISTENER = "configurator.listener";
		public static final String FIELD_LAYOUT_BUILDABLE = "configurator.layout.buildable";
		public static final String FIELD_CONTROLLER_ENTITY_INJECTABLE = "controllerEntityInjectable";
		/**/
		
		public static interface Listener {
			Collection<String> getFieldsNames(Form form);
			AbstractInput<?> buildInput(Form form,String fieldName);		
			Class<?> getInputClass(Form form,String fieldName);
			Map<Object,Object> getInputArguments(Form form,String fieldName);
			Map<Object,Object> getCommandButtonArguments(Form form,Collection<AbstractInput<?>> inputs);
			Map<Object,Object> getLayoutArguments(Form form,Collection<Map<Object,Object>> cellsArguments);
			//Layout instantiateLayout(Form form);
			
			public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
				@Override
				public Collection<String> getFieldsNames(Form form) {
					Collection<String> fieldsNames = FieldHelper.getNames(FieldHelper.getByAnnotationClass(form.entity.getClass(), Input.class));
					if(CollectionHelper.getSize(fieldsNames) < 2)
						return fieldsNames;
					if(fieldsNames.contains(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER)
							&& fieldsNames.contains(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_CODE))
						fieldsNames.remove(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER);
					return fieldsNames;
				}
				
				@Override
				public AbstractInput<?> buildInput(Form form,String fieldName) {
					return InputBuilder.getInstance().build(form.entity, fieldName, getInputArguments(form, fieldName),getInputClass(form, fieldName));
				}
				
				@Override
				public Class<?> getInputClass(Form form,String fieldName) {
					return InputClassGetter.getInstance().get(form.entityClass, fieldName);
				}
				
				@Override
				public Map<Object,Object> getInputArguments(Form form,String fieldName) {
					Map<Object,Object> arguments = new HashMap<>();
					arguments.put(AbstractInput.FIELD_OBJECT, form.entity);
					arguments.put(AbstractInput.FIELD_FIELD, FieldHelper.getByName(form.entityClass, fieldName));
					arguments.put(AbstractInput.AbstractConfiguratorImpl.FIELD_ACTION, form.getAction());
					return arguments;
				}
				
				@Override
				public Map<Object, Object> getCommandButtonArguments(Form form,Collection<AbstractInput<?>> inputs) {
					return MapHelper.instantiate(CommandButton.FIELD_ICON,"fa fa-floppy-o",CommandButton.ConfiguratorImpl.FIELD_OBJECT,form
							,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,METHOD_EXECUTE,CommandButton.ConfiguratorImpl.FIELD_INPUTS,inputs
							,CommandButton.FIELD_STYLE_CLASS,"cyk-float-right");
				}
				
				@Override
				public Map<Object, Object> getLayoutArguments(Form form,Collection<Map<Object,Object>> cellsArguments) {
					return MapHelper.instantiate(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,2
								,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(3),1,new Cell().setWidth(9))
								,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,cellsArguments);
				}
				
				/**/
				
				public static class DefaultImpl extends AbstractImpl implements Serializable {
					public static final Listener INSTANCE = new DefaultImpl();
				}
			}
		}
	}
	
	public static Form build(Map<Object,Object> arguments) {
		return Builder.build(Form.class,arguments);
	}
	
	public static Form build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Form.class, new ConfiguratorImpl());
	}
	
	/**/
	
	public static interface Listener {
		void listenBeforeExecute(Form form);
		void act(Form form);
		void listenAfterExecute(Form form);
		void redirect(Form form,Object request);
		Boolean isSubmitButtonShowable(Form form);
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			
			@Override
			public void act(Form form) {
				if(Action.CREATE.equals(form.action)) {
					if(Boolean.TRUE.equals(form.saverUsable)) {
						@SuppressWarnings("unchecked")
						Class<Object> clazz = (Class<Object>) form.entityClass;
						EntitySaver.getInstance().save(clazz, new Arguments<Object>().addCreatablesOrUpdatables(form.entity));
					}else
						form.controllerEntity.create(form.entity);
				}else if(Action.UPDATE.equals(form.action)) {			 
					if(CollectionHelper.isEmpty(form.updatableEntityFieldsNames))
						throw new RuntimeException("No fields names have been defined for update");			
					form.controllerEntity.update(form.entity,new Properties().setFields(StringHelper.concatenate(form.updatableEntityFieldsNames, ",")));
				}else if(Action.DELETE.equals(form.action)) {
					form.controllerEntity.delete(form.entity);
				}else
					throw new RuntimeException(String.format("Action %s not yet handled", form.action));
			}
			
			@Override
			public void redirect(Form form,Object request) {
				String url = WebController.getInstance().getRequestParameter(ParameterName.URL);
				if(StringHelper.isBlank(url))
					url = form.redirectionUrl;
				if(StringHelper.isBlank(url))
					Faces.redirect(UniformResourceIdentifierHelper.build(request, SystemActionList.class, null, form.entityClass, null, null, null, null));
				else
					Redirector.getInstance().redirect(url);
			}
			
			@Override
			public void listenBeforeExecute(Form form) {}
			
			@Override
			public void listenAfterExecute(Form form) {}
			
			@Override
			public Boolean isSubmitButtonShowable(Form form) {
				return Action.CREATE.equals(form.action) || Action.UPDATE.equals(form.action) || Action.DELETE.equals(form.action) || Action.EDIT.equals(form.action);
			}
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				public static final Form.Listener INSTANCE = new DefaultImpl();
			}
		}
	}
}