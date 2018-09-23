package org.cyk.utility.system.layer;

public interface SystemSubLayerInterface extends SystemSubLayer {

	@Override SystemSubLayerInterface setInterfaceNameRegularExpression(String expression);
	
	@Override SystemSubLayerInterface setParent(Object parent);
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API = "api";
}
