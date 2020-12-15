package org.cyk.utility.template.freemarker;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.StringGenerator;
import org.cyk.utility.__kernel__.string.StringTemplateGetter;
import org.cyk.utility.__kernel__.string.StringTemplateIdentifierGetter;
import org.cyk.utility.template.freemarker.annotation.FreeMarker;

@Deprecated
public interface Application {

	static void initialize() {
		DependencyInjection.setQualifierClassTo(FreeMarker.class,StringGenerator.class,StringTemplateGetter.class,StringTemplateIdentifierGetter.class);
	}
	
}