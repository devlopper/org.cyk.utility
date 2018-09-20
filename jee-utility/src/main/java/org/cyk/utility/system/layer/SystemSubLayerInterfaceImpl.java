package org.cyk.utility.system.layer;

import java.io.Serializable;

public class SystemSubLayerInterfaceImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerInterface, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setPackageNameRegularExpression("[.]{0,1}api[.]{0,1}");
	}
	
	@Override
	public SystemSubLayerInterface setInterfaceNameRegularExpression(String expression) {
		return (SystemSubLayerInterface) super.setInterfaceNameRegularExpression(expression);
	}
}
