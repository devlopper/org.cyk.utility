package org.cyk.utility.template.freemarker;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.AbstractStringTemplateGetterImpl;
import org.cyk.utility.template.freemarker.annotation.FreeMarker;

@FreeMarker
public class StringTemplateGetterImpl extends AbstractStringTemplateGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object __get__(String identifier) {
		return FreeMarkerHelper.getTemplate(identifier);
	}
	
}
