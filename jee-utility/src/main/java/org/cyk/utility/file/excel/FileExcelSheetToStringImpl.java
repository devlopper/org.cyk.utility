package org.cyk.utility.file.excel;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class FileExcelSheetToStringImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements FileExcelSheetToString,Serializable {
	private static final long serialVersionUID = 1L;
	
	private FileExcelSheetDataArrayReader dataArrayReader;
	
	@Override
	public FileExcelSheetDataArrayReader getDataArrayReader() {
		return dataArrayReader;
	}

	@Override
	public FileExcelSheetDataArrayReader getDataArrayReader(Boolean injectIfNull) {
		return (FileExcelSheetDataArrayReader) __getInjectIfNull__(FIELD_DATA_ARRAY_READER, injectIfNull);
	}

	@Override
	public FileExcelSheetToString setDataArrayReader(FileExcelSheetDataArrayReader dataArrayReader) {
		this.dataArrayReader = dataArrayReader;
		return this;
	}

	/**/
	
	private static final String FIELD_DATA_ARRAY_READER = "dataArrayReader";
}
