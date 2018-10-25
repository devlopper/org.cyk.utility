package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.view.View;

public class FormImpl extends AbstractVisibleComponentImpl implements Form, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private View view;
	
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
