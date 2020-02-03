package org.cyk.utility.__kernel__.string.barcode;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface BarCodeReader {

	String read(byte[] bytes);
	
	/**/
	
	static BarCodeReader getInstance() {
		return Helper.getInstance(BarCodeReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
