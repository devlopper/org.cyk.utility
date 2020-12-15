package org.cyk.utility.template.freemarker;

import java.io.StringReader;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public interface FreeMarkerHelper {

	static Configuration getConfiguration() {
		if(!CONFIGURATION.isHasBeenSet()) {
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
	        configuration.setDefaultEncoding("UTF-8");
	        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	        configuration.setLogTemplateExceptions(false);
	        configuration.setWrapUncheckedExceptions(true);
	        configuration.setFallbackOnNullLoopVariable(false);
	        configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerHelper.class, "/org/cyk/utility/__kernel__/string/freemarker/template"));
	        CONFIGURATION.set(configuration);
		}
		return (Configuration) CONFIGURATION.get();
	}
	
	static Template getTemplate(Configuration configuration,String name) {
		try {
			return configuration.getTemplate(name);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static Template getTemplate(String name) {
		return getTemplate(getConfiguration(), name);
	}
	
	static Template createTemplateFromString(String name,String string) {
		if(StringHelper.isBlank(name) || StringHelper.isBlank(string))
			return null;
		try {
			return new Template(name, new StringReader(string),getConfiguration());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	Value CONFIGURATION = new Value();
}