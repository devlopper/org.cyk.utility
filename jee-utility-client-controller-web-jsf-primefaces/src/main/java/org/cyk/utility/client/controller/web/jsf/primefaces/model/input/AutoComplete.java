package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.event.AjaxBehaviorEvent;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.controller.Arguments;
import org.cyk.utility.controller.EntityReader;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.query.QueryIdentifierGetter;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
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
	private Integer maxResults = initialNumberOfResults;
	private String queryString,targetWidgetVar;
	private String dropdownMode = "current",emptyMessage="-- Aucun résultat --";
	private Integer queryDelay = QUERY_DELAY;
	private Boolean multiple,dropdown;
	private Object itemSelected;
	
	private ReadListener readItemLabelListener;
	private ReadListener readItemValueListener;
	
	private Boolean readerUsable;
	private Boolean isCountEqualsListSize;
	
	private String __queryString__;
	private Filter.Dto __filter__;
	private Properties __readProperties__;
	private Arguments<Object> __readerArguments__;
	private Response __response__;
	private Listener<Object> __listener__;
	private Runner.Arguments __runnerArguments__;
	private List<Object> __list__;
	private Integer __count__;
	
	@SuppressWarnings("unchecked")
	public Collection<Object> complete(String queryString) {
		long timestamp = System.currentTimeMillis();
		this.queryString = this.__queryString__ = queryString;
		if(StringHelper.isBlank(__queryString__))
			__queryString__ = null;
		__listener__ = (Listener<Object>) listener;
		if(__listener__ == null)
			__listener__ = (Listener<Object>) Listener.AbstractImpl.DefaultImpl.INSTANCE;		
		__filter__ = __listener__.instantiateFilter(this);
		if(Boolean.TRUE.equals(readerUsable))
			__readerArguments__ = __listener__.instantiateArguments(this);
		else
			__readProperties__ = __listener__.instantiateReadProperties(this);
		__list__ = (List<Object>) __listener__.complete(this);
		__response__ = __listener__.getResponse(this);
		if(CollectionHelper.isEmpty(__list__))
			__count__ = 0;
		else {
			if(Boolean.TRUE.equals(isCountEqualsListSize))
				__count__ = __list__.size();
			else
				__count__ = __listener__.getCount(this);
		}
		long duration = System.currentTimeMillis() - timestamp;
		if(Boolean.TRUE.equals(LOGGABLE)) {
			LogHelper.log(String.format("Read(%s) , duration=%s", __queryString__,duration), LOG_LEVEL,getClass());
		}		
		return __list__;
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
	
	public AutoComplete enableAjaxItemSelect() {
		getAjaxes().get("itemSelect").setDisabled(Boolean.FALSE).setListener(new Ajax.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				itemSelected = FieldHelper.read(action.get__argument__(), "source.value");
			}
		});
		return this;
	}
	
	public AutoComplete listenComplete(AutoComplete parent,String parentFieldName) {
		if(parent == null || StringHelper.isBlank(parentFieldName))
			return this;
		setListener(new AutoComplete.Listener.AbstractImpl<Object>() {
			@Override
			public void listenComplete(AutoComplete autoComplete, Runner.Arguments arguments, Filter.Dto filter,String queryString) {
				if(parent != null && parent.getValue() != null) {
					filter.addField(parentFieldName, FieldHelper.readBusinessIdentifier(parent.getValue()));
					//System.out.println("AutoComplete.listenComplete(...).new AbstractImpl() {...}.listenComplete()");
					//System.out.println(parent+" : "+parentFieldName+" ::: "+filter);
				}
				super.listenComplete(autoComplete, arguments, filter, queryString);
			}
		});
		return this;
	}
	
	public AutoComplete listenComplete(AutoComplete parent) {
		if(parent == null)
			return this;
		return listenComplete(parent,StringHelper.getVariableNameFrom(parent.getEntityClass().getSimpleName()));
	}
	
	public AutoComplete useQueryIdentifiersFiltersLike() {
		setReadQueryIdentifier(QueryHelper.getIdentifierReadByFiltersLike(entityClass));
		setCountQueryIdentifier(QueryHelper.getIdentifierCountByFiltersLike(entityClass));
		return this;
	}
	
	@Override
	public Object deriveBinding(String beanPath) {
		org.primefaces.component.autocomplete.AutoComplete component = new org.primefaces.component.autocomplete.AutoComplete();
		component.setVar("item");
		for(Object[] array : new Object[][] {
				new Object[] {FIELD_PLACEHOLDER,null,String.class}
				,new Object[] {FIELD_CONVERTER,null,Object.class}
				,new Object[] {FIELD_MAX_RESULTS,null,Integer.class}
				,new Object[] {FIELD_QUERY_DELAY,null,Integer.class}
				,new Object[] {FIELD_DROPDOWN,null,Boolean.class}
				,new Object[] {FIELD_DROPDOWN_MODE,null,String.class}
				,new Object[] {FIELD_EMPTY_MESSAGE,null,String.class}
				,new Object[] {FIELD_ON_CHANGE,"onchange",String.class}
				,new Object[] {FIELD_RENDERED,null,Boolean.class}
			}) {
			String property = array[1] == null ? (String) array[0] : (String) array[1];
			Class<?> klass = array[2] == null ? String.class : (Class<?>) array[2];
			__inject__(JavaServerFacesHelper.class).setValueExpression(component, property, JavaServerFacesHelper.buildValueExpression(String.format("#{%s.%s}",beanPath,array[0]), klass));
		}
		__inject__(JavaServerFacesHelper.class).setValueExpression(component, "itemValue", JavaServerFacesHelper.buildValueExpression("#{"+beanPath+".readItemValue(item)}", Object.class));
		__inject__(JavaServerFacesHelper.class).setValueExpression(component, "itemLabel", JavaServerFacesHelper.buildValueExpression("#{"+beanPath+".readItemLabel(item)}", String.class));		
		component.setCompleteMethod(__inject__(JavaServerFacesHelper.class).buildMethodExpression(beanPath+".complete", List.class, new Class<?>[] {String.class}));		
		return component;
	}
	
	public AbstractObject setBindingByDerivation(String beanPath,String valuePath) {
		setBindingByDerivation(beanPath);
		org.primefaces.component.autocomplete.AutoComplete component = (org.primefaces.component.autocomplete.AutoComplete) binding;
		__inject__(JavaServerFacesHelper.class).setValueExpression(component, "value", JavaServerFacesHelper.buildValueExpression("#{"+valuePath+"}", Object.class));
		return this;
	}
	
	/**/
	
	public static final String FIELD_CONTROLLER_ENTITY = "controllerEntity";
	public static final String FIELD_DROPDOWN = "dropdown";
	public static final String FIELD_DROPDOWN_MODE = "dropdownMode";
	public static final String FIELD_MULTIPLE = "multiple";
	public static final String FIELD_ENTITY_CLASS = "entityClass";
	public static final String FIELD_ENTITY_CONTROLLER = "entityController";
	public static final String FIELD_TARGET_WIDGET_VAR = "targetWidgetVar";
	public static final String FIELD_FILTER = "filter";
	public static final String FIELD_READ_QUERY_IDENTIFIER = "readQueryIdentifier";
	public static final String FIELD_COUNT_QUERY_IDENTIFIER = "countQueryIdentifier";
	public static final String FIELD_READER_USABLE = "readerUsable";
	public static final String FIELD_INITIAL_NUMBER_OF_RESULTS = "initialNumberOfResults";
	public static final String FIELD_MAX_RESULTS = "maxResults";
	public static final String FIELD_QUERY_DELAY = "queryDelay";
	public static final String FIELD_EMPTY_MESSAGE = "emptyMessage";
	public static final String FIELD_ON_CHANGE = "onChange";
	
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
	
	/**/
	
	public static interface Listener<T> extends AbstractInput.Listener {
		
		Filter.Dto instantiateFilter(AutoComplete autoComplete);
		Arguments<T> instantiateArguments(AutoComplete autoComplete);
		Properties instantiateReadProperties(AutoComplete autoComplete);
		Collection<T> complete(AutoComplete autoComplete);
		Response getResponse(AutoComplete autoComplete);
		Integer getCount(AutoComplete autoComplete);
		
		default void listenComplete(AutoComplete autoComplete,Runner.Arguments arguments,Filter.Dto filter,String queryString) {
			if(autoComplete == null || autoComplete.controllerEntity == null) {
				MessageRenderer.getInstance().render("controller need to be defined to query data by "+queryString,Severity.WARNING, RenderType.GROWL);
				return;
			}
			__complete__(arguments, autoComplete.controllerEntity, autoComplete.readQueryIdentifier, filter, queryString);
		}
		
		public static abstract class AbstractImpl<T> extends AbstractInput.Listener.AbstractImpl implements Listener<T>,Serializable {
			
			@Override
			public Filter.Dto instantiateFilter(AutoComplete autoComplete) {
				Filter.Dto filter = new Filter.Dto();
				if(Boolean.TRUE.equals(autoComplete.readerUsable)) {
					//String queryIdentifier = ValueHelper.defaultToIfBlank(autoComplete.readQueryIdentifier
					//		, QueryIdentifierGetter.getInstance().get(autoComplete.entityClass, QueryName.READ_WHERE_CODE_OR_NAME_LIKE));					
					filter.addField(Querier.PARAMETER_NAME_CODE, autoComplete.get__queryString__());
					filter.addField(Querier.PARAMETER_NAME_NAME, autoComplete.get__queryString__());					
				}else {
					if(StringHelper.isNotBlank(autoComplete.__queryString__))
						filter.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, autoComplete.__queryString__)
						.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, autoComplete.__queryString__);
				}
				return filter;
			}
			
			@Override
			public Arguments<T> instantiateArguments(AutoComplete autoComplete) {
				Arguments<T> arguments = new Arguments<T>()
						.setRepresentationArguments(new org.cyk.utility.representation.Arguments().setQueryExecutorArguments(new QueryExecutorArguments.Dto()
								.setQueryIdentifier(autoComplete.readQueryIdentifier)
								.setFirstTupleIndex(0)
								.setNumberOfTuples(autoComplete.maxResults)
								.setFilter(autoComplete.__filter__))
								.setCountable(Boolean.TRUE));
				return arguments;
			}
			
			@Override
			public Properties instantiateReadProperties(AutoComplete autoComplete) {
				Properties properties = new Properties().setQueryIdentifier(autoComplete.readQueryIdentifier).setIsPageable(Boolean.TRUE);
				if(autoComplete.__filter__.getFields() != null && CollectionHelper.isNotEmpty(autoComplete.__filter__.getFields())) {
					properties.setFilters(autoComplete.__filter__);
				}
				return properties;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Collection<T> complete(AutoComplete autoComplete) {
				if(Boolean.TRUE.equals(autoComplete.readerUsable)) {
					Runner.Arguments arguments = new Runner.Arguments().assignDefaultMessageArguments().setSuccessMessageArguments(null);
					arguments.getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));										
					arguments.addRunnables(new Runnable() {				
						@Override
						public void run() {
							arguments.setResult(EntityReader.getInstance().readMany((Class<Object>)autoComplete.entityClass, autoComplete.__readerArguments__));
						}
					});				
					return (Collection<T>) Runner.getInstance().run(arguments);	
				}else {
					if(autoComplete.controllerEntity == null) {
						MessageRenderer.getInstance().render("controller need to be defined to query data by "+autoComplete.__queryString__,Severity.WARNING, RenderType.GROWL);
						return null;
					}
					if(StringHelper.isBlank(autoComplete.readQueryIdentifier)) {
						MessageRenderer.getInstance().render("read query identifier need to be defined to query data by "+autoComplete.__queryString__,Severity.WARNING, RenderType.GROWL);
						return null;
					}
					
					if(autoComplete.__queryString__ != null)
						autoComplete.__filter__.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, autoComplete.__queryString__)
						.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, autoComplete.__queryString__);
					Properties properties = new Properties().setQueryIdentifier(autoComplete.readQueryIdentifier).setIsPageable(Boolean.TRUE);
					if(autoComplete.__filter__.getFields() != null && CollectionHelper.isNotEmpty(autoComplete.__filter__.getFields())) {
						properties.setFilters(autoComplete.__filter__);
					}
					
					Runner.Arguments arguments = new Runner.Arguments().assignDefaultMessageArguments().setSuccessMessageArguments(null);
					arguments.getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));										
					arguments.addRunnables(new Runnable() {				
						@Override
						public void run() {
							arguments.setResult(autoComplete.controllerEntity.read(properties));
						}
					});				
					return (Collection<T>) Runner.getInstance().run(arguments);	
				}				
			}
			
			@Override
			public Response getResponse(AutoComplete autoComplete) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Integer getCount(AutoComplete autoComplete) {
				// TODO Auto-generated method stub
				return null;
			}
			
			/**/
			
			public static class DefaultImpl extends Listener.AbstractImpl<Object> implements Serializable {
				public static final Listener<Object> INSTANCE = new DefaultImpl();
			}			
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
			
			if(Boolean.TRUE.equals(autoComplete.readerUsable)) {
				if(StringHelper.isBlank(autoComplete.readQueryIdentifier)) {
					autoComplete.readQueryIdentifier = QueryIdentifierGetter.getInstance().get(autoComplete.entityClass, QueryName.READ_WHERE_CODE_OR_NAME_LIKE);
				}
			}else {
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
			}
						
			autoComplete.addAjaxes(Map.of(Ajax.FIELD_EVENT,"query",Ajax.FIELD_DISABLED,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"moreText",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD_LISTENER
					,new AbstractAction.Listener.AbstractImpl() {
						@Override
						public void run(AbstractAction action) {
							if(action.get__argument__() instanceof AjaxBehaviorEvent) {
								AjaxBehaviorEvent event = (AjaxBehaviorEvent) action.get__argument__();
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
					itemSelect.set__runnable__(Boolean.FALSE);
					
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
		protected String __getDefaultOutputLabelValue__(AutoComplete autoComplete) {
			if(autoComplete.entityClass == null)
				return super.__getDefaultOutputLabelValue__(autoComplete);
			return InternationalizationHelper.buildString(InternationalizationHelper.buildKey(autoComplete.entityClass.getSimpleName()),null,null,Case.FIRST_CHARACTER_UPPER);
		}
		
		@Override
		protected Class<AutoComplete> __getClass__() {
			return AutoComplete.class;
		}
		
		@Override
		protected String __getTemplate__(AutoComplete autoComplete, Map<Object, Object> arguments) {
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
	
	public static AutoComplete buildFromArray(Object...objects) {
		return build(objects);
	}
	
	/**/
	
	private static void __complete__(Runner.Arguments arguments,ControllerEntity<?> controllerEntity,String readQueryIdentifier,Filter.Dto filter,String queryString) {
		if(StringHelper.isBlank(queryString))
			queryString = null;
		if(queryString != null)
			filter.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl.FIELD_CODE, queryString)
			.addField(AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl.FIELD_NAME, queryString);
		Properties properties = new Properties().setQueryIdentifier(readQueryIdentifier).setIsPageable(Boolean.TRUE);
		if(filter.getFields() != null && CollectionHelper.isNotEmpty(filter.getFields())) {
			properties.setFilters(filter);
		}
		arguments.setResult(controllerEntity.read(properties));
	}
	
	/**/
	
	public static Boolean LOGGABLE = Boolean.TRUE;
	public static Level LOG_LEVEL = Level.FINE;

	static {
		Configurator.set(AutoComplete.class, new ConfiguratorImpl());
	}
}