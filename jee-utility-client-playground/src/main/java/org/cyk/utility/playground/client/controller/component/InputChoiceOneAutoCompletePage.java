package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AutoCompleteEntity;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class InputChoiceOneAutoCompletePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private AutoCompleteEntity<Namable> autoComplete;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		autoComplete = new AutoCompleteEntity<Namable>(Namable.class);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice One Auto Complete";
	}

}
