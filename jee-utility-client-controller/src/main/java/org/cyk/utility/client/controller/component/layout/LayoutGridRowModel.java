package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface LayoutGridRowModel extends Objectable {

	Proportions getWidthProportions();
	Proportions getWidthProportions(Boolean injectIfNull);
	LayoutGridRowModel setWidthProportions(Proportions widthProportions);
	
}
