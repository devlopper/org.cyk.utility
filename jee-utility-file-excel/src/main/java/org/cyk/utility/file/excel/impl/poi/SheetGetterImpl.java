package org.cyk.utility.file.excel.impl.poi;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.file.excel.AbstractSheetGetterImpl;
import org.cyk.utility.file.excel.Sheet;
import org.cyk.utility.file.excel.WorkBook;
import org.cyk.utility.file.excel.WorkBookGetter;

@ApplicationScoped
public class SheetGetterImpl extends AbstractSheetGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkBookGetter workBookGetter;
	
	@Override
	public Sheet get(Arguments arguments) {
		if(arguments == null)
			return null;
		WorkBook workBook = arguments.getWorkBook();
		if(workBook == null && arguments.getWorkBookGetterArguments() != null)
			workBook = workBookGetter.get(arguments.getWorkBookGetterArguments());
		if(workBook != null && workBook.getValue() != null) {
			if(StringHelper.isNotBlank(arguments.getName()))
				return new Sheet(workBook,((Workbook)workBook.getValue()).getSheet(arguments.getName()));
			if(arguments.getIndex() != null)
				return new Sheet(workBook,((Workbook)workBook.getValue()).getSheetAt(arguments.getIndex()));
		}
		return null;
	}
	
	@Override
	public Sheet get(WorkBook workBook, String name) {
		return get(new Arguments().setWorkBook(workBook).setName(name));
	}
	
	@Override
	public Sheet get(WorkBook workBook, Integer index) {
		return get(new Arguments().setWorkBook(workBook).setIndex(index));
	}

}
