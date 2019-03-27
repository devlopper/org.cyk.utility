package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.file.File;

public class OutputFileBuilderImpl extends AbstractOutputBuilderImpl<OutputFile,File> implements OutputFileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected File __getValue__(Object object, Field field, Object value) {
		return (File) value;
	}
	
}
