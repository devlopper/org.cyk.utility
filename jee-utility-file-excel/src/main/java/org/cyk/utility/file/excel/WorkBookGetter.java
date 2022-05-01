package org.cyk.utility.file.excel;

import java.io.InputStream;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface WorkBookGetter {

	WorkBook get(InputStream inputStream);
	WorkBook get(byte[] bytes);
	WorkBook get(String fileName);
	
	/**/
	
	static WorkBookGetter getInstance() {
		return Helper.getInstance(WorkBookGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
