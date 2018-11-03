package org.cyk.utility.client.controller.component.view;

import java.io.Serializable;

import org.cyk.utility.map.AbstractMapInstanceImpl;

public class ViewBuilderMapImpl extends AbstractMapInstanceImpl<String, ViewBuilder> implements ViewBuilderMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ViewBuilderMap set(Object... keyValues) {
		return (ViewBuilderMap) super.set(keyValues);
	}
	
}
