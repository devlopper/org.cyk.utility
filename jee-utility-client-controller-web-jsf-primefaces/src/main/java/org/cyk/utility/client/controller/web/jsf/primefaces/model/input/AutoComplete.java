package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoComplete extends AbstractObject implements Serializable {

	public static Integer INITIAL_NUMBER_OF_RESULTS = 10;
	public static Integer QUERY_DELAY = 2000;
	
	@Getter private Class<?> entityClass;
	private ControllerEntity<?> controllerEntity;
	
	private Integer initialNumberOfResults = INITIAL_NUMBER_OF_RESULTS;
	@Getter private Integer numberOfResults = initialNumberOfResults;
	private String queryString;
	private String dropdownMode = "current",placeholder,emptyMessage="-- Aucun résultat --";
	private Object converter;
	private Object value;
	private Integer queryDelay = QUERY_DELAY;
	private Boolean multiple,dropdown;
	
	private ReadListener readItemLabelListener;
	private ReadListener readItemValueListener;
	
	private Ajax ajaxItemSelect,ajaxItemUnselect,ajaxQuery,ajaxMoreText;
	
	@Getter @Setter private Listener listener;
	
	@SuppressWarnings("unchecked")
	public Collection<Object> complete(String queryString) {
		this.queryString = queryString;
		if(listener != null || controllerEntity != null) {
			Runner.Arguments arguments = new Runner.Arguments().assignDefaultMessageArguments()
					.setSuccessMessageArguments(null);
			arguments.getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));
			if(listener != null) {				
				arguments.addRunnables(new Runnable() {				
					@Override
					public void run() {
						arguments.setResult(listener.listenComplete(AutoComplete.this,queryString));
					}
				});				
			}else if(controllerEntity != null) {
				arguments.addRunnables(new Runnable() {				
					@Override
					public void run() {
						arguments.setResult(controllerEntity.readByString(queryString));
					}
				});
			}
			return (Collection<Object>) Runner.getInstance().run(arguments);
		}
		MessageRenderer.getInstance().render("listener or controller need to be defined to query data by "+queryString,Severity.WARNING, RenderType.GROWL);
		return null;
	}
	
	public Object readItemValue(Object entity) {
		if(readItemValueListener == null)
			return entity;
		return readItemValueListener.read(entity);
	}
	
	public Object readItemLabel(Object entity) {
		if(readItemLabelListener == null)
			return entity;
		return readItemLabelListener.read(entity);
	}
	
	/**/
	
	public static final String FIELD_ENTITY_CLASS = "entityClass";
	public static final String FIELD_ENTITY_CONTROLLER = "entityController";
	
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
	
	/**/
	
	public static interface Listener {
		
		@SuppressWarnings("unchecked")
		default Collection<Object> listenComplete(AutoComplete autoComplete,String queryString) {
			if(autoComplete == null || autoComplete.controllerEntity == null)
				return null;
			return (Collection<Object>) autoComplete.controllerEntity.readByString(queryString);
		}
		
	}

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<AutoComplete> implements Serializable {

		@Override
		public void configure(AutoComplete autoComplete, Map<Object, Object> arguments) {
			super.configure(autoComplete, arguments);
			if(autoComplete.controllerEntity == null) {
				if(autoComplete.entityClass != null)
					autoComplete.controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(autoComplete.entityClass);
			}
			
			autoComplete.ajaxQuery = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"query",Ajax.FIELD_DISABLED,Boolean.FALSE));
			autoComplete.ajaxQuery.getRunnerArguments().setSuccessMessageArguments(null);
			
			autoComplete.ajaxMoreText = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"moreText",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD_LISTENER
					,new AbstractAction.Listener() {			
						@Override
						public void listenAction(Object argument) {
							if(argument instanceof AjaxBehaviorEvent) {
								AjaxBehaviorEvent event = (AjaxBehaviorEvent) argument;
								org.primefaces.component.autocomplete.AutoComplete component = (org.primefaces.component.autocomplete.AutoComplete) event.getSource();
								component.setMaxResults(component.getMaxResults() + autoComplete.initialNumberOfResults);
								if(StringHelper.isBlank(component.getWidgetVar())) {
									MessageRenderer.getInstance().render(new Message().setSummary("auto complete widget var attribute must be defined in order to execute search script")
											.setSeverity(Severity.WARNING), RenderType.GROWL);
								}else
									PrimeFaces.current().executeScript(String.format(SCRIPT_SEARCH, component.getWidgetVar(),autoComplete.queryString));		
							}					
						}
					}));
			autoComplete.ajaxMoreText.getRunnerArguments().setSuccessMessageArguments(null);
			
			autoComplete.ajaxItemSelect = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"itemSelect"));
			
			autoComplete.ajaxItemUnselect = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"itemUnselect"));
		}
		
		@Override
		protected Class<AutoComplete> __getClass__() {
			return AutoComplete.class;
		}		
	}

	static {
		Configurator.set(AutoComplete.class, new ConfiguratorImpl());
	}
}
