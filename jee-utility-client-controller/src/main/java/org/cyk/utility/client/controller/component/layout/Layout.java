package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Layout extends VisibleComponent {

	Layout addItemFromClass(Object clazz);
	Layout addItemFromClasses(Collection<String> classes);
	Layout addItemFromClasses(String...classes);
	/*Layout addItemFromWidthClassBuilders(Collection<StyleClassBuilderWidth> styleClassBuilderWidths);
	Layout addItemFromWidthClassBuilders(StyleClassBuilderWidth...styleClassBuilderWidths);
	
	Layout addItemFromDeviceClassAndWidths(Object...objects);
	*/
	LayoutType getType();
	Layout setType(LayoutType type);
	
	@Override LayoutItem getChildAt(Integer index);
	
}
