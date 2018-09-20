package org.cyk.utility.system.layer;

public interface SystemSubLayerEntity extends SystemSubLayer {

	@Override SystemSubLayerEntity setPackageNameRegularExpression(String expression);

	String PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES = "entities";
}
