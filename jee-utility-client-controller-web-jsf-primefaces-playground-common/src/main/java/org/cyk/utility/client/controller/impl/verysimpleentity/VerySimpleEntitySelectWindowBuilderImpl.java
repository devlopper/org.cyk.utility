package org.cyk.utility.client.controller.impl.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderSelectDataImpl;
import org.cyk.utility.client.controller.data.RowData;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntitySelectWindowBuilder;

public class VerySimpleEntitySelectWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderSelectDataImpl implements VerySimpleEntitySelectWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, VerySimpleEntity.PROPERTY_CODE,VerySimpleEntity.PROPERTY_NAME);
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		
	}
	
}
