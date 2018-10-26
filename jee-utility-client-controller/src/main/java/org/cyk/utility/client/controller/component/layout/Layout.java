package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.css.StyleClassBuilderWidth;

public interface Layout extends VisibleComponent {

	Layout addItemFromClass(Object clazz);
	Layout addItemFromClasses(Collection<String> classes);
	Layout addItemFromClasses(String...classes);
	Layout addItemFromWidthClassBuilders(Collection<StyleClassBuilderWidth> styleClassBuilderWidths);
	Layout addItemFromWidthClassBuilders(StyleClassBuilderWidth...styleClassBuilderWidths);
	Layout addItemFromDeviceClassAndWidths(Object...objects);
	
	@Override LayoutItem getChildAt(Integer index);
	
}
