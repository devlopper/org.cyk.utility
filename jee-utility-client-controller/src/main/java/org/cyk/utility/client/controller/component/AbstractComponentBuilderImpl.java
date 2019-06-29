package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.Classes;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventBuilders;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.internationalization.InternalizationStringBuilderByStringMap;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.Strings;
import org.cyk.utility.type.BooleanMap;

public abstract class AbstractComponentBuilderImpl<COMPONENT extends Component> extends AbstractFunctionWithPropertiesAsInputImpl<COMPONENT> implements ComponentBuilder<COMPONENT> , Serializable {
	private static final long serialVersionUID = 1L;

	private Class<COMPONENT> componentClass;
	private Classes qualifiers;
	private COMPONENT component;
	private DeviceScreenArea area;
	private StyleBuilder layoutItemStyle;
	private ComponentRoles roles;
	private Boolean isTargetModelToBeBuilt;
	private Objects updatables;
	private InternalizationStringBuilderByStringMap internalizationStringMap;
	private EventBuilders events;
	private Throwable throwable;
	private BooleanMap derivableFieldNameMap;
	private String getByIdentifierExpressionLanguageFormat;
	private Strings derivableProperties;
	private Object linkedTo;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setComponentClass(__getComponentClass__());
		__inject__(ComponentBuilderPostConstructListener.class).setObject(this).execute();
	}
	
	protected Class<COMPONENT> __getComponentClass__(){
		return (Class<COMPONENT>) __injectClassHelper__().getByName(__injectClassHelper__().getParameterAt(getClass(), 0, Object.class).getName());
	}
	
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
		__inject__(ComponentBuilderExecuteListenerBefore.class).setObject(this).setComponent(component).execute();
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
		__inject__(ComponentBuilderExecuteListenerAfter.class).setObject(this).setComponent(component).execute();
		
		Throwable throwable = getThrowable();
		component.setThrowable(throwable);
		if(component.getThrowable() != null)
			component.setThrowableInternalizationMessage(__inject__(InternalizationStringBuilder.class).setKeyValue(component.getThrowable()).execute().getOutput());
		return component;
	}
	
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
		return (Classes) __getInjectIfNull__(FIELD_QUALIFIERS, injectIfNull);
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
		return (DeviceScreenArea) __getInjectIfNull__(FIELD_AREA, injectIfNull);
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
	public ComponentBuilder<COMPONENT> setLayoutItemStyle(StyleBuilder style) {
		this.layoutItemStyle = style;
		return this;
	}
	
	@Override
	public StyleBuilder getLayoutItemStyle() {
		return layoutItemStyle;
	}
	
	@Override
	public StyleBuilder getLayoutItemStyle(Boolean injectIfNull) {
		return (StyleBuilder) __getInjectIfNull__(FIELD_LAYOUT_ITEM_STYLE, injectIfNull);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> addLayoutItemStyleClasses(String... classes) {
		getLayoutItemStyle(Boolean.TRUE).addClasses(classes);
		return this;
	}
	
	@Override
	public ComponentRoles getRoles() {
		return roles;
	}
	
	@Override
	public ComponentRoles getRoles(Boolean injectIfNull) {
		return (ComponentRoles) __getInjectIfNull__(FIELD_ROLES, injectIfNull);
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
		return (Objects) __getInjectIfNull__(FIELD_UPDATABLES, injectIfNull);
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
		return (InternalizationStringBuilderByStringMap) __getInjectIfNull__(FIELD_INTERNALIZATION_STRING_MAP, injectIfNull);
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
	
	@Override
	public EventBuilders getEvents() {
		return events;
	}
	
	@Override
	public EventBuilders getEvents(Boolean injectIfNull) {
		return (EventBuilders) __getInjectIfNull__(FIELD_EVENTS, injectIfNull);
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
		return (BooleanMap) __getInjectIfNull__(FIELD_DERIVABLE_FIELD_NAME_MAP, injectIfNull);
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
		return (Strings) __getInjectIfNull__(FIELD_DERIVABLE_PROPERTIES, injectIfNull);
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
	
	public static final String FIELD_AREA = "area";
	public static final String FIELD_DERIVABLE_PROPERTIES = "derivableProperties";
	public static final String FIELD_QUALIFIERS = "qualifiers";
	public static final String FIELD_LAYOUT_ITEM_STYLE = "layoutItemStyle";
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_UPDATABLES = "updatables";
	public static final String FIELD_EVENTS = "events";
	public static final String FIELD_INTERNALIZATION_STRING_MAP = "internalizationStringMap";
	public static final String FIELD_DERIVABLE_FIELD_NAME_MAP = "derivableFieldNameMap";
}
