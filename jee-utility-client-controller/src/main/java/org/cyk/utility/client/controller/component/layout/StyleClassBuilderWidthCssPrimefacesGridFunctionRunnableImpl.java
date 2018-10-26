package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

public class StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl extends StyleClassBuilderWidthCssFunctionRunnableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __getFormatPrefix__() {
		return "ui";
	}

	@Override
	protected String __getDeviceClassWhenNull__() {
		return "g";
	}
}