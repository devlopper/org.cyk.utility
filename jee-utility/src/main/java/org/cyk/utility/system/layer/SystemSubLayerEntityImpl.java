package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class SystemSubLayerEntityImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getPackageNameRegularExpression(Boolean.TRUE).setExpression(String.format(PACKAGE_NAME_REGULAR_EXPRESSION_FORMAT, PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES));
		getPackageNameRegularExpression(Boolean.TRUE).getMiddleTokens(Boolean.TRUE).add(PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES);
	}
	
	@Override
	public SystemSubLayerEntity setPackageNameRegularExpression(String expression) {
		return (SystemSubLayerEntity) super.setPackageNameRegularExpression(expression);
	}
	
	@Override
	public SystemSubLayerEntity setParent(Object parent) {
		return (SystemSubLayerEntity) super.setParent(parent);
	}
}
