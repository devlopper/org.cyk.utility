package org.cyk.utility.client.controller.impl.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderEditDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetails;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetailsEditWindowBuilder;
import org.cyk.utility.system.action.SystemAction;

public class VerySimpleEntityDetailsEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements VerySimpleEntityDetailsEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder) {
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntityDetails.PROPERTY_CODE);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntityDetails.PROPERTY_ADDRESS);
	}

}
