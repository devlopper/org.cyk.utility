package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.clazz.Classes;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventBuilders;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.internationalization.InternationalizationStringMap;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.type.BooleanMap;

public interface ComponentBuilder<COMPONENT extends Component> extends FunctionWithPropertiesAsInput<COMPONENT> {
	
	Class<COMPONENT> getComponentClass();
	ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass);
	
	Classes getQualifiers();
	Classes getQualifiers(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setQualifiers(Classes qualifiers);
	ComponentBuilder<COMPONENT> addQualifiers(@SuppressWarnings("rawtypes") Collection<Class> qualifiers);
	ComponentBuilder<COMPONENT> addQualifiers(@SuppressWarnings("rawtypes") Class...qualifiers);
	
	COMPONENT getComponent();
	ComponentBuilder<COMPONENT> setComponent(COMPONENT component);
	
	ComponentBuilder<COMPONENT> setOutputProperty(Object key,Object value);
	ComponentBuilder<COMPONENT> setOutputPropertyName(Object value);
	ComponentBuilder<COMPONENT> setOutputPropertyValue(Object value);
	
	DeviceScreenArea getArea();
	DeviceScreenArea getArea(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setArea(DeviceScreenArea area);
	
	ComponentBuilder<COMPONENT> setAreaWidth(DeviceScreenDimensionProportions areaWidth);
	
	ComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>,Integer> proportionMap);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>...classes);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	ComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width);
	
	Style getLayoutItemStyle();
	Style getLayoutItemStyle(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setLayoutItemStyle(Style style);
	ComponentBuilder<COMPONENT> addLayoutItemStyleClasses(String...classes);
	
	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setRoles(ComponentRoles roles);
	ComponentBuilder<COMPONENT> addRoles(Collection<ComponentRole> roles);
	ComponentBuilder<COMPONENT> addRoles(ComponentRole...roles);
	
	@Deprecated
	Boolean getIsTargetModelToBeBuilt();
	@Deprecated
	ComponentBuilder<COMPONENT> setIsTargetModelToBeBuilt(Boolean isTargetModelToBeBuilt);
	
	Strings getDerivableProperties();
	Strings getDerivableProperties(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setDerivableProperties(Strings derivableProperties);
	ComponentBuilder<COMPONENT> addDerivableProperties(Collection<String> derivableProperties);
	ComponentBuilder<COMPONENT> addDerivableProperties(String...derivableProperties);
	
	Objects getUpdatables();
	Objects getUpdatables(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setUpdatables(Objects updatables);
	ComponentBuilder<COMPONENT> addUpdatables(Collection<Object> updatables);
	ComponentBuilder<COMPONENT> addUpdatables(Object...updatables);
	/*
	InternalizationStringBuilderByStringMap getInternalizationStringMap();
	InternalizationStringBuilderByStringMap getInternalizationStringMap(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setInternalizationStringMap(InternalizationStringBuilderByStringMap internalizationStringMap);
	ComponentBuilder<COMPONENT> setInternalizationKeyValue(String key,String value);
	
	InternalizationStringBuilder getNameInternalization();
	InternalizationStringBuilder getNameInternalization(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setNameInternalization(InternalizationStringBuilder nameInternalization);
	ComponentBuilder<COMPONENT> setNameInternalizationKeyValue(String nameInternalizationKeyValue);
	*/
	InternationalizationStringMap getInternationalizationStringMap();
	InternationalizationStringMap getInternationalizationStringMap(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setInternationalizationStringMap(InternationalizationStringMap internationalizationStringMap);
	
	InternationalizationString getNameInternationalization();
	InternationalizationString getNameInternationalization(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setNameInternationalization(InternationalizationString nameInternationalization);
	
	EventBuilders getEvents();
	EventBuilders getEvents(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setEvents(EventBuilders events);
	ComponentBuilder<COMPONENT> addEvents(Collection<EventBuilder> events);
	ComponentBuilder<COMPONENT> addEvents(EventBuilder...events);
	EventBuilder getEventByName(EventName name,Boolean injectIfNull);
	EventBuilder getEventByName(EventName name);
	ComponentBuilder<COMPONENT> addEvent(EventName name,Runnable runnable,Object...updatables);
	ComponentBuilder<COMPONENT> addEvent(EventName name,String...scriptInstructions);
	
	Object getRequest();
	ComponentBuilder<COMPONENT> setRequest(Object request);

	Object getContext();
	ComponentBuilder<COMPONENT> setContext(Object context);
	
	Object getUniformResourceLocatorMap();
	ComponentBuilder<COMPONENT> setUniformResourceLocatorMap(Object uniformResourceLocatorMap);
	
	Throwable getThrowable();
	ComponentBuilder<COMPONENT> setThrowable(Throwable throwable);
	
	BooleanMap getDerivableFieldNameMap();
	BooleanMap getDerivableFieldNameMap(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setDerivableFieldNameMap(BooleanMap derivableFieldNameMap);
	ComponentBuilder<COMPONENT> setDerivableFieldNames(Object...values);
	
	String getGetByIdentifierExpressionLanguageFormat();
	ComponentBuilder<COMPONENT> setGetByIdentifierExpressionLanguageFormat(String getByIdentifierExpressionLanguageFormat);
	
	Object getLinkedTo();
	ComponentBuilder<COMPONENT> setLinkedTo(Object linkedTo);
}
