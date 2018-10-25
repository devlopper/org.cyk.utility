package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

	private VisibleComponentArea area;
	private StyleBuilder style;
	
	@Override
	protected void __execute__(COMPONENT component) {
		super.__execute__(component);
		StyleBuilder style = getStyle();
		if(style!=null)
			component.setStyle(style.execute().getOutput());
		VisibleComponentArea area = getArea();
		if(area!=null) {
			component.setArea(area);
		}
	}
	
	@Override
	public VisibleComponentArea getArea() {
		return area;
	}

	@Override
	public VisibleComponentArea getArea(Boolean injectIfNull) {
		return (VisibleComponentArea) __getInjectIfNull__(FIELD_AREA, injectIfNull);
	}

	@Override
	public VisibleComponentBuilder<COMPONENT> setArea(VisibleComponentArea area) {
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
	public VisibleComponentBuilder<COMPONENT> setAreaWidth(VisibleComponentAreaDimension areaWidth) {
		getArea(Boolean.TRUE).setWidth(areaWidth);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>, Integer> proportionMap) {
		getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportionMap(proportionMap);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(_default, television, desktop, tablet, phone);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>... classes) {
		getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(proportion, classes);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes) {
		getArea(Boolean.TRUE).getWidth(Boolean.TRUE).setProportions(proportion, classes);
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
