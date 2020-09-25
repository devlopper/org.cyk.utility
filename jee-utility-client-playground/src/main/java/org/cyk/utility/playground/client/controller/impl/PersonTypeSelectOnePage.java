package org.cyk.utility.playground.client.controller.impl;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeSelectOnePage extends AbstractPersonTypeSelectPage {

	@Override
	protected Boolean isMany() {
		return Boolean.FALSE;
	}

}
