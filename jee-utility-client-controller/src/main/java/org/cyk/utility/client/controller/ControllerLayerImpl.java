package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionSelect;
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
				else if(SystemActionRead.class.isAssignableFrom(systemActionClass))
					name = READ;
				else if(SystemActionList.class.isAssignableFrom(systemActionClass))
					name = LIST;
				else if(SystemActionSelect.class.isAssignableFrom(systemActionClass))
					name = SELECT;
				else if(SystemActionProcess.class.isAssignableFrom(systemActionClass))
					name = PROCESS;
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
		WindowContainerManagedWindowBuilder builder = systemAction == null || systemAction.getEntities() == null ? null 
				: injectWindowContainerManagedWindowBuilder(systemAction.getEntities().getElementClass(), systemAction.getClass());
		if(builder!=null)
			builder.setSystemAction(systemAction);
		return builder;
	}
	
	@Override
	public Class<Form> getFormClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Form> clazz = null;
		if(entityClass!=null) {
			String name = null;
			if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(entityClass.getName())) {
				if(SystemActionCreate.class.isAssignableFrom(systemActionClass) || SystemActionUpdate.class.isAssignableFrom(systemActionClass) || SystemActionDelete.class.isAssignableFrom(systemActionClass))
					name = EDIT_FORM;
				else if(SystemActionRead.class.isAssignableFrom(systemActionClass))
					name = READ_FORM;
				else if(SystemActionProcess.class.isAssignableFrom(systemActionClass))
					name = PROCESS_FORM;
				if(__inject__(StringHelper.class).isNotBlank(name))
					clazz = (Class<Form>) __inject__(ClassHelper.class).getByName(entityClass.getName()+name);
			}
		}
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
	public Class<Row> getRowClass(Class<?> entityClass, Class<? extends SystemAction> systemActionClass) {
		Class<Row> clazz = null;
		if(entityClass!=null) {
			String name = null;
			if(__inject__(SystemLayerController.class).getEntityLayer().isPackage(entityClass.getName())) {
				if(SystemActionList.class.isAssignableFrom(systemActionClass))
					name = READ_ROW;
				else if(SystemActionSelect.class.isAssignableFrom(systemActionClass))
					name = SELECT_ROW;
				if(__inject__(StringHelper.class).isNotBlank(name))
					clazz = (Class<Row>) __inject__(ClassHelper.class).getByName(entityClass.getName()+name);
			}
		}
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
	public Class<?> getDataTransferClassFromEntityClass(Class<?> entityClass) {
		return __inject__(SystemLayerController.class).getDataTransferClassFromEntityClass(entityClass);
	}
	
	@Override
	public Class<?> getDataTransferClassFromEntity(Object entity) {
		return __inject__(SystemLayerController.class).getDataTransferClassFromEntity(entity);
	}
	
	@Override
	public Class<?> getDataRepresentationClassFromEntityClass(Class<?> entityClass) {
		return __inject__(SystemLayerController.class).getRepresentationClassFromEntityClass(entityClass);
	}
	
	@Override
	public Class<?> getDataRepresentationClassFromEntity(Object entity) {
		return __inject__(SystemLayerController.class).getRepresentationClassFromEntity(entity);
	}
	
	
	/**/
	
	private static final String EDIT = "Edit";
	private static final String READ = "Read";
	private static final String LIST = "List";
	private static final String SELECT = "Select";
	private static final String PROCESS = "Process";
	private static final String WINDOW_BUILDER = "WindowBuilder";
	private static final String FORM = "Form";
	private static final String ROW = "Row";
	private static final String EDIT_FORM = EDIT+FORM;
	private static final String READ_FORM = READ+FORM;
	private static final String PROCESS_FORM = PROCESS+FORM;
	private static final String READ_ROW = READ+ROW;
	private static final String SELECT_ROW = SELECT+ROW;
	//private static final String FORM_DATA = "FormData";
}
