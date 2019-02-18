package org.cyk.utility.client.controller.icon;

import java.io.Serializable;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.character.CharacterConstant;

public class IconIdentifierGetterFontAwsome extends AbstractIconIdentifierGetterFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __process__(Icon icon) {
		return format(StringUtils.replace(icon.name().toLowerCase(),UNDESCORE, MINUS) );
	}
	
	private String format(String identifier) {
		return String.format(FORMAT, identifier);
	}
	
	private static final String FORMAT = "fa fa-%s";
	private static final String UNDESCORE = CharacterConstant.UNDESCORE.toString();
	private static final String MINUS = CharacterConstant.MINUS.toString();
}
