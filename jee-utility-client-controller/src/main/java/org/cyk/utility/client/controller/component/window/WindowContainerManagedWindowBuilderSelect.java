package org.cyk.utility.client.controller.component.window;

import java.util.Collection;

import org.cyk.utility.__kernel__.string.Strings;

public interface WindowContainerManagedWindowBuilderSelect extends WindowContainerManagedWindowBuilder {

	Collection<?> getGridObjects();
	WindowContainerManagedWindowBuilderSelect setGridObjects(Collection<?> gridObjects);
	
	Strings getGridColumnsFieldNames();
	Strings getGridColumnsFieldNames(Boolean injectIfNull);
	WindowContainerManagedWindowBuilderSelect setGridColumnsFieldNames(Strings gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNames(Collection<String> gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNames(String...gridColumnsFieldNames);
	
	WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNamesWithPrefix(String prefix,Collection<String> gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderSelect addGridColumnsFieldNamesWithPrefix(String prefix,String...gridColumnsFieldNames);
	
}
