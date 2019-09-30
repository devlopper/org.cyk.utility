package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractWindowContainerManagedWindowBuilderViewDataImpl extends AbstractWindowContainerManagedWindowBuilderViewImpl implements WindowContainerManagedWindowBuilderViewData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		Data data = (Data) systemAction.getEntities().getAt(0);			
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		__execute__(data,viewBuilder);		
		setView(viewBuilder);
		
		/*
		if(formClass!=null) {
			Form form = __inject__(formClass);
			if(window.getTitle()!=null)
				form.setTitle(window.getTitle().getValue());
			Data data = (Data) systemAction.getEntities().getAt(0);
			
			if(form instanceof FormData<?>) {
				((FormData<Data>)form).setData(data);	
			}
						
			ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
			viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);			
			__execute__(data,viewBuilder);
			
			//viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT,systemAction);
				
			setView(viewBuilder);
		}
		*/
	}
	
	protected abstract void __execute__(Data data,ViewBuilder viewBuilder);
	
}
