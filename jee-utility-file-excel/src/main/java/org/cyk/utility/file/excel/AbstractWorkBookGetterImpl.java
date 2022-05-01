package org.cyk.utility.file.excel;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractWorkBookGetterImpl extends AbstractObject implements WorkBookGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public WorkBook get(InputStream inputStream) {
		if(inputStream == null)
			return null;
		return __get__(inputStream);
	}
	
	protected abstract WorkBook __get__(InputStream inputStream);
	
	@Override
	public WorkBook get(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		return __get__(new ByteArrayInputStream(bytes));
	}
	
	public WorkBook get(String fileName) {
		if(StringHelper.isBlank(fileName))
			return null;
		try {
			return __get__(new FileInputStream(fileName));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}