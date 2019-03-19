package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventBuilders;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.internationalization.InternalizationStringBuilderByStringMap;
import org.cyk.utility.object.Objects;
import org.cyk.utility.type.BooleanMap;

public interface ComponentBuilder<COMPONENT extends Component> extends FunctionWithPropertiesAsInput<COMPONENT> {
	
	Class<COMPONENT> getComponentClass();
	ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass);
	
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
	
	StyleBuilder getLayoutItemStyle();
	StyleBuilder getLayoutItemStyle(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setLayoutItemStyle(StyleBuilder style);
	ComponentBuilder<COMPONENT> addLayoutItemStyleClasses(String...classes);
	
	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setRoles(ComponentRoles roles);
	ComponentBuilder<COMPONENT> addRoles(Collection<ComponentRole> roles);
	ComponentBuilder<COMPONENT> addRoles(ComponentRole...roles);
	
	Boolean getIsTargetModelToBeBuilt();
	ComponentBuilder<COMPONENT> setIsTargetModelToBeBuilt(Boolean isTargetModelToBeBuilt);
	
	Objects getUpdatables();
	Objects getUpdatables(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setUpdatables(Objects updatables);
	ComponentBuilder<COMPONENT> addUpdatables(Collection<Object> updatables);
	ComponentBuilder<COMPONENT> addUpdatables(Object...updatables);
	
	InternalizationStringBuilderByStringMap getInternalizationStringMap();
	InternalizationStringBuilderByStringMap getInternalizationStringMap(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setInternalizationStringMap(InternalizationStringBuilderByStringMap internalizationStringMap);
	ComponentBuilder<COMPONENT> setInternalizationKeyValue(String key,String value);
	
	InternalizationStringBuilder getNameInternalization();
	InternalizationStringBuilder getNameInternalization(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setNameInternalization(InternalizationStringBuilder nameInternalization);
	ComponentBuilder<COMPONENT> setNameInternalizationKeyValue(String nameInternalizationKeyValue);
	
	EventBuilders getEvents();
	EventBuilders getEvents(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setEvents(EventBuilders events);
	ComponentBuilder<COMPONENT> addEvents(Collection<EventBuilder> events);
	ComponentBuilder<COMPONENT> addEvents(EventBuilder...events);
	EventBuilder getEventByName(EventName name,Boolean injectIfNull);
	EventBuilder getEventByName(EventName name);
	ComponentBuilder<COMPONENT> addEvent(EventName name,Runnable runnable);
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
