package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.FormData;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

public abstract class AbstractWindowContainerManagedWindowBuilderEditDataImpl extends AbstractWindowContainerManagedWindowBuilderEditImpl implements WindowContainerManagedWindowBuilderEditData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		if(formClass!=null) {
			Form form = __inject__(formClass);
			if(window.getTitle()!=null)
				form.setTitle(window.getTitle().getValue());
			Data data = (Data) systemAction.getEntities().getAt(0);
			
			if(form instanceof FormData<?>) {
				((FormData<Data>)form).setData(data);	
			}
						
			ViewBuilder viewBuilder = getView();
			if(viewBuilder == null)
				viewBuilder = __inject__(ViewBuilder.class);
			viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);
			
			__execute__(form,systemAction,data,viewBuilder);
			
			if(!(systemAction instanceof SystemActionRead)) {
				viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT,systemAction);
			}
			
			setView(viewBuilder);
		}
	}
	
	protected abstract void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder);
	
}
