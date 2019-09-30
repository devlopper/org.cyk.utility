package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.system.action.SystemActionRelatedClassGetter;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;

@ApplicationScoped
public class WindowHelperImpl extends AbstractHelper implements WindowHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass) {
		Class<WindowContainerManagedWindowBuilder> clazz = __inject__(SystemActionRelatedClassGetter.class).setNamesGetter(
				__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(entityClass).setSystemActionClass(systemActionClass).setNameSuffix("WindowBuilder")
				.setExtendedInterface(WindowContainerManagedWindowBuilder.class)
				.setDefaultSuffix("DataDefault")
				).execute().getOutput();
		
		return clazz;
	}

	@Override
	public Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(SystemAction systemAction) {
		return systemAction == null || systemAction.getEntities() == null ? null 
				: getWindowContainerManagedWindowBuilderClass(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
	@Override
	public WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(Class<?> entityClass,Class<? extends SystemAction> systemActionClass) {
		Class<WindowContainerManagedWindowBuilder> clazz = getWindowContainerManagedWindowBuilderClass(entityClass, systemActionClass);
		return clazz == null ? null : __inject__(clazz);
	}
	
	@Override
	public WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder(SystemAction systemAction) {
		WindowContainerManagedWindowBuilder builder = systemAction == null || systemAction.getEntities() == null ? null 
				: injectWindowContainerManagedWindowBuilder(systemAction.getEntities().getElementClass(), systemAction.getClass());
		if(builder!=null)
			builder.setSystemAction(systemAction);
		return builder;
	}
	
}
