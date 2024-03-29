package org.cyk.utility.client.controller.data;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.field.Fields;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.system.action.SystemActionRelatedClassGetter;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;

@ApplicationScoped
public class DataHelperImpl extends AbstractHelper implements DataHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<Data> getDataClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Strings names = __inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(entityClass).setSystemActionClass(systemActionClass).setExtendedInterface(Data.class)
				.setDefaultSuffix("Default").execute().getOutput();
		CollectionHelper.swap(names, 1, 2);
		Class<Data> clazz = __inject__(SystemActionRelatedClassGetter.class).setNames(names).execute().getOutput();
		return clazz;
	}
	
	@Override
	public Class<Data> getDataClass(SystemAction systemAction) {
		return systemAction == null || systemAction.getEntities() == null ? null : getDataClass(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
	@Override
	public Data injectData(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<?> clazz = getDataClass(entityClass, systemActionClass);
		return clazz == null ? null : (Data)__inject__(clazz);
	}
	
	@Override
	public Data injectData(SystemAction systemAction) {
		return systemAction == null || systemAction.getEntities() == null ? null : injectData(systemAction.getEntities().getElementClass(), systemAction.getClass());
	}
	
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

	@Override
	public Fields getPropertiesFields(Class<?> klass) {
		return (Fields) __inject__(Fields.class).add(FieldHelper.filter(klass, "^PROPERTY_", null));
	}
	
	@Override
	public Strings getPropertiesFieldsNames(Class<?> klass) {
		Strings names = null;
		Fields fields = getPropertiesFields(klass);
		if(CollectionHelper.isNotEmpty(fields)) {
			names = __inject__(Strings.class);
			for(Field index : fields.get())
				names.add(FieldHelper.readStatic(index).toString());
		}
		return names;
	}
}
