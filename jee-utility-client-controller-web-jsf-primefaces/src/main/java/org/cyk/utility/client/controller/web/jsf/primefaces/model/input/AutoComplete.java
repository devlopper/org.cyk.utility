package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Event;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoComplete extends AbstractInput<Object> implements Serializable {

	public static Integer INITIAL_NUMBER_OF_RESULTS = 10;
	public static Integer QUERY_DELAY = 2000;
	
	private Class<?> entityClass;
	private ControllerEntity<?> controllerEntity;
	private String readQueryIdentifier,countQueryIdentifier;
	
	private Integer initialNumberOfResults = INITIAL_NUMBER_OF_RESULTS;
	private Integer numberOfResults = initialNumberOfResults;
	private String queryString,targetWidgetVar;
	private String dropdownMode = "current",emptyMessage="-- Aucun résultat --";
	private Integer queryDelay = QUERY_DELAY;
	private Boolean multiple,dropdown;
	
	private ReadListener readItemLabelListener;
	private ReadListener readItemValueListener;
	
	@SuppressWarnings("unchecked")
	public Collection<Object> complete(String queryString) {
		this.queryString = queryString;
		if(listener != null || controllerEntity != null) {
			Runner.Arguments arguments = new Runner.Arguments().assignDefaultMessageArguments().setSuccessMessageArguments(null);
			arguments.getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));
			if(listener instanceof AutoComplete.Listener) {				
				arguments.addRunnables(new Runnable() {				
					@Override
					public void run() {
						((AutoComplete.Listener)listener).listenComplete(AutoComplete.this,arguments,new FilterDto(),queryString);
					}
				});				
			}else if(controllerEntity != null) {
				if(StringHelper.isBlank(readQueryIdentifier)) {
					MessageRenderer.getInstance().render("read query identifier need to be defined to query data by "+queryString,Severity.WARNING, RenderType.GROWL);
				}else {
					arguments.addRunnables(new Runnable() {				
						@Override
						public void run() {
							__complete__(arguments, controllerEntity, readQueryIdentifier, new FilterDto(), queryString);
						}
					});
				}				
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
	
	public static final String FIELD_DROPDOWN = "dropdown";
	public static final String FIELD_MULTIPLE = "multiple";
	public static final String FIELD_ENTITY_CLASS = "entityClass";
	public static final String FIELD_ENTITY_CONTROLLER = "entityController";
	public static final String FIELD_TARGET_WIDGET_VAR = "targetWidgetVar";
	public static final String FIELD_FILTER = "filter";
	public static final String FIELD_READ_QUERY_IDENTIFIER = "readQueryIdentifier";
	public static final String FIELD_COUNT_QUERY_IDENTIFIER = "countQueryIdentifier";
	
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
	
	/**/
	
	public static interface Listener {
		
		default void listenComplete(AutoComplete autoComplete,Runner.Arguments arguments,FilterDto filter,String queryString) {
			if(autoComplete == null || autoComplete.controllerEntity == null) {
				MessageRenderer.getInstance().render("controller need to be defined to query data by "+queryString,Severity.WARNING, RenderType.GROWL);
				return;
			}
			__complete__(arguments, autoComplete.controllerEntity, autoComplete.readQueryIdentifier, filter, queryString);
		}
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {
			
		}
	}

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<AutoComplete> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(AutoComplete autoComplete, Map<Object, Object> arguments) {
			super.configure(autoComplete, arguments);
			if(autoComplete.entityClass == null) {
				if(autoComplete.field != null)
					autoComplete.entityClass = autoComplete.field.getType();
			}
			
			if(autoComplete.controllerEntity == null) {
				if(autoComplete.entityClass != null) {
					autoComplete.controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(autoComplete.entityClass);
					
					String persistenceEntityClassName = ClassHelper.buildName(autoComplete.entityClass.getPackageName(), autoComplete.entityClass.getSimpleName()
							, new NamingModel().client().controller().entities(), new NamingModel().server().persistence().entities());						
					Class<?> persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
					if(StringHelper.isBlank(autoComplete.readQueryIdentifier))
						autoComplete.readQueryIdentifier = QueryHelper.getIdentifierReadWhereBusinessIdentifierOrNameContains(persistenceEntityClass);
					if(StringHelper.isBlank(autoComplete.countQueryIdentifier))
						autoComplete.countQueryIdentifier = QueryHelper.getIdentifierCountWhereBusinessIdentifierOrNameContains(persistenceEntityClass);
				}
			}
			
			autoComplete.addAjaxes(Map.of(Ajax.FIELD_EVENT,"query",Ajax.FIELD_DISABLED,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"moreText",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD_LISTENER
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
					})
					,Map.of(Ajax.FIELD_EVENT,"itemSelect")
					,Map.of(Ajax.FIELD_EVENT,"itemUnselect"));
			
			if(autoComplete.entityClass == null) {
				
			}else {
				if(StringHelper.isBlank(autoComplete.targetWidgetVar)) {
					autoComplete.setConverter(DependencyInjection.inject(ObjectConverter.class));
				}else {
					String script = String.format(SCRIPT_FILTER, autoComplete.targetWidgetVar);
					Ajax itemSelect = autoComplete.ajaxes.get("itemSelect");
					autoComplete.getEventScripts(Boolean.TRUE).write(Event.CHANGE, script);
					itemSelect.setDisabled(Boolean.FALSE);
					itemSelect.getEventScripts(Boolean.TRUE).write(Event.COMPLETE, script);
					itemSelect.setThrowNotYetImplemented(Boolean.FALSE);
					
					autoComplete.setReadItemValueListener(new ReadListener() {				
						@Override
						public Object read(Object object) {
							return FieldHelper.readBusinessIdentifier(object);
						}
					});
				}
			}
			
			if(autoComplete.dropdown == null) {
				autoComplete.dropdown = Boolean.TRUE;
			}
		}
		
		@Override
		protected Class<AutoComplete> __getClass__() {
			return AutoComplete.class;
		}
		
		@Override
		protected String __getTemplate__() {
			return "/input/select/one/autoComplete.xhtml";
		}
		
		/**/
		
		private static final String SCRIPT_FILTER = "PF('%s').filter()";
	}
	
	public static AutoComplete build(Map<Object, Object> arguments) {
		return Builder.build(AutoComplete.class,arguments);
	}
	
	public static AutoComplete build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	/**/
	
	private static void __complete__(Runner.Arguments arguments,ControllerEntity<?> controllerEntity,String readQueryIdentifier,FilterDto filter,String queryString) {
		if(StringHelper.isBlank(queryString))
			queryString = null;
		if(queryString != null)
			filter.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, queryString)
			.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, queryString);
		Properties properties = new Properties().setQueryIdentifier(readQueryIdentifier).setIsPageable(Boolean.TRUE);
		if(filter.getFields() != null && CollectionHelper.isNotEmpty(filter.getFields().getCollection())) {
			properties.setFilters(filter);
		}
		arguments.setResult(controllerEntity.read(properties));
	}
	
	/**/

	static {
		Configurator.set(AutoComplete.class, new ConfiguratorImpl());
	}
}
