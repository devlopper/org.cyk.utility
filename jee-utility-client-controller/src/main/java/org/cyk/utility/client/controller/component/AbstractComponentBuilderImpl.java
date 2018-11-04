package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractComponentBuilderImpl<COMPONENT extends Component> extends AbstractFunctionWithPropertiesAsInputImpl<COMPONENT> implements ComponentBuilder<COMPONENT> , Serializable {
	private static final long serialVersionUID = 1L;

	private Class<COMPONENT> componentClass;
	private DeviceScreenArea area;
	private StyleBuilder layoutItemStyle;
	private ComponentRoles roles;
	private Boolean isTargetModelToBeBuilt;
	
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
		COMPONENT component = __inject__(componentClass);
		__inject__(ComponentBuilderExecuteListenerBefore.class).setObject(this).setComponent(component).execute();
		ComponentRoles roles = getRoles();
		component.setRoles(roles);
		__execute__(component);
		__inject__(ComponentBuilderExecuteListenerAfter.class).setObject(this).setComponent(component).execute();
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
		return null;
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
	
	public static final String FIELD_AREA = "area";
	public static final String FIELD_LAYOUT_ITEM_STYLE = "layoutItemStyle";
	public static final String FIELD_ROLES = "roles";
}
