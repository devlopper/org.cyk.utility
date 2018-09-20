package org.cyk.utility.system.layer;

import org.cyk.utility.character.CharacterConstant;

public interface SystemSubLayerImplementation extends SystemSubLayer {

	@Override SystemSubLayerImplementation setClassNameRegularExpression(String expression);
	
	/**/
	
	String CLASS_NAME_SUFFIX = "Impl";
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL = CLASS_NAME_SUFFIX.toLowerCase();
	
	String PACKAGE_NAME_SUB_STRING_AS_IDENTIFIER = CharacterConstant.DOT+PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL+CharacterConstant.DOT;
	
}
