package org.cyk.utility.__kernel__.file.microsoft.excel.poi;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.file.microsoft.excel.AbstractSheetGetterImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

@Dependent
public class SheetGetterImpl extends AbstractSheetGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object get(Object workbook, String name) {
		if(workbook == null || StringHelper.isBlank(name))
			return null;
		return ((Workbook)workbook).getSheet(name);
	}
	
	@Override
	public Object get(Object workbook, Integer index) {
		if(workbook == null || index == null)
			return null;
		return ((Workbook)workbook).getSheetAt(index);
	}

}
