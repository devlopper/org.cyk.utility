package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

	private DeviceScreenArea area;
	private StyleBuilder style;
	
	@Override
	protected void __execute__(COMPONENT component) {
		super.__execute__(component);
		StyleBuilder style = getStyle();
		if(style!=null)
			component.setStyle(style.execute().getOutput());
		DeviceScreenArea area = getArea();
		if(area!=null) {
			component.setArea(area);
		}
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
	public VisibleComponentBuilder<COMPONENT> setArea(DeviceScreenArea area) {
		this.area = area;
		return this;
	}
	
	@Override
	public StyleBuilder getStyle() {
		return style;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setStyle(StyleBuilder style) {
		this.style = style;
		return this;
	}
	
	@Override
	public StyleBuilder getStyle(Boolean injectIfNull) {
		return (StyleBuilder) __getInjectIfNull__(FIELD_STYLE, injectIfNull);
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidth(DeviceScreenDimensionProportions areaWidth) {
		getArea(Boolean.TRUE).setWidthProportions(areaWidth);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>, Integer> proportionMap) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setMap(proportionMap);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setAllClasses(_default, television, desktop, tablet, phone);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>... classes) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setByClasses(proportion, classes);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes) {
		getArea(Boolean.TRUE).getWidthProportions(Boolean.TRUE).setByClasses(proportion, classes);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width) {
		return setAreaWidthProportions(width, width, width, width, null);
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (VisibleComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}

	public static final String FIELD_AREA = "area";
	public static final String FIELD_STYLE = "style";
}
