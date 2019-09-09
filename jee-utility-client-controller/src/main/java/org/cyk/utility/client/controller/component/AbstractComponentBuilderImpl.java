package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.AbstractObjectLifeCycleListenerImpl;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.clazz.Classes;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventBuilders;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.session.AbstractSessionHelperImpl;
import org.cyk.utility.client.controller.session.SessionHelper;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.internationalization.InternationalizationHelperImpl;
import org.cyk.utility.internationalization.InternationalizationString;
import org.cyk.utility.internationalization.InternationalizationStringMap;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.Strings;
import org.cyk.utility.type.BooleanMap;

public abstract class AbstractComponentBuilderImpl<COMPONENT extends Component> extends AbstractFunctionWithPropertiesAsInputImpl<COMPONENT> implements ComponentBuilder<COMPONENT> , Serializable {
	private static final long serialVersionUID = 1L;

	private Class<COMPONENT> componentClass;
	private Classes qualifiers;
	private COMPONENT component;
	private DeviceScreenArea area;
	private Style layoutItemStyle;
	private ComponentRoles roles;
	private Boolean isTargetModelToBeBuilt;
	private Objects updatables;
	//private InternalizationStringBuilderByStringMap internalizationStringMap;
	private InternationalizationStringMap internationalizationStringMap;
	private EventBuilders events;
	private Throwable throwable;
	private BooleanMap derivableFieldNameMap;
	private String getByIdentifierExpressionLanguageFormat;
	private Strings derivableProperties;
	private Object linkedTo;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		if(componentClass == null) {
			if(componentClass == null) {
				componentClass = (Class<COMPONENT>) ClassHelperImpl.__getByName__(ClassHelperImpl.__getParameterAt__(getClass(), 0, Object.class).getName());
			}	
		}
		AbstractObjectLifeCycleListenerImpl.getInstance().listenPostConstruct(this);
	}
	
	@Override
	public Function<Properties, COMPONENT> execute() {
		Classes qualifiers = getQualifiers();
		COMPONENT component = null;
		if(CollectionHelperImpl.__isEmpty__(qualifiers))
			component = DependencyInjection.inject(componentClass);
		else
			component = DependencyInjection.injectByQualifiersClasses(componentClass, qualifiers.get().toArray(new Class[] {}));		
		// Bidirectional Linking
		setComponent(component);
		component.setBuilder(this);
		
		component.setGetByIdentifierExpressionLanguageFormat(getGetByIdentifierExpressionLanguageFormat());
		Object linkedTo = getLinkedTo();
		if(linkedTo instanceof ComponentBuilder<?>)
			linkedTo = ((ComponentBuilder<?>)linkedTo).getComponent();
		component.setLinkedTo(linkedTo);
		
		Properties outputProperties = getOutputProperties();
		if(outputProperties!=null) {
			component.setIdentifier(outputProperties.getIdentifier());
			component.getProperties().setIdentifierAsStyleClass(outputProperties.getStyleClass());
		}
		AbstractObjectLifeCycleListenerImpl.getInstance().listenExecuteBefore(this);
		ComponentRoles roles = getRoles();
		component.setRoles(roles);
		Objects updatables = getUpdatables();
		component.setUpdatables(updatables);
		
		EventBuilders events = getEvents();
		if(CollectionHelperImpl.__isNotEmpty__(events)) {
			for(EventBuilder index : events.get()) {
				Event event = index.execute().getOutput();
				component.getEvents(Boolean.TRUE).add(event);
			}
		}
		__execute__(component);
		AbstractObjectLifeCycleListenerImpl.getInstance().listenExecuteAfter(this);
		Throwable throwable = getThrowable();
		component.setThrowable(throwable);
		if(component.getThrowable() != null)
			component.setThrowableInternalizationMessage(InternationalizationHelperImpl.__buildInternationalizationString__(InternationalizationHelperImpl
					.__buildInternationalizationKey__(component.getThrowable())));
		setProperty(Properties.OUTPUT, component);
		return this;
	}
	
	/*
	@Override
	protected COMPONENT __execute__() throws Exception {
		Class<COMPONENT> componentClass = getComponentClass();
		Classes qualifiers = getQualifiers();
		COMPONENT component = null;
		if(__injectCollectionHelper__().isEmpty(qualifiers))
			component = __inject__(componentClass);
		else
			component = __injectByQualifiersClasses__(componentClass, qualifiers.get().toArray(new Class[] {}));
		
		// Bidirectional Linking
		setComponent(component);
		component.setBuilder(this);
		
		component.setGetByIdentifierExpressionLanguageFormat(getGetByIdentifierExpressionLanguageFormat());
		Object linkedTo = getLinkedTo();
		if(linkedTo instanceof ComponentBuilder<?>)
			linkedTo = ((ComponentBuilder<?>)linkedTo).getComponent();
		component.setLinkedTo(linkedTo);
		
		Properties outputProperties = getOutputProperties();
		if(outputProperties!=null) {
			component.setIdentifier(outputProperties.getIdentifier());
			component.getProperties().setIdentifierAsStyleClass(outputProperties.getStyleClass());
		}
		//__inject__(ComponentBuilderExecuteListenerBefore.class).setObject(this).setComponent(component).execute();
		__inject__(ObjectLifeCycleListener.class).listenExecuteBefore(this);
		
		ComponentRoles roles = getRoles();
		component.setRoles(roles);
		
		Objects updatables = getUpdatables();
		component.setUpdatables(updatables);
		
		EventBuilders events = getEvents();
		if(__injectCollectionHelper__().isNotEmpty(events)) {
			for(EventBuilder index : events.get()) {
				Event event = index.execute().getOutput();
				component.getEvents(Boolean.TRUE).add(event);
			}
		}
		
		__execute__(component);
		__inject__(ObjectLifeCycleListener.class).listenExecuteAfter(this);
		//__inject__(ComponentBuilderExecuteListenerAfter.class).setObject(this).setComponent(component).execute();
		
		Throwable throwable = getThrowable();
		component.setThrowable(throwable);
		if(component.getThrowable() != null)
			component.setThrowableInternalizationMessage(__inject__(InternalizationStringBuilder.class).setKeyValue(component.getThrowable()).execute().getOutput());
		return component;
	}
	*/
	protected void __execute__(COMPONENT component) {
		component.getProperties().copyNonNullKeysFrom(getOutputProperties());		
	}
	
	@Override
	public Class<COMPONENT> getComponentClass() {
		return componentClass;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass) {
		this.componentClass = componentClass;
		return this;
	}
	
	@Override
	public Classes getQualifiers() {
		return qualifiers;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setQualifiers(Classes qualifiers) {
		this.qualifiers = qualifiers;
		return this;
	}
	
	@Override
	public Classes getQualifiers(Boolean injectIfNull) {
		if(qualifiers == null && Boolean.TRUE.equals(injectIfNull))
			qualifiers = __inject__(Classes.class);
		return qualifiers;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addQualifiers(@SuppressWarnings("rawtypes") Collection<Class> qualifiers) {
		if(__injectCollectionHelper__().isNotEmpty(qualifiers))
			getQualifiers(Boolean.TRUE).add(qualifiers);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addQualifiers(@SuppressWarnings("rawtypes") Class... qualifiers) {
		return addQualifiers(__injectCollectionHelper__().instanciate(qualifiers));
	}
	
	@Override
	public COMPONENT getComponent() {
		return component;
	}
	@Override
	public ComponentBuilder<COMPONENT> setComponent(COMPONENT component) {
		this.component = component;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputProperties(Properties outputProperties) {
		return (ComponentBuilder<COMPONENT>) super.setOutputProperties(outputProperties);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (ComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputPropertyName(Object value) {
		return (ComponentBuilder<COMPONENT>) super.setOutputPropertyName(value);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputPropertyValue(Object value) {
		return (ComponentBuilder<COMPONENT>) super.setOutputPropertyValue(value);
	}
	
	@Override
	public DeviceScreenArea getArea() {
		return area;
	}

	@Override
	public DeviceScreenArea getArea(Boolean injectIfNull) {
		if(area == null && Boolean.TRUE.equals(injectIfNull))
			area = __inject__(DeviceScreenArea.class);
		return area;
	}

	@Override
	public ComponentBuilder<COMPONENT> setArea(DeviceScreenArea area) {
		this.area = area;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidth(DeviceScreenDimensionProportions areaWidth) {
		getArea(Boolean.TRUE).setWidthProportions(areaWidth);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>, Integer> proportionMap) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setMap(proportionMap);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setAllClasses(_default, television, desktop, tablet, phone);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>... classes) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setByClasses(proportion, classes);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setByClasses(proportion, classes);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width) {
		return setAreaWidthProportions(width, width, width, width, null);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setLayoutItemStyle(Style style) {
		this.layoutItemStyle = style;
		return this;
	}
	
	@Override
	public Style getLayoutItemStyle() {
		return layoutItemStyle;
	}
	
	@Override
	public Style getLayoutItemStyle(Boolean injectIfNull) {
		if(layoutItemStyle == null && Boolean.TRUE.equals(injectIfNull))
			layoutItemStyle = __inject__(Style.class);
		return layoutItemStyle;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addLayoutItemStyleClasses(String... classes) {
		getLayoutItemStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add(classes);
		return this;
	}
	
	@Override
	public ComponentRoles getRoles() {
		return roles;
	}
	
	@Override
	public ComponentRoles getRoles(Boolean injectIfNull) {
		if(roles == null && Boolean.TRUE.equals(injectIfNull))
			roles = __inject__(ComponentRoles.class);
		return roles;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setRoles(ComponentRoles roles) {
		this.roles = roles;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addRoles(Collection<ComponentRole> roles) {
		getRoles(Boolean.TRUE).add(roles);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addRoles(ComponentRole... roles) {
		getRoles(Boolean.TRUE).add(roles);
		return this;
	}
	
	@Override
	public Boolean getIsTargetModelToBeBuilt() {
		return isTargetModelToBeBuilt;
	}
	@Override
	public ComponentBuilder<COMPONENT> setIsTargetModelToBeBuilt(Boolean isTargetModelToBeBuilt) {
		this.isTargetModelToBeBuilt = isTargetModelToBeBuilt;
		return this;
	}
	
	@Override
	public Objects getUpdatables() {
		return updatables;
	}
	
	@Override
	public Objects getUpdatables(Boolean injectIfNull) {
		if(updatables == null && Boolean.TRUE.equals(injectIfNull))
			updatables = __inject__(Objects.class);
		return updatables;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setUpdatables(Objects updatables) {
		this.updatables = updatables;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addUpdatables(Collection<Object> updatables) {
		getUpdatables(Boolean.TRUE).add(updatables);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addUpdatables(Object... updatables) {
		getUpdatables(Boolean.TRUE).add(updatables);
		return this;
	}
	
	@Override
	public InternationalizationStringMap getInternationalizationStringMap() {
		return internationalizationStringMap;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setInternationalizationStringMap(InternationalizationStringMap internationalizationStringMap) {
		this.internationalizationStringMap = internationalizationStringMap;
		return this;
	}
	
	@Override
	public InternationalizationStringMap getInternationalizationStringMap(Boolean injectIfNull) {
		if(internationalizationStringMap == null && Boolean.TRUE.equals(injectIfNull))
			internationalizationStringMap = __inject__(InternationalizationStringMap.class);
		return internationalizationStringMap;
	}
	
	@Override
	public InternationalizationString getNameInternationalization() {
		return getInternationalizationStringMap(Boolean.TRUE).get(Properties.NAME);
	}
	
	@Override
	public InternationalizationString getNameInternationalization(Boolean injectIfNull) {
		return getInternationalizationStringMap(Boolean.TRUE).get(Properties.NAME,injectIfNull);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setNameInternationalization(InternationalizationString nameInternationalization) {
		getInternationalizationStringMap(Boolean.TRUE).set(Properties.NAME,nameInternationalization);
		return this;
	}
	/*
	@Override
	public InternalizationStringBuilderByStringMap getInternalizationStringMap() {
		return internalizationStringMap;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setInternalizationStringMap(InternalizationStringBuilderByStringMap internalizationStringMap) {
		this.internalizationStringMap = internalizationStringMap;
		return this;
	}
	
	@Override
	public InternalizationStringBuilderByStringMap getInternalizationStringMap(Boolean injectIfNull) {
		if(internalizationStringMap == null && Boolean.TRUE.equals(injectIfNull))
			internalizationStringMap = __inject__(InternalizationStringBuilderByStringMap.class);
		return internalizationStringMap;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setInternalizationKeyValue(String key, String value) {
		getInternalizationStringMap(Boolean.TRUE).setInternalizationKeyValue(key, value);
		return this;
	}
	
	@Override
	public InternalizationStringBuilder getNameInternalization() {
		return getInternalizationStringMap(Boolean.TRUE).get(Properties.NAME);
	}
	
	@Override
	public InternalizationStringBuilder getNameInternalization(Boolean injectIfNull) {
		return getInternalizationStringMap(Boolean.TRUE).get(Properties.NAME,injectIfNull);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setNameInternalization(InternalizationStringBuilder nameInternalization) {
		getInternalizationStringMap(Boolean.TRUE).set(Properties.NAME,nameInternalization);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setNameInternalizationKeyValue(String nameInternalizationKeyValue) {
		getNameInternalization(Boolean.TRUE).setKeyValue(nameInternalizationKeyValue);
		return this;
	}
	*/
	@Override
	public EventBuilders getEvents() {
		return events;
	}
	
	@Override
	public EventBuilders getEvents(Boolean injectIfNull) {
		if(events == null && Boolean.TRUE.equals(injectIfNull))
			events = __inject__(EventBuilders.class);
		return events;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setEvents(EventBuilders events) {
		this.events = events;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addEvents(Collection<EventBuilder> events) {
		getEvents(Boolean.TRUE).add(events);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addEvents(EventBuilder... events) {
		return addEvents(__injectCollectionHelper__().instanciate(events));
	}
	
	@Override
	public EventBuilder getEventByName(EventName name, Boolean injectIfNull) {
		EventBuilders events = getEvents(injectIfNull);
		return events == null ? null : events.getByName(name, injectIfNull);
	}
	
	@Override
	public EventBuilder getEventByName(EventName name) {
		return getEventByName(name, null);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addEvent(EventName name, Runnable runnable,Object...updatables) {
		EventBuilder event = getEventByName(name, Boolean.TRUE);
		event.addUpdatables(updatables);
		CommandFunction function = event.getFunction(Boolean.TRUE);
		function.setIsActionRequired(Boolean.FALSE);
		function.setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		function.try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE).add(runnable);
        addEvents(event);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addEvent(EventName name, String...scriptInstructions) {
		EventBuilder event = getEventByName(name, Boolean.TRUE);
		event.getScript(Boolean.TRUE).getInstructions(Boolean.TRUE).add(scriptInstructions);
        addEvents(event);
		return this;
	}
	
	@Override
	public Object getRequest() {
		return getProperties().getRequest();
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setRequest(Object request) {
		getProperties().setRequest(request);
		return this;
	}
	
	@Override
	public Object getContext() {
		return getProperties().getContext();
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setContext(Object context) {
		getProperties().setContext(context);
		return this;
	}
	
	@Override
	public Object getUniformResourceLocatorMap() {
		return getProperties().getUniformResourceLocatorMap();
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setUniformResourceLocatorMap(Object uniformResourceLocatorMap) {
		getProperties().setUniformResourceLocatorMap(uniformResourceLocatorMap);
		return this;
	}
	
	@Override
	public Throwable getThrowable() {
		return throwable;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	public BooleanMap getDerivableFieldNameMap() {
		return derivableFieldNameMap;
	}
	
	@Override
	public BooleanMap getDerivableFieldNameMap(Boolean injectIfNull) {
		if(derivableFieldNameMap == null && Boolean.TRUE.equals(injectIfNull))
			derivableFieldNameMap = __inject__(BooleanMap.class);
		return derivableFieldNameMap;
	}
	
	public ComponentBuilder<COMPONENT> setDerivableFieldNameMap(BooleanMap derivableFieldNameMap) {
		this.derivableFieldNameMap = derivableFieldNameMap;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setDerivableFieldNames(Object... values) {
		getDerivableFieldNameMap(Boolean.TRUE).set(values);
		return this;
	}
	
	@Override
	public String getGetByIdentifierExpressionLanguageFormat() {
		return getByIdentifierExpressionLanguageFormat;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setGetByIdentifierExpressionLanguageFormat(String getByIdentifierExpressionLanguageFormat) {
		this.getByIdentifierExpressionLanguageFormat = getByIdentifierExpressionLanguageFormat;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setLinkedTo(Object linkedTo) {
		this.linkedTo = linkedTo;
		return this;
	}
	
	@Override
	public Object getLinkedTo() {
		return linkedTo;
	}
	
	@Override
	public Strings getDerivableProperties() {
		return derivableProperties;
	}
	
	@Override
	public Strings getDerivableProperties(Boolean injectIfNull) {
		if(derivableProperties == null && Boolean.TRUE.equals(injectIfNull))
			derivableProperties = __inject__(Strings.class);
		return derivableProperties;
	}
	@Override
	public ComponentBuilder<COMPONENT> setDerivableProperties(Strings derivableProperties) {
		this.derivableProperties = derivableProperties;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addDerivableProperties(Collection<String> derivableProperties) {
		getDerivableProperties(Boolean.TRUE).add(derivableProperties);
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addDerivableProperties(String... derivableProperties) {
		getDerivableProperties(Boolean.TRUE).add(derivableProperties);
		return this;
	}
	
	protected Boolean __getIsFieldNameDerivable__(String fieldName,Boolean defaultIfNull) {
		Boolean value = null;
		BooleanMap derivableFieldNameMap = getDerivableFieldNameMap();
		if(derivableFieldNameMap == null)
			value = defaultIfNull;
		else {
			value = derivableFieldNameMap.get(fieldName);
			if(value == null)
				value = defaultIfNull;
		}
		return value;
	}
	
	protected Boolean __getIsFieldNameDerivable__(String fieldName) {
		return __getIsFieldNameDerivable__(fieldName, Boolean.TRUE);
	}
	
	protected void __setRequestAndContextAndUniformResourceLocatorMapOf__(ComponentBuilder<?> component) {
		if(component.getRequest() == null)
			component.setRequest(getRequest());
		if(component.getContext() == null)
			component.setContext(getContext());
		if(component.getUniformResourceLocatorMap() == null)
			component.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
	}
	
	protected static SessionHelper __injectSessionHelper__(){
		return AbstractSessionHelperImpl.getInstance();
	}
	
	/**/
	
}
