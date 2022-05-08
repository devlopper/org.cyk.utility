package org.cyk.utility.file.excel;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SheetReader {

	String[][] read(Arguments arguments);
	
	String[][] read(Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows);
	String[][] read(Sheet sheet);

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private SheetGetter.Arguments sheetGetterArguments = new SheetGetter.Arguments();
		private Sheet sheet;
		private Integer fromColumnIndex,numberOfColumns,fromRowIndex,numberOfRows;
	}
	
	/**/
	
	static SheetReader getInstance() {
		return Helper.getInstance(SheetReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
