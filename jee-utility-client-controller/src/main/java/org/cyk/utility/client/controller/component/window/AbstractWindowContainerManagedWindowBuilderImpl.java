package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.internationalization.InternalizationKeyStringType;
import org.cyk.utility.internationalization.InternalizationPhraseBuilder;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.string.Case;
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
	private Class<? extends Form> formClass;
	private Class<? extends Row> rowClass;
	
	@Override
	protected WindowBuilder __execute__() throws Exception {
		WindowBuilder window = __inject__(WindowBuilder.class);
		MenuBuilderMap menuMap = getMenuMap();
		if(menuMap == null)
			menuMap = __inject__(MenuBuilderMapGetter.class).execute().getOutput();
		window.setMenuMap(menuMap);
		
		SystemAction systemAction = getSystemAction();
		if(systemAction == null)
			systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		
		Object instance = null;
		if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete) {
			Long identifier = __inject__(RequestParameterValueMapper.class).setParameterName(FieldName.IDENTIFIER).execute().getOutputAs(Long.class);
			instance = __injectCollectionHelper__().getFirst(__inject__(InstanceGetter.class).setClazz(systemAction.getEntities().getElementClass())
					.setFieldName(FieldName.IDENTIFIER)
					.setValueUsageType(ValueUsageType.SYSTEM).setValue(identifier).execute().getOutput());	
		}else if(systemAction instanceof SystemActionCreate) {
			instance = __inject__(systemAction.getEntities().getElementClass());
		}
		
		if(instance!=null && systemAction!=null)
			systemAction.getEntities().add(instance);
		
		if(systemAction == null) {
			
		}else {
			window.setTitleValue(
				__inject__(InternalizationPhraseBuilder.class).addStringsByKeys(
					__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER).setKeyType(InternalizationKeyStringType.NOUN)
					,systemAction.getEntities().getElementClass()).execute().getOutput()
				);
			__execute__(window,systemAction,__getFormClass__(getFormClass()),__getRowClass__(getRowClass()));
		}
		
		
		ViewBuilder view = getView();
		window.setView(view);
		
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
}
