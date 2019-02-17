package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.internationalization.InternalizationKeyStringType;
import org.cyk.utility.internationalization.InternalizationPhraseBuilder;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.string.Case;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionRedirect;
import org.cyk.utility.system.action.SystemActionUpdate;

public abstract class AbstractWindowContainerManagedWindowBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<WindowBuilder> implements WindowContainerManagedWindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private WindowBuilder window;
	private MenuBuilderMap menuMap;
	private ViewBuilder view;
	private SystemAction systemAction;
	private Class<? extends Form> formClass;
	private Class<? extends Row> rowClass;
	private WindowContainerManaged windowContainerManaged;
	private WindowRenderType windowRenderType;
	private Object request,context,navigationIdentifierStringMap;
	
	@Override
	protected WindowBuilder __execute__() throws Exception {
		//DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		Object request = getRequest();
		Object context = getContext();
		WindowBuilder window = getWindow();
		if(window == null)
			window = __inject__(WindowBuilder.class);
		WindowRenderType windowRenderType = getWindowRenderType();
		if(windowRenderType == null) {
			Class<?> windowRenderTypeClass = __inject__(RequestParameterValueMapper.class).setParameterNameAsWindowRenderTypeClass().execute().getOutputAs(Class.class);
			if(windowRenderTypeClass!=null)
				windowRenderType = (WindowRenderType) __inject__(windowRenderTypeClass);
		}
		if(windowRenderType == null || windowRenderType instanceof WindowRenderTypeNormal) {
			MenuBuilderMap menuMap = getMenuMap();
			if(menuMap == null)
				menuMap = __inject__(MenuBuilderMapGetter.class).execute().getOutput();
			//window.setMenuMap(menuMap);	
		}
		
		SystemAction systemAction = getSystemAction();
		if(systemAction == null)
			systemAction = __inject__(RequestParameterValueMapper.class).setParameterNameAsActionClass().execute().getOutputAs(SystemAction.class);
		
		Object instance = null;
		if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete || systemAction instanceof SystemActionProcess) {
			Long identifier = (Long) __injectCollectionHelper__().getFirst(systemAction.getEntitiesIdentifiers());
			instance = __inject__(Controller.class).readOne(systemAction.getEntities().getElementClass(), identifier);
		}else if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionAdd || systemAction instanceof SystemActionRedirect) {
			instance = __inject__(systemAction.getEntities().getElementClass());
		}
		
		if(instance!=null && systemAction!=null)
			systemAction.getEntities().add(instance);
		
		if(systemAction == null) {
			
		}else {
			if(window.getTitle()==null || __injectStringHelper__().isBlank(window.getTitle().getValue()))
				window.setTitleValue(
					__inject__(InternalizationPhraseBuilder.class)
						.addStrings(__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER).setKeyType(InternalizationKeyStringType.NOUN))
						.addStringsByKeys(systemAction.getEntities().getElementClass()).execute().getOutput()
					);
			__execute__(window,systemAction,__getFormClass__(getFormClass()),__getRowClass__(getRowClass()));
		}
		
		
		ViewBuilder view = getView();
		//if(view == null)
		//	view = __inject__(ViewBuilder.class);
		if(view == null) {
			
		}else {
			if(view.getRequest() == null)
				view.setRequest(request);	
			if(view.getContext() == null)
				view.setContext(context);	
		}
		window.setView(view);
		//System.out.println("build window container managed window : "+__inject__(DurationStringBuilder.class).setDuration(durationBuilder.setEndNow().execute().getOutput()).execute().getOutput());
		return window;
	}
	
	protected abstract void __execute__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass);
	
	protected Class<? extends Form> __getFormClass__(Class<? extends Form> aClass){
		return aClass;
	}
	
	protected Class<? extends Row> __getRowClass__(Class<? extends Row> aClass){
		return aClass;
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
	public WindowBuilder getWindow() {
		return window;
	}
	
	@Override
	public WindowBuilder getWindow(Boolean injectIfNull) {
		return (WindowBuilder) __getInjectIfNull__(FIELD_WINDOW, injectIfNull);
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setWindow(WindowBuilder window) {
		this.window = window;
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
	
	@Override
	public Class<? extends Form> getFormClass() {
		return formClass;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setFormClass(Class<? extends Form> formClass) {
		this.formClass = formClass;
		return this;
	}
	
	@Override
	public Class<? extends Form> getFormClass(Boolean getFromRequestIfNull) {
		Class<? extends Form> clazz = getFormClass();
		//if(clazz == null)
		//	setFormClass(clazz = __inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class));
		return clazz;
	}
	
	@Override
	public Class<? extends Row> getRowClass() {
		return rowClass;
	}
	
	@Override
	public Class<? extends Row> getRowClass(Boolean getFromRequestIfNull) {
		Class<? extends Row> clazz = getRowClass();
		//if(clazz == null)
		//	setRowClass(clazz = __inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class));
		return clazz;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setRowClass(Class<? extends Row> rowClass) {
		this.rowClass = rowClass;
		return this;
	}
	
	@Override
	public WindowContainerManaged getWindowContainerManaged() {
		return windowContainerManaged;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setWindowContainerManaged(WindowContainerManaged windowContainerManaged) {
		this.windowContainerManaged = windowContainerManaged;
		return this;
	}
	
	@Override
	public WindowRenderType getWindowRenderType() {
		return windowRenderType;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setWindowRenderType(WindowRenderType windowRenderType) {
		this.windowRenderType = windowRenderType;
		return this;
	}
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	@Override
	public Object getContext() {
		return context;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setContext(Object context) {
		this.context = context;
		return this;
	}
	
	@Override
	public Object getNavigationIdentifierStringMap() {
		return navigationIdentifierStringMap;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setNavigationIdentifierStringMap(Object navigationIdentifierStringMap) {
		this.navigationIdentifierStringMap = navigationIdentifierStringMap;
		return this;
	}
	
	/*
	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	@Override
	public Class<?> getEntityClass(Boolean getFromRequestIfNull) {
		Class<?> clazz = getEntityClass();
		if(clazz == null)
			setEntityClass(clazz = __inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class));
		return clazz;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
		return this;
	}
	*/
	public static final String FIELD_MENU_MAP = "menuMap";
	public static final String FIELD_VIEW = "view";
	public static final String FIELD_WINDOW = "window";
}
