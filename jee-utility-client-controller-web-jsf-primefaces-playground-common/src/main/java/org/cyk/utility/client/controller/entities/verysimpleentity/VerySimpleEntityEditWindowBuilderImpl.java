package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderEditDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;

public class VerySimpleEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements VerySimpleEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,Data data,ViewBuilder viewBuilder) {
		((VerySimpleEntity)data).setDetails(__inject__(VerySimpleEntityDetails.class));
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_CODE);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_NAME);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_DESCRIPTION);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_DETAILS,VerySimpleEntityDetails.PROPERTY_ADDRESS);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_ENUMERATION);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_ENUMERATIONS);
	}

}
