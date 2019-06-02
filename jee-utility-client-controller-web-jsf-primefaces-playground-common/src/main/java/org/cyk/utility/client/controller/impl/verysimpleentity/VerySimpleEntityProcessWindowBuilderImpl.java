package org.cyk.utility.client.controller.impl.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderProcessDataImpl;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityProcessWindowBuilder;
import org.cyk.utility.system.action.SystemAction;

public class VerySimpleEntityProcessWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderProcessDataImpl implements VerySimpleEntityProcessWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form,SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_CODE);
		viewBuilder.addComponentBuilderByObjectByFieldNames(data, VerySimpleEntity.PROPERTY_NAME);
	}
	
}
