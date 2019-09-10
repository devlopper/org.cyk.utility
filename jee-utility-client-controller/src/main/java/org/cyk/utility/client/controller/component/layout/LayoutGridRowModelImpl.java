package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class LayoutGridRowModelImpl extends AbstractObject implements LayoutGridRowModel,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Proportions widthProportions;
	
	@Override
	public Proportions getWidthProportions() {
		return widthProportions;
	}
	
	@Override
	public Proportions getWidthProportions(Boolean injectIfNull) {
		if(widthProportions == null && Boolean.TRUE.equals(injectIfNull))
			widthProportions = __inject__(Proportions.class);
		return widthProportions;
	}

	@Override
	public LayoutGridRowModel setWidthProportions(Proportions widthProportions) {
		this.widthProportions = widthProportions;
		return this;
	}

}
