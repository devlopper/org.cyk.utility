package org.cyk.utility.template.freemarker;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.string.AbstractStringTemplateIdentifierGetterImpl;
import org.cyk.utility.template.freemarker.annotation.FreeMarker;

@FreeMarker
public class StringTemplateIdentifierGetterImpl extends AbstractStringTemplateIdentifierGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __get__(NamingModel namingModel) {
		String identifier = super.__get__(namingModel);
		return identifier+".ftl";
	}
	
}
