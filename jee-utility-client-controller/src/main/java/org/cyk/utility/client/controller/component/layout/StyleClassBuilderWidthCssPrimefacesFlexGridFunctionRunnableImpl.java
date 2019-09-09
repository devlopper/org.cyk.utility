package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

@Deprecated
public class StyleClassBuilderWidthCssPrimefacesFlexGridFunctionRunnableImpl extends StyleClassBuilderWidthCssFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __getFormatPrefix__() {
		return "p";
	}

	@Override
	protected String __getDeviceClassWhenNull__() {
		return "col";
	}
}