package org.cyk.utility.file.excel.impl.poi;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.file.excel.AbstractSheetGetterImpl;
import org.cyk.utility.file.excel.Sheet;
import org.cyk.utility.file.excel.WorkBook;

@Dependent
public class SheetGetterImpl extends AbstractSheetGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Sheet get(WorkBook workBook, String name) {
		if(workBook == null || workBook.getValue() == null || StringHelper.isBlank(name))
			return null;
		return new Sheet(workBook,((Workbook)workBook.getValue()).getSheet(name));
	}
	
	@Override
	public Sheet get(WorkBook workBook, Integer index) {
		if(workBook == null || workBook.getValue() == null || index == null)
			return null;
		return new Sheet(workBook,((Workbook)workBook.getValue()).getSheetAt(index));
	}

}
