package org.cyk.utility.system.layer;

public interface SystemSubLayerEntity extends SystemSubLayer {

	@Override SystemSubLayerEntity setPackageNameRegularExpression(String expression);

	@Override SystemSubLayerEntity setParent(Object parent);
	
	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES = "entities";
}
