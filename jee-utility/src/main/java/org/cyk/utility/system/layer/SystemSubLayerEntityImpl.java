package org.cyk.utility.system.layer;

import java.io.Serializable;

public class SystemSubLayerEntityImpl extends AbstractSystemSubLayerImpl implements SystemSubLayerEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getPackageNameRegularExpression(Boolean.TRUE).setExpression("[.]{0,1}"+PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES+"[.]{0,1}");
	}
	
	@Override
	public SystemSubLayerEntity setPackageNameRegularExpression(String expression) {
		return (SystemSubLayerEntity) super.setPackageNameRegularExpression(expression);
	}
	
}
