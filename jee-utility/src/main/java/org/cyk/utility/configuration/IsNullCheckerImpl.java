package org.cyk.utility.configuration;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.value.AbstractIsNullCheckerImpl;

@Dependent @Configuration @Deprecated
public class IsNullCheckerImpl extends AbstractIsNullCheckerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __execute__(Object value) throws Exception {
		Boolean isNull = super.__execute__(value);
		if(isNull != null && !isNull) {
			if(value instanceof String) {
				//avoid maven not filtered value
				isNull = ((String)value).trim().startsWith("${");
			}
		}
		return isNull;
	}
	
}
