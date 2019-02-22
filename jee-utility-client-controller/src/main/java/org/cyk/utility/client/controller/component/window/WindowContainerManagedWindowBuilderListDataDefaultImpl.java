package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.RowData;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		//TODO dynamically get fields
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, "code","name","description");
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		//TODO dynamically build grid
	}

}
