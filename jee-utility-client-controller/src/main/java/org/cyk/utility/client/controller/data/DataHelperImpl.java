package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRelatedClassGetter;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;

@Singleton
public class DataHelperImpl extends AbstractHelper implements DataHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<Row> getRowClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Row> clazz = __inject__(SystemActionRelatedClassGetter.class).setNamesGetter(
				__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(entityClass).setSystemActionClass(systemActionClass).setNameSuffix("Row")
				.setExtendedInterface(RowData.class).setDefaultSuffix("Default")
				).execute().getOutput();
		return clazz;
	}
	
	@Override
	public Class<Row> getRowClass(SystemAction systemAction) {
		return systemAction == null || systemAction.getEntities() == null ? null : getRowClass(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
	@Override
	public Row injectRow(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<?> clazz = getRowClass(entityClass, systemActionClass);
		return clazz == null ? null : (Row)__inject__(clazz);
	}
	
	@Override
	public Row injectRow(SystemAction systemAction) {
		return systemAction == null || systemAction.getEntities() == null ? null : injectRow(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
	@Override
	public Class<Form> getFormClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Form> clazz = null;
		clazz = __inject__(SystemActionRelatedClassGetter.class).setNamesGetter(
				__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(entityClass).setSystemActionClass(systemActionClass).setNameSuffix("Form")
				.setExtendedInterface(FormData.class).setDefaultSuffix("Default")
				).execute().getOutput();
		return clazz;
	}
	
	@Override
	public Class<Form> getFormClass(SystemAction systemAction) {
		return systemAction == null  || systemAction.getEntities() == null ? null : getFormClass(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
	@Override
	public Form injectForm(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<?> clazz = getFormClass(entityClass, systemActionClass);
		return clazz == null ? null : (Form)__inject__(clazz);
	}
	
	@Override
	public Form injectForm(SystemAction systemAction) {
		return systemAction == null  || systemAction.getEntities() == null ? null : injectForm(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
}
