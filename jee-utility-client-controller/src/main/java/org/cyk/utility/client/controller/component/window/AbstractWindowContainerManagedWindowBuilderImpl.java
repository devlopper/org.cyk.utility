package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionHelper;
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
import org.cyk.utility.system.action.SystemActionView;

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
	private Object request,context,uniformResourceLocatorMap;
	
	//TODO improve build logic to reduce build time
	@Override
	protected WindowBuilder __execute__() throws Exception {
		Object request = __injectValueHelper__().returnOrThrowIfBlank("request for "+getClass(), getRequest());
		Object context = getContext();
		WindowBuilder window = getWindow();
		if(window == null)
			//window = __inject__(WindowBuilder.class);
			window = __getProperty__(WindowContainerManagedProperty.WINDOW,WindowBuilder.class);
		
		WindowRenderType windowRenderType = getWindowRenderType();
		if(windowRenderType == null) {
			Class<?> windowRenderTypeClass = __inject__(RequestParameterValueMapper.class).setParameterNameAsWindowRenderTypeClass().execute().getOutputAs(Class.class);
			if(windowRenderTypeClass!=null)
				windowRenderType = (WindowRenderType) __inject__(windowRenderTypeClass);
		}
		
		if(windowRenderType == null || windowRenderType instanceof WindowRenderTypeNormal) {
			MenuBuilderMap menuMap = getMenuMap();
			if(menuMap == null)
				menuMap = (MenuBuilderMap) __inject__(SessionHelper.class).getAttributeValue(SessionAttributeEnumeration.MENU_BUILDER_MAP,request);
			if(menuMap == null) {
				menuMap = __inject__(MenuBuilderMapGetter.class).setRequest(request).execute().getOutput();
				__inject__(SessionHelper.class).setAttributeValue(SessionAttributeEnumeration.MENU_BUILDER_MAP,menuMap,request);
			}
			window.setMenuMap(menuMap);	
		}
		
		SystemAction systemAction = getSystemAction();
		if(systemAction == null)
			systemAction = __getProperty__(WindowContainerManagedProperty.SYSTEM_ACTION,SystemAction.class);
		//if(systemAction == null)
		//	systemAction = __inject__(RequestParameterValueMapper.class).setParameterNameAsActionClass().execute().getOutputAs(SystemAction.class);
		
		Object instance = __getEntity__(systemAction);
		
		if(instance!=null && systemAction!=null)
			systemAction.getEntities().add(instance);
		
		if(systemAction == null) {
			
		}else {
			if(window.getTitle()==null || __injectStringHelper__().isBlank(window.getTitle().getValue())) {
				InternalizationPhraseBuilder windowTitleInternalizationPhraseBuilder = __getWindowTitleInternalizationPhraseBuilder__(systemAction);
				if(windowTitleInternalizationPhraseBuilder == null) {
					windowTitleInternalizationPhraseBuilder = __inject__(InternalizationPhraseBuilder.class)
							.addStrings(__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER).setKeyType(InternalizationKeyStringType.NOUN))
							.addStringsByKeys(systemAction.getEntities().getElementClass());
				}
				if(windowTitleInternalizationPhraseBuilder != null)
					window.setTitleValue(windowTitleInternalizationPhraseBuilder.execute().getOutput());
			}
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
		return window;
	}
	
	public InternalizationPhraseBuilder __getWindowTitleInternalizationPhraseBuilder__(SystemAction systemAction) {
		InternalizationPhraseBuilder internalizationPhraseBuilder = __inject__(InternalizationPhraseBuilder.class)
				.addStrings(__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER)
						.setKeyType(InternalizationKeyStringType.NOUN))
				.addStrings(__inject__(InternalizationStringBuilder.class).setKey("of"))
				.addStringsByKeys(systemAction.getEntities().getElementClass());
		if(systemAction.getNextAction() != null) {
			internalizationPhraseBuilder
			.addStrings(__inject__(InternalizationStringBuilder.class).setKey("for"))
			.addStrings(__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction.getNextAction()).setKeyType(InternalizationKeyStringType.NOUN));	
		}
		return internalizationPhraseBuilder;
	}
	
	protected Object __getEntity__(SystemAction systemAction) {
		Object entity = null;
		if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete 
				|| systemAction instanceof SystemActionProcess || systemAction instanceof SystemActionView) {
			Object identifier = __injectCollectionHelper__().getFirst(systemAction.getEntitiesIdentifiers());
			if(identifier == null) {
				
			}else {
				entity = __readOne__(systemAction,systemAction.getEntities().getElementClass(), identifier,null);	
			}
			
		}else if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionAdd || systemAction instanceof SystemActionRedirect) {
			entity = __inject__(systemAction.getEntities().getElementClass());
		}
		return entity;
	}
	
	protected Object __readOne__(SystemAction systemAction,Class<?> klass,Object identifier,Properties properties) {
		return __inject__(Controller.class).readBySystemIdentifier(klass, identifier,properties);	
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
	public Object getUniformResourceLocatorMap() {
		return uniformResourceLocatorMap;
	}
	
	@Override
	public WindowContainerManagedWindowBuilder setUniformResourceLocatorMap(Object uniformResourceLocatorMap) {
		this.uniformResourceLocatorMap = uniformResourceLocatorMap;
		return this;
	}
	
	/**/
	
	protected <T> T __getProperty__(WindowContainerManagedProperty property,Class<T> aClass) {
		return __inject__(WindowContainerManagedPropertyValueGetter.class).setContainer(this).setProperty(property).execute().getOutputAs(aClass);
	}
	
	/**/
	
	public static final String FIELD_MENU_MAP = "menuMap";
	public static final String FIELD_VIEW = "view";
	public static final String FIELD_WINDOW = "window";
}
