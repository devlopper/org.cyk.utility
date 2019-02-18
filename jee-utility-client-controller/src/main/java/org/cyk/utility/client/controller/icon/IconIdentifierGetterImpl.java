package org.cyk.utility.client.controller.icon;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class IconIdentifierGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements IconIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Icon icon;
		
	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public IconIdentifierGetter setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

}
