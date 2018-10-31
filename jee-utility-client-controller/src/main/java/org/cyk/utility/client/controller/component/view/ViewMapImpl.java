package org.cyk.utility.client.controller.component.view;

import java.io.Serializable;

import org.cyk.utility.map.AbstractMapInstanceImpl;

public class ViewMapImpl extends AbstractMapInstanceImpl<String, View> implements ViewMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ViewMap set(Object... keyValues) {
		return (ViewMap) super.set(keyValues);
	}
	
}
