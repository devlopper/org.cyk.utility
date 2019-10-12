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
		if(dataArrayReader == null && Boolean.TRUE.equals(injectIfNull))
			dataArrayReader = __inject__(FileExcelSheetDataArrayReader.class);
		return dataArrayReader;
	}

	@Override
	public FileExcelSheetToString setDataArrayReader(FileExcelSheetDataArrayReader dataArrayReader) {
		this.dataArrayReader = dataArrayReader;
		return this;
	}

	/**/
	
}
