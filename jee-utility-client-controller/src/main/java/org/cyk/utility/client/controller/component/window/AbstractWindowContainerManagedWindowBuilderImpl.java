package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowBuilder> implements WindowContainerManagedWindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private MenuBuilderMap menuMap;
	private ViewBuilder view;
	private SystemAction systemAction;
	
	@Override
	protected WindowBuilder __execute__() throws Exception {
		WindowBuilder window = __inject__(WindowBuilder.class);
		MenuBuilderMap menuMap = getMenuMap();
		if(menuMap == null)
			menuMap = __inject__(MenuBuilderMapGetter.class).execute().getOutput();
		window.setMenuMap(menuMap);
		/*
		Object identifier = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("identifier");
		Object action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("action");
		
		window.setTitleValue("TITLE??? : "+action);
		*/
		ViewBuilder view = getView();
		window.setView(view);
		return window;
	}
	
	@Override
	public MenuBuilderMap getMenuMap() {
		return menuMap;
	}
	
	@Override
	public MenuBuilderMap getMenuMap(Boolean injectIfNull) {
		return (MenuBuilderMap) __getInjectIfNull__(FIELD_MENU_MAP, injectIfNull);
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setMenuMap(MenuBuilderMap menuMap) {
		this.menuMap = menuMap;
		return this;
	}
	
	@Override
	public ViewBuilder getView() {
		return view;
	}
	
	@Override
	public ViewBuilder getView(Boolean injectIfNull) {
		return (ViewBuilder) __getInjectIfNull__(FIELD_VIEW, injectIfNull);
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
	public static final String FIELD_MENU_MAP = "menuMap";
	public static final String FIELD_VIEW = "view";
}
