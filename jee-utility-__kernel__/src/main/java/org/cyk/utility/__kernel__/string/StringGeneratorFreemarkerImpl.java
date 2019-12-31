package org.cyk.utility.__kernel__.string;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class StringGeneratorFreemarkerImpl extends AbstractStringGeneratorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String generate(String templateString, Map<String, Object> arguments) {
		try {
			Configuration configuration = __instantiateConfiguration__();			
			Template template = new Template(TEMPLATE, new StringReader(templateString),configuration);
	        return __process__(template, arguments);
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public String generate(File templateFile, Map<String, Object> arguments) {
		try {
			Configuration configuration = __instantiateConfiguration__();
	        configuration.setDirectoryForTemplateLoading(templateFile.getParentFile());
	        return __process__(configuration.getTemplate(templateFile.getName()), arguments);
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private static Configuration __instantiateConfiguration__() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        return configuration;
	}

	private static String __process__(Template template, Map<String, Object> arguments) {
		try {
			StringWriter stringWriter = new StringWriter();
	        template.process(arguments, stringWriter);
	        String string = stringWriter.toString();
	        stringWriter.close();
	        return string;
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private static final String TEMPLATE = "template";
}
