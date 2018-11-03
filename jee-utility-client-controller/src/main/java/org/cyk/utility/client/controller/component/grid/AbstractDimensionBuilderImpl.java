package org.cyk.utility.client.controller.component.grid;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public abstract class AbstractDimensionBuilderImpl<DIMENSION extends Dimension> extends AbstractVisibleComponentBuilderImpl<DIMENSION> implements DimensionBuilder<DIMENSION>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(DIMENSION dimension) {
		super.__execute__(dimension);
		dimension.setOrderNumber(getOrderNumber());
	}
	
}
