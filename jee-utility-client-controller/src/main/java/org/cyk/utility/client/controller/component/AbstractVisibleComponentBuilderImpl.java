package org.cyk.utility.client.controller.component;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.DeviceScreenArea;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

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
	public VisibleComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (VisibleComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> addStyleClasses(String... classes) {
		getStyle(Boolean.TRUE).addClasses(classes);
		return this;
	}

	public static final String FIELD_STYLE = "style";
}
