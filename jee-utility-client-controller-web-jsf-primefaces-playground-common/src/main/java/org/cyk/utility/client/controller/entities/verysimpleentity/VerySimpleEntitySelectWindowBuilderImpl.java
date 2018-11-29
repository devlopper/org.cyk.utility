package org.cyk.utility.client.controller.entities.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderSelectDataImpl;

public class VerySimpleEntitySelectWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderSelectDataImpl implements VerySimpleEntitySelectWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		/*Collection<?> objects = getGridObjects();
		if(objects == null)
			objects = __inject__(Controller.class).readMany(getSystemAction().getEntities().getElementClass());
		
		addGridColumnsFieldNamesWithPrefix(RowData.PROPERTY_DATA, VerySimpleEntity.PROPERTY_CODE,VerySimpleEntity.PROPERTY_NAME);
		setGridObjects(objects);
		*/
	}
	
	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		
	}
	
}
