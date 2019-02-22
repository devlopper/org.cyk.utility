package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderListDataImpl;
import org.cyk.utility.client.controller.data.RowData;

public class VerySimpleEntityListWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements VerySimpleEntityListWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, VerySimpleEntity.PROPERTY_CODE,VerySimpleEntity.PROPERTY_NAME,VerySimpleEntity.PROPERTY_DESCRIPTION);
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		
	}
	
}
