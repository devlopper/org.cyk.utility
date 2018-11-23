package org.cyk.utility.client.controller.entities.myentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderListDataImpl;
import org.cyk.utility.client.controller.data.RowData;

public class MyEntityListWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements MyEntityListWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, MyEntity.PROPERTY_CODE,MyEntity.PROPERTY_NAME,MyEntity.PROPERTY_DESCRIPTION);
		setGridObjects(MyEntity.COLLECTION);
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		
	}
	
}
