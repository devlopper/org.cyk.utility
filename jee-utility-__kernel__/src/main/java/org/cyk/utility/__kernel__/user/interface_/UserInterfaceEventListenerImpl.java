package org.cyk.utility.__kernel__.user.interface_;

import java.io.Serializable;

import org.cyk.utility.__kernel__.log.LogHelper;

public class UserInterfaceEventListenerImpl extends AbstractUserInterfaceEventListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listen(UserInterfaceEvent event) {
		LogHelper.logInfo("user interface event : "+event, getClass());
	}

}
