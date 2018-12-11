package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderEditDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.system.action.SystemAction;

public class VerySimpleEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements VerySimpleEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder) {
		if(((VerySimpleEntity)data).getDetails() == null)
			((VerySimpleEntity)data).setDetails(__inject__(VerySimpleEntityDetails.class));
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_CODE);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_NAME);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_DESCRIPTION);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_DETAILS,VerySimpleEntityDetails.PROPERTY_ADDRESS);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATION);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATIONS);
	}

}
