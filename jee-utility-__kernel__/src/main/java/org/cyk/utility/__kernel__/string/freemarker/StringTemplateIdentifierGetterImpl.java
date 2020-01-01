package org.cyk.utility.__kernel__.string.freemarker;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.string.AbstractStringTemplateIdentifierGetterImpl;

public class StringTemplateIdentifierGetterImpl extends AbstractStringTemplateIdentifierGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __get__(NamingModel namingModel) {
		String identifier = super.__get__(namingModel);
		return identifier+".ftl";
	}
	
}
