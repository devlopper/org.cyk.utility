package org.cyk.utility.system.layer;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;

public interface SystemSubLayerImplementation extends SystemSubLayer {

	@Override SystemSubLayerImplementation setClassNameRegularExpression(String expression);
	
	@Override SystemSubLayerImplementation setParent(Object parent);
	
	/**/
	
	String CLASS_NAME_SUFFIX = "Impl";
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL = CLASS_NAME_SUFFIX.toLowerCase();
	
	String PACKAGE_NAME_SUB_STRING_AS_IDENTIFIER = ConstantCharacter.DOT+PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL+ConstantCharacter.DOT;
	
}
