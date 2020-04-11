package org.cyk.utility.__kernel__.file.microsoft.excel;

import java.io.FileInputStream;
import java.io.InputStream;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface WorkBookGetter {

	Object get(InputStream inputStream);
	
	default Object get(String fileName) {
		if(StringHelper.isBlank(fileName))
			return null;
		try {
			return get(new FileInputStream(fileName));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/**/
	
	static WorkBookGetter getInstance() {
		return Helper.getInstance(WorkBookGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
