package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.FormData;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderEditDataImpl extends AbstractWindowContainerManagedWindowBuilderEditImpl implements WindowContainerManagedWindowBuilderEditData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction systemAction) {
		Class<? extends Form> formClass = getFormClass();
		if(formClass!=null) {
			Form form = __inject__(formClass);
			form.setTitle(systemAction.getIdentifier().toString()+" "+systemAction.getEntities().getElementClass().getSimpleName());
			Data data = (Data) systemAction.getEntities().getAt(0);
			
			if(form instanceof FormData<?>) {
				((FormData<Data>)form).setData(data);	
			}
						
			ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
			viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);			
			__execute__(form,data,viewBuilder);
			
			CommandableBuilder commandableBuilder = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT);
			commandableBuilder.setName(systemAction.getIdentifier().toString());	
				
			setView(viewBuilder);
		}
	}
	
	protected abstract void __execute__(Form form,Data data,ViewBuilder viewBuilder);
	
}
