package org.cyk.utility.internationalization;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.string.Case;

@Dependent
public class InternalizationImpl extends AbstractObject implements Internalization,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(String key, Object[] parameters, Locale locale, Case aCase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(String key, Object[] parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(String key, Locale locale, Case aCase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
