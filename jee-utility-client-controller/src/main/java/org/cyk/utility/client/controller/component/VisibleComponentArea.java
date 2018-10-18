package org.cyk.utility.client.controller.component;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface VisibleComponentArea extends Objectable {

	VisibleComponentAreaDimension getWidth();
	VisibleComponentAreaDimension getWidth(Boolean injectIfNull);
	VisibleComponentArea setWidth(VisibleComponentAreaDimension width);
	
	VisibleComponentAreaDimension getHeight();
	VisibleComponentAreaDimension getHeight(Boolean injectIfNull);
	VisibleComponentArea setHeight(VisibleComponentAreaDimension height);
	
}
