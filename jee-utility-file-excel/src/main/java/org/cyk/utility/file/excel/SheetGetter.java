package org.cyk.utility.file.excel;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface SheetGetter {

	Sheet get(WorkBook workBook,String name);
	
	Sheet get(WorkBook workBook,Integer index);
	
	/**/
	
	static SheetGetter getInstance() {
		return Helper.getInstance(SheetGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
