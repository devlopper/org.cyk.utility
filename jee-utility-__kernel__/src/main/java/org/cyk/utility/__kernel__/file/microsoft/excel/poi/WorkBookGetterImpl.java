package org.cyk.utility.__kernel__.file.microsoft.excel.poi;

import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.utility.__kernel__.file.microsoft.excel.AbstractWorkBookGetterImpl;

@Dependent
public class WorkBookGetterImpl extends AbstractWorkBookGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object get(InputStream inputStream) {
		try {
			return WorkbookFactory.create(inputStream);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
