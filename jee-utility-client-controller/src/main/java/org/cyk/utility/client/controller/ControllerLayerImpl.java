package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.layer.SystemLayerController;

public class ControllerLayerImpl extends AbstractSingleton implements ControllerLayer,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		Class<ControllerEntity<ENTITY>> aClass = (Class<ControllerEntity<ENTITY>>) __inject__(SystemLayerController.class).getInterfaceClassFromEntityClassName(entityClass);
		return aClass;
	}
	
	@Override
	public <ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		Class<ControllerEntity<ENTITY>> aClass = getInterfaceClassFromEntityClass(entityClass);
		ControllerEntity<ENTITY> persistence = null;
		if(aClass == null)
			System.err.println("No controller interface found for controller entity class "+entityClass);
		else
			persistence = (ControllerEntity<ENTITY>) __inject__(SystemLayerController.class).injectInterfaceClassFromEntityClassName(entityClass);
		return persistence;
	}

	@Override
	public <ENTITY> Class<ControllerEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : getInterfaceClassFromEntityClass((Class<ENTITY>)entity.getClass());
	}

	@Override
	public <ENTITY> ControllerEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : (ControllerEntity<ENTITY>) injectInterfaceClassFromEntityClass(entity.getClass());
	}

	@Override
	public Class<WindowContainerManagedWindowBuilder> getWindowContainerManagedWindowBuilderClass(Class<?> entityClass,Class<? extends SystemAction> systemActionClass) {
		Class<WindowContainerManagedWindowBuilder> clazz = null;
		if(entityClass!=null) {
			String name = null;
			if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(entityClass.getName())) {
				if(SystemActionCreate.class.isAssignableFrom(systemActionClass) || SystemActionUpdate.class.isAssignableFrom(systemActionClass) || SystemActionDelete.class.isAssignableFrom(systemActionClass))
					name = EDIT;
				else if(SystemActionList.class.isAssignableFrom(systemActionClass))
					name = LIST;
				if(__inject__(StringHelper.class).isNotBlank(name))
					clazz = (Class<WindowContainerManagedWindowBuilder>) __inject__(ClassHelper.class).getByName(entityClass.getName()+name+WINDOW_BUILDER);
			}
		}
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
		return systemAction == null || systemAction.getEntities() == null ? null 
				: injectWindowContainerManagedWindowBuilder(systemAction.getEntities().getElementClass(), systemAction.getClass()).setSystemAction(systemAction);
	}
	
	@Override
	public WindowContainerManagedWindowBuilder injectWindowContainerManagedWindowBuilder() {
		SystemAction systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		return injectWindowContainerManagedWindowBuilder(systemAction);
	}
	
	@Override
	public Class<Form> getFormClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Form> clazz = null;
		if(entityClass!=null) {
			String name = null;
			if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(entityClass.getName())) {
				if(SystemActionCreate.class.isAssignableFrom(systemActionClass) || SystemActionUpdate.class.isAssignableFrom(systemActionClass) || SystemActionDelete.class.isAssignableFrom(systemActionClass))
					name = EDIT_FORM;
				if(__inject__(StringHelper.class).isNotBlank(name))
					clazz = (Class<Form>) __inject__(ClassHelper.class).getByName(entityClass.getName()+name);
			}
		}
		return clazz;
	}
	
	@Override
	public Class<Form> getFormClass(Class<?> entityClass, SystemAction systemAction) {
		return getFormClass(entityClass, systemAction == null ? null :systemAction.getClass());
	}
	
	@Override
	public Form injectForm(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<?> clazz = getFormClass(entityClass, systemActionClass);
		return clazz == null ? null : (Form)__inject__(clazz);
	}
	
	@Override
	public Form injectForm(Class<?> entityClass, SystemAction systemAction) {
		return injectForm(entityClass, systemAction == null ? null :systemAction.getClass());
	}
	
	@Override
	public Class<Row> getRowClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Row> clazz = null;
		if(entityClass!=null) {
			String name = null;
			if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(entityClass.getName())) {
				if(SystemActionList.class.isAssignableFrom(systemActionClass))
					name = READ_ROW;
				if(__inject__(StringHelper.class).isNotBlank(name))
					clazz = (Class<Row>) __inject__(ClassHelper.class).getByName(entityClass.getName()+name);
			}
		}
		return clazz;
	}
	
	@Override
	public Class<Row> getRowClass(Class<?> entityClass, SystemAction systemAction) {
		return getRowClass(entityClass, systemAction == null ? null :systemAction.getClass());
	}
	
	@Override
	public Row injectRow(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<?> clazz = getRowClass(entityClass, systemActionClass);
		return clazz == null ? null : (Row)__inject__(clazz);
	}
	
	@Override
	public Row injectRow(Class<?> entityClass, SystemAction systemAction) {
		return injectRow(entityClass, systemAction == null ? null :systemAction.getClass());
	}
	
	/**/
	
	private static final String EDIT = "Edit";
	private static final String READ = "Read";
	private static final String LIST = "List";
	private static final String WINDOW_BUILDER = "WindowBuilder";
	private static final String FORM = "Form";
	private static final String ROW = "Row";
	private static final String EDIT_FORM = EDIT+FORM;
	private static final String READ_ROW = READ+ROW;
	//private static final String FORM_DATA = "FormData";
}
