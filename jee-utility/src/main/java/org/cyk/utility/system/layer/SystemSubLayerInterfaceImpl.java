package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class SystemSubLayerInterfaceImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerInterface, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setPackageNameRegularExpression(String.format(PACKAGE_NAME_REGULAR_EXPRESSION_FORMAT, PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API));
		getPackageNameRegularExpression(Boolean.TRUE).getMiddleTokens(Boolean.TRUE).add(PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API);
	}
	
	@Override
	public SystemSubLayerInterface setInterfaceNameRegularExpression(String expression) {
		return (SystemSubLayerInterface) super.setInterfaceNameRegularExpression(expression);
	}
	
	@Override
	public SystemSubLayerInterface setParent(Object parent) {
		return (SystemSubLayerInterface) super.setParent(parent);
	}
}
