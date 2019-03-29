package org.cyk.utility.client.controller.component.text;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class TextBuilderImpl extends AbstractVisibleComponentBuilderImpl<Text> implements TextBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String characters;
	
	@Override
	protected void __execute__(Text text) {
		super.__execute__(text);
		String characters = getCharacters();
		text.setCharacters(characters);
	}
	
	@Override
	public String getCharacters() {
		return characters;
	}

	@Override
	public TextBuilder setCharacters(String characters) {
		this.characters = characters;
		return this;
	}

}
