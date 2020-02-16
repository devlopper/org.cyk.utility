package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTableEntity.ActionMode;

@Deprecated
public interface DataTableEntityBuilder {

	static <ENTITY> DataTableEntity<ENTITY> build(Class<ENTITY> entityClass,DataTableEntity.ActionMode addMode) {
		if(entityClass == null)
			return null;
		DataTableEntity<ENTITY> dataTableEntity = new DataTableEntity<ENTITY>();
		dataTableEntity.setEntityClass(entityClass);
		dataTableEntity.setAddMode(addMode);
		
		CommandableBuilder commandableBuilder = DependencyInjection.inject(CommandableBuilder.class);
		commandableBuilder.setName("Ajouter").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					if(ActionMode.INLINE.equals(dataTableEntity.getAddMode())) {
						dataTableEntity.add();
					}else {
						dataTableEntity.openDialogForAdd();
					}
				}
			}
		);
		if(ActionMode.INLINE.equals(dataTableEntity.getAddMode())) {
			commandableBuilder.addUpdatables(DependencyInjection.inject(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers(),":form:"+dataTableEntity.getIdentifier());
			commandableBuilder.getCommand().getFunction().setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		}else {
			commandableBuilder.addUpdatables(DependencyInjection.inject(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
		}
		dataTableEntity.setAddCommandable(commandableBuilder.execute().getOutput());
		
		commandableBuilder = DependencyInjection.inject(CommandableBuilder.class);
		commandableBuilder.setName("Enregistrer").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					if(CollectionHelper.isNotEmpty((Collection<?>) dataTableEntity.getValue())) {
						DependencyInjection.inject(Controller.class).createMany((Collection<Object>)dataTableEntity.getValue());
					}
				}
			}
		);
		commandableBuilder.addUpdatables(DependencyInjection.inject(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
		//dataTableEntity.getDialog().setCommandable(commandableBuilder.execute().getOutput());
		return dataTableEntity;
	}
	
}
