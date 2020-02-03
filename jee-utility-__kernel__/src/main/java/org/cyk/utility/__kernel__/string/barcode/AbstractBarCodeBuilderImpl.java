package org.cyk.utility.__kernel__.string.barcode;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractBarCodeBuilderImpl extends AbstractObject implements BarCodeBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public byte[] build(String string,Parameters parameters) {
		if(StringHelper.isBlank(string) || parameters == null)
			return null;
		return __build__(string,parameters);
	}
	
	protected abstract byte[] __build__(String string,Parameters parameters);
	
}