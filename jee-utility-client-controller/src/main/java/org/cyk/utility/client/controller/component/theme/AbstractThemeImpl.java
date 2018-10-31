package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.view.ViewMap;

public abstract class AbstractThemeImpl extends AbstractObject implements Theme,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewMap viewMap;
	private ThemeTemplate template;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifier(__getIdentifier__());
		setTemplate(__inject__(ThemeTemplate.class));
		getTemplate().setIdentifier(__getTemplateIdentifier__());
	}
	
	protected abstract String __getIdentifier__();
	protected abstract String __getTemplateIdentifier__();
	
	@Override
	public ViewMap getViewMap() {
		return viewMap;
	}
	
	@Override
	public ViewMap getViewMap(Boolean injectIfNull) {
		return (ViewMap) __getInjectIfNull__(FIELD_VIEW_MAP, injectIfNull);
	}

	@Override
	public Theme setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
		return this;
	}

	@Override
	public Theme mapViews(Object...objects) {
		getViewMap(Boolean.TRUE).set(objects);
		return this;
	}
	
	@Override
	public ThemeTemplate getTemplate() {
		return template;
	}

	@Override
	public Theme setTemplate(ThemeTemplate template) {
		this.template = template;
		return this;
	}

	public static final String FIELD_VIEW_MAP = "viewMap";
}
