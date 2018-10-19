package org.cyk.utility.client.controller.component.form;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.view.ViewBuilder;

public class FormBuilderImpl extends AbstractVisibleComponentBuilderImpl<Form> implements FormBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ViewBuilder view;

	@Override
	protected void __execute__(Form form) {
		super.__execute__(form);
		ViewBuilder view = getView();
		if(view!=null)
			form.setView(view.execute().getOutput());
	}
	
	@Override
	public ViewBuilder getView() {
		return view;
	}

	@Override
	public ViewBuilder getView(Boolean injectIfNull) {
		return (ViewBuilder) __getInjectIfNull__(FIELD_VIEW, injectIfNull);
	}

	@Override
	public FormBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}

	/**/
	
	public static final String FIELD_VIEW = "view";
	
}
