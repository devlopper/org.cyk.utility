package org.cyk.utility.client.controller.icon;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;

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
	private static final String UNDESCORE = ConstantCharacter.UNDESCORE.toString();
	private static final String MINUS = ConstantCharacter.MINUS.toString();
}
