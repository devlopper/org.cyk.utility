package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

@Deprecated
public class StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl extends AbstractStyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl implements Serializable {
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