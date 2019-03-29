package org.cyk.utility.client.controller.component.text;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface TextBuilder extends VisibleComponentBuilder<Text> {

	String getCharacters();
	TextBuilder setCharacters(String characters);
	
}
