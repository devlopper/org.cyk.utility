package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.InvisibleComponent;
import org.cyk.utility.css.StyleClassBuilderWidth;

public interface Layout extends InvisibleComponent {

	Layout addItemFromClass(Object clazz);
	Layout addItemFromClasses(Collection<String> classes);
	Layout addItemFromClasses(String...classes);
	Layout addItemFromWidthClassBuilders(Collection<StyleClassBuilderWidth> styleClassBuilderWidths);
	Layout addItemFromWidthClassBuilders(StyleClassBuilderWidth...styleClassBuilderWidths);
	Layout addItemFromDeviceClassAndWidths(Object...objects);
	//Layout addItemFromColumnWidth(Object clazz);
	
	/* Cardinal point */
	/*
	LayoutCell getNorth();
	Layout setNorth(LayoutCell north);
	
	LayoutCell getCenter();
	Layout setCenter(LayoutCell footer);
	
	LayoutCell getSouth();
	Layout setSouth(LayoutCell south);
	
	LayoutCell getWest();
	Layout setWest(LayoutCell west);
	
	LayoutCell getEast();
	Layout setEast(LayoutCell east);
	*/
	
}
