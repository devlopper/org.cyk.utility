package org.cyk.utility.template.freemarker;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

import org.cyk.utility.__kernel__.string.AbstractStringGeneratorImpl;
import org.cyk.utility.template.freemarker.annotation.FreeMarker;

import freemarker.template.Template;

@FreeMarker
public class StringGeneratorImpl extends AbstractStringGeneratorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String __generate__(Object template, Map<String, Object> arguments) {
		try {
			StringWriter stringWriter = new StringWriter();
	        ((Template)template).process(arguments, stringWriter);
	        String string = stringWriter.toString();
	        stringWriter.close();
	        return string;
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
