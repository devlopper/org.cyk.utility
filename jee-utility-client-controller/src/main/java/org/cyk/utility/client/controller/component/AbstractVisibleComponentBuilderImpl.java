package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

	private StyleBuilder style;
	private Object tooltip;
	private Theme theme;
	
	@Override
	protected void __execute__(COMPONENT component) {
		super.__execute__(component);
		StyleBuilder style = getStyle();
		if(style!=null) {
			ComponentRoles roles = component.getRoles();
			if(__injectCollectionHelper__().isNotEmpty(roles)) {
				for(ComponentRole index : roles.get()) {
					String styleClass = __inject__(ComponentRoleStyleClassGetter.class).setRole(index).execute().getOutput();
					if(__inject__(StringHelper.class).isNotBlank(styleClass))
						style.addClasses(styleClass);
				}
			}	
			
			component.setStyle(style.execute().getOutput());
		}
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
			Object request = getRequest();
			if(request!=null)
				theme = (Theme) __inject__(SessionAttributeGetter.class).setRequest(request).setAttribute(SessionAttributeEnumeration.THEME).execute().getOutput();
		}
		component.setTheme(theme);
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
	
	@Override
	public VisibleComponentBuilder<COMPONENT> addStyleValues(String... values) {
		getStyle(Boolean.TRUE).addValues(values);
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
