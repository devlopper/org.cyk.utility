package org.cyk.utility.file.excel;

import java.io.InputStream;

import org.cyk.utility.array.ArrayInstanceTwoDimensionString;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.number.Interval;

public interface FileExcelSheetDataArrayReader extends FunctionWithPropertiesAsInput<ArrayInstanceTwoDimensionString> {

	String getWorkbookFileName();
	FileExcelSheetDataArrayReader setWorkbookFileName(String workbookFileName);
	
	InputStream getWorkbookInputStream();
	FileExcelSheetDataArrayReader setWorkbookInputStream(InputStream workbookInputStream);
	
	String getSheetName();
	FileExcelSheetDataArrayReader setSheetName(String sheetName);
	
	Interval getRowInterval();
	Interval getRowInterval(Boolean injectIfNull);
	FileExcelSheetDataArrayReader setRowInterval(Interval rowInterval);
	
	Interval getColumnInterval();
	Interval getColumnInterval(Boolean injectIfNull);
	FileExcelSheetDataArrayReader setColumnInterval(Interval columnInterval);
}
