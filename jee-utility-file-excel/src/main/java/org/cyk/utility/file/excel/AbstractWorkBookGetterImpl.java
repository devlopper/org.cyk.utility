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
	public WorkBook get(Arguments arguments) {
		if(arguments == null)
			return null;
		InputStream inputStream = arguments.getInputStream();
		if(inputStream == null && arguments.getBytes() != null && arguments.getBytes().length > 0)
			inputStream = new ByteArrayInputStream(arguments.getBytes());
		if(inputStream == null && StringHelper.isNotBlank(arguments.getFileName()))
			try {
				inputStream = new FileInputStream(arguments.getFileName());
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		return __get__(inputStream);
	}
	
	@Override
	public WorkBook get(InputStream inputStream) {
		return get(new Arguments().setInputStream(inputStream));
	}
	
	protected abstract WorkBook __get__(InputStream inputStream);
	
	@Override
	public WorkBook get(byte[] bytes) {
		return get(new Arguments().setBytes(bytes));
	}
	
	public WorkBook get(String fileName) {
		return get(new Arguments().setFileName(fileName));
	}
}