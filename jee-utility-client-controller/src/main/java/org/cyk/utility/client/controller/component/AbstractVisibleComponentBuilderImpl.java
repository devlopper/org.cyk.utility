package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.css.CascadeStyleSheetHelperImpl;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.DeviceScreenArea;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

	private Style style;
	private Object tooltip;
	private Theme theme;
	
	@Override
	protected void __execute__(COMPONENT component) {
		super.__execute__(component);
		Object request = getRequest();
		//Style
		Style style = getStyle();		
		ComponentRoles roles = component.getRoles();
		if(CollectionHelper.isNotEmpty(roles)) {
			if(style == null)
				style = __inject__(Style.class);
			Collection<String> styleClasses = CascadeStyleSheetHelperImpl.__getStyleClassesFromRoles__(roles.get());
			if(CollectionHelper.isNotEmpty(styleClasses))
				style.getClasses(Boolean.TRUE).add(styleClasses);
		}		
		if(style!=null) {
			if(component.getStyle() == null)
				component.setStyle(style);
			else {
				component.getStyle().getClasses(Boolean.TRUE).add(style.getClasses());
				component.getStyle().getValues(Boolean.TRUE).add(style.getValues());
			}
		}
		
		//Area
		DeviceScreenArea area = getArea();
		if(area!=null) {
			component.setArea(area);
		}
		
		if(component.getTooltip() == null) {
			Object tooltip = getTooltip();
			component.setTooltip(tooltip);
		}
		
		Theme theme = getTheme();
		if(theme == null) {
			if(request != null)
				theme = (Theme) SessionHelper.getAttributeValueFromRequest(SessionAttributeEnumeration.THEME,request);
		}
		component.setTheme(theme);
	}
	
	@Override
	public Style getStyle() {
		return style;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setStyle(Style style) {
		this.style = style;
		return this;
	}
	
	@Override
	public Style getStyle(Boolean injectIfNull) {
		if(style == null && Boolean.TRUE.equals(injectIfNull))
			style = __inject__(Style.class);
		return style;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (VisibleComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> addStyleClasses(String... classes) {
		getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add(classes);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> addStyleValues(String... values) {
		getStyle(Boolean.TRUE).getValues(Boolean.TRUE).add(values);
		return this;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setTooltip(Object tooltip) {
		this.tooltip = tooltip;
		return this;
	}
	
	@Override
	public Object getTooltip() {
		return tooltip;
	}
	
	@Override
	public Theme getTheme() {
		return theme;
	}
	
	@Override
	public VisibleComponentBuilder<COMPONENT> setTheme(Theme theme) {
		this.theme = theme;
		return this;
	}

	public static final String FIELD_STYLE = "style";
}
