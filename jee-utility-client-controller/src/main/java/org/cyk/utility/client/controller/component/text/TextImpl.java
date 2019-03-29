package org.cyk.utility.client.controller.component.text;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class TextImpl extends AbstractVisibleComponentImpl implements Text,Serializable {
	private static final long serialVersionUID = 1L;

	private String characters;
	
	@Override
	public String getCharacters() {
		return characters;
	}

	@Override
	public Text setCharacters(String characters) {
		this.characters = characters;
		return this;
	}

}
