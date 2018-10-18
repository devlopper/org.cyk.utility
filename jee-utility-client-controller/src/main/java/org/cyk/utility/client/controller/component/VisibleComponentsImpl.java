package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class VisibleComponentsImpl extends AbstractCollectionInstanceImpl<VisibleComponent> implements VisibleComponents,Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	public Layout getLayout() {
		return layout;
	}

	@Override
	public VisibleComponents setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	
}
