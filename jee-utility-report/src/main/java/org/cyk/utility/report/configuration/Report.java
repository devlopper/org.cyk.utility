package org.cyk.utility.report.configuration;

import java.util.List;

import org.cyk.utility.__kernel__.configuration.Configuration;

import io.smallrye.config.WithConverter;
import io.smallrye.config.WithDefault;

public interface Report {
	
	String identifier();
	
	@WithDefault("pdf")
	String fileType();
	
	@WithDefault("false")
	Boolean isContentInline();
	
	@WithConverter(Configuration.StringConverter.class)
	String parametersAsJson();
	
	List<Parameter> parameters();
	
	public interface Parameter {
		String name();
		String mappedTo();
	}
	
	/**/
	
	public static Report.Parameter getParameterByName(Report report,String name) {
		if(report == null)
			return null;
		for(Parameter parameter : report.parameters())
			if(parameter.name().equals(name))
				return parameter;
		return null;
	}
}