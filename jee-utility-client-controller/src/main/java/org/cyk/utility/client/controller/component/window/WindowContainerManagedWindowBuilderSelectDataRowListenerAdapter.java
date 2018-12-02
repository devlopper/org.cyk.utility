package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.RowListenerAdapter;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderSelectDataRowListenerAdapter extends RowListenerAdapter implements WindowContainerManagedWindowBuilderSelectDataRowListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listenSystemAction(SystemAction systemAction) {
		if(getWindowContainerManagedWindowBuilder().getSystemAction().getNextAction()!=null)
			systemAction.setIdentifier(getWindowContainerManagedWindowBuilder().getSystemAction().getNextAction().getIdentifier());
	}
	
}
