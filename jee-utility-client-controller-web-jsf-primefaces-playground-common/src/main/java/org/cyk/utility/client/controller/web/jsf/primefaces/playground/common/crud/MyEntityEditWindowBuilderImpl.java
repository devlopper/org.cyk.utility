package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderImpl;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.system.action.SystemAction;

public class MyEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements MyEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction systemAction) {
		MyEntityEditForm form = __inject__(MyEntityEditForm.class);
		form.setData((MyEntity) systemAction.getEntities().getAt(0));
		form.setTitle(systemAction.getIdentifier().toString()+" "+systemAction.getEntities().getElementClass().getSimpleName());
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);
	
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_CODE);
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_NAME);
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_DESCRIPTION);
		
		CommandableBuilder commandableBuilder = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT);
		commandableBuilder.setName(systemAction.getIdentifier().toString());	
			
		setView(viewBuilder);
	}
	
}
