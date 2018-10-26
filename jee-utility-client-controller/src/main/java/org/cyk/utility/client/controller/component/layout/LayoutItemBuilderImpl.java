package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInvisibleComponentBuilderImpl;

public class LayoutItemBuilderImpl extends AbstractInvisibleComponentBuilderImpl<LayoutItem> implements LayoutItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	
	@Override
	protected void __execute__(LayoutItem layoutItem) {
		super.__execute__(layoutItem);
		LayoutBuilder layout = getLayout();
		if(layout!=null)
			layoutItem.setLayout(layout.execute().getOutput());
	}
	
	@Override
	public LayoutBuilder getLayout() {
		return layout;
	}

	@Override
	public LayoutItemBuilder setLayout(LayoutBuilder layout) {
		this.layout = layout;
		return this;
	}
	
	
	
}
