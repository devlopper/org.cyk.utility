package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.log.LogHelper;

public class ViewEventListenerImpl extends AbstractViewEventListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listen(ViewEvent event) {
		LogHelper.logInfo("view event : "+event, getClass());
	}

}
