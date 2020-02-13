package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.web.ComponentHelper;

public interface DataTableEntityBuilder {

	static <ENTITY> DataTableEntity<ENTITY> build(Class<ENTITY> entityClass) {
		if(entityClass == null)
			return null;
		DataTableEntity<ENTITY> dataTableEntity = new DataTableEntity<ENTITY>();
		dataTableEntity.setEntityClass(entityClass);
		
		CommandableBuilder commandableBuilder = DependencyInjection.inject(CommandableBuilder.class);
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
