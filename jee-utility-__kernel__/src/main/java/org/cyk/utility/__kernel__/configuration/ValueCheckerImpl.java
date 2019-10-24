package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.__kernel__.value.AbstractCheckerImpl;

@ApplicationScoped @Configuration
public class ValueCheckerImpl extends AbstractCheckerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isNull(Object value) {
		if(super.isNull(value))
			return Boolean.TRUE;
		if(value instanceof String && ((String)value).trim().startsWith("${"))
			return Boolean.TRUE;			
		return Boolean.FALSE;
	}	
	
}