package org.cyk.utility.__kernel__.file.microsoft.excel;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface SheetGetter {

	Object get(Object workbook,String name);
	
	Object get(Object workbook,Integer index);
	
	/**/
	
	static SheetGetter getInstance() {
		return Helper.getInstance(SheetGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
