package org.cyk.utility.system.layer;

public interface SystemSubLayerInterface extends SystemSubLayer {

	@Override SystemSubLayerInterface setInterfaceNameRegularExpression(String expression);
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API = "api";
}
