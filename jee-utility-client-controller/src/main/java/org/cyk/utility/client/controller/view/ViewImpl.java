package org.cyk.utility.client.controller.view;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public class ViewImpl extends AbstractVisibleComponentImpl implements View,Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	private OutputStringText name;

	@Override
	public Layout getLayout() {
		return layout;
	}

	@Override
	public View setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	
	@Override
	public OutputStringText getName() {
		return name;
	}

	@Override
	public OutputStringText getName(Boolean injectIfNull) {
		OutputStringText name = getName();
		if(name == null && Boolean.TRUE.equals(injectIfNull))
			setName(name = __inject__(OutputStringText.class));
		return name;
	}

	@Override
	public View setName(OutputStringText name) {
		this.name = name;
		return this;
	}
	
}
