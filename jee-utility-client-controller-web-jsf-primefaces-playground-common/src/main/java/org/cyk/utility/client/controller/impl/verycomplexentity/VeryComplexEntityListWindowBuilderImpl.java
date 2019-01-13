package org.cyk.utility.client.controller.impl.verycomplexentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderListDataImpl;
import org.cyk.utility.client.controller.data.RowData;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntityListWindowBuilder;

public class VeryComplexEntityListWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements VeryComplexEntityListWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, VeryComplexEntity.PROPERTY_CODE,VeryComplexEntity.PROPERTY_FIRST_NAME
				,VeryComplexEntity.PROPERTY_LAST_NAMES,VeryComplexEntity.PROPERTY_DESCRIPTION);
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		
	}
	
}
