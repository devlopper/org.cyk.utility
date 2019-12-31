package org.cyk.utility.__kernel__.file.microsoft.excel;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayTwoDimensionString;
import org.cyk.utility.__kernel__.number.Interval;
import org.cyk.utility.__kernel__.value.Value;

public interface SheetReader {

	ArrayTwoDimensionString read(Object workbook,Object sheet,Interval rowInterval,Interval columnInterval);
	
	default ArrayTwoDimensionString read(Object workbook,Object sheet) {
		if(workbook == null || sheet == null)
			return null;
		return read(workbook, sheet,null,null);
	}
	
	/**/
	
	static SheetReader getInstance() {
		return Helper.getInstance(SheetReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
