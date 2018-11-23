package org.cyk.utility.client.controller.entities.myentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderReadDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;

public class MyEntityReadWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderReadDataImpl implements MyEntityReadWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,Data data,ViewBuilder viewBuilder) {
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, MyEntity.PROPERTY_CODE);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, MyEntity.PROPERTY_NAME);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, MyEntity.PROPERTY_DESCRIPTION);
	}

}
