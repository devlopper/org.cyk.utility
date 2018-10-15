package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.layout.LayoutBuiler;
import org.cyk.utility.client.controller.view.View;

public class FormImpl extends AbstractVisibleComponentImpl implements Form, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private View view;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getView().setLayout(__inject__(LayoutBuiler.class).setMaximumWidth(12).setType(LayoutBuiler.Type.FORM).execute().getOutput());
	}
	
	@Override
	public View getView() {
		return view;
	}

	@Override
	public Form setView(View view) {
		this.view = view;
		return this;
	}

	

}
