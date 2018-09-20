package org.cyk.utility.system.layer;

import java.io.Serializable;

public class SystemSubLayerImplementationImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerImplementation, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getPackageNameRegularExpression(Boolean.TRUE).setExpression("[.]{0,1}impl[.]{0,1}");
		setClassNameRegularExpression("Impl");
	}
	
	@Override
	public SystemSubLayerImplementation setClassNameRegularExpression(String expression) {
		return (SystemSubLayerImplementation) super.setClassNameRegularExpression(expression);
	}
	
}
