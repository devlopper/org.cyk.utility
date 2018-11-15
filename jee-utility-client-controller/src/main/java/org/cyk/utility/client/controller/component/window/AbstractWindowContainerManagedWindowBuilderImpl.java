package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractWindowContainerManagedWindowBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowBuilder> implements WindowContainerManagedWindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private MenuBuilderMap menuMap;
	private ViewBuilder view;
	private SystemAction systemAction;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		Class<?> clazz = __inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(clazz);
		
		Object instance = null;
		if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete) {
			Long identifier = __inject__(RequestParameterValueMapper.class).setParameterName(FieldName.IDENTIFIER).execute().getOutputAs(Long.class);
			instance = __injectCollectionHelper__().getFirst(__inject__(InstanceGetter.class).setClazz(clazz).setFieldName(FieldName.IDENTIFIER)
					.setValueUsageType(ValueUsageType.SYSTEM).setValue(identifier).execute().getOutput());	
		}else if(systemAction instanceof SystemActionCreate) {
			instance = __inject__(clazz);
		}
		
		if(instance!=null && systemAction!=null)
			systemAction.getEntities(Boolean.TRUE).add(instance);
	}
	
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
