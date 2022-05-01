package org.cyk.utility.file.excel;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface SheetReader {

	String[][] read(Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows);
	String[][] read(Sheet sheet);
	
	/**/
	
	static SheetReader getInstance() {
		return Helper.getInstance(SheetReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
