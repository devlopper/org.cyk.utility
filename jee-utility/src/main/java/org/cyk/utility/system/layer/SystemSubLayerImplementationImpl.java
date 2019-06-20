package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class SystemSubLayerImplementationImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerImplementation, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getPackageNameRegularExpression(Boolean.TRUE).setExpression(String.format(PACKAGE_NAME_REGULAR_EXPRESSION_FORMAT, PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL));
		getPackageNameRegularExpression(Boolean.TRUE).getMiddleTokens(Boolean.TRUE).add(PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL);
		setClassNameRegularExpression(CLASS_NAME_SUFFIX);
	}
	
	@Override
	public SystemSubLayerImplementation setClassNameRegularExpression(String expression) {
		return (SystemSubLayerImplementation) super.setClassNameRegularExpression(expression);
	}
	
	@Override
	public SystemSubLayerImplementation setParent(Object parent) {
		return (SystemSubLayerImplementation) super.setParent(parent);
	}
	
}
