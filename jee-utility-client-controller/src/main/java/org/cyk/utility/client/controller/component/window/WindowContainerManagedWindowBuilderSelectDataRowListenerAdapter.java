package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.RowListenerAdapter;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;

public class WindowContainerManagedWindowBuilderSelectDataRowListenerAdapter extends RowListenerAdapter implements WindowContainerManagedWindowBuilderSelectDataRowListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listenNavigationBuilder(NavigationBuilder navigationBuilder) {
		navigationBuilder.setParameters("processId","NUM");
	}
	
}
