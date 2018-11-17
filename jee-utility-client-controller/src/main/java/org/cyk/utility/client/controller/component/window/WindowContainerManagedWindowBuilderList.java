package org.cyk.utility.client.controller.component.window;

import java.util.Collection;

import org.cyk.utility.string.Strings;

public interface WindowContainerManagedWindowBuilderList extends WindowContainerManagedWindowBuilder {

	Collection<?> getGridObjects();
	WindowContainerManagedWindowBuilderList setGridObjects(Collection<?> gridObjects);
	
	Strings getGridColumnsFieldNames();
	Strings getGridColumnsFieldNames(Boolean injectIfNull);
	WindowContainerManagedWindowBuilderList setGridColumnsFieldNames(Strings gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderList addGridColumnsFieldNames(Collection<String> gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderList addGridColumnsFieldNames(String...gridColumnsFieldNames);
	
	WindowContainerManagedWindowBuilderList addGridColumnsFieldNamesWithPrefix(String prefix,Collection<String> gridColumnsFieldNames);
	WindowContainerManagedWindowBuilderList addGridColumnsFieldNamesWithPrefix(String prefix,String...gridColumnsFieldNames);
	
}
