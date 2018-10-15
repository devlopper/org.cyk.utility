package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.layout;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuiler;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class LayoutFormPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		layout = __inject__(LayoutBuiler.class).setMaximumWidth(12).setType(LayoutBuiler.Type.FORM).execute().getOutput();
	}
}
