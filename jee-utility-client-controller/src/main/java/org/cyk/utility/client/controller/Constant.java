package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.configuration.ConfigurationParameterValueGetter;
import org.cyk.utility.value.IsNullChecker;

public interface Constant {
	
	String CYK_CONTEXT_PARAMETER_NAME_FORMAT = "cyk.context.parameter.%s";
	
	static String formatCykContextParameterName(String string) {
		return String.format(CYK_CONTEXT_PARAMETER_NAME_FORMAT, string);
	}
	
	String FILE_URL_PATTERN_PREFIX = "/private/__file__/";
	String FILE_URL_PATTERN = FILE_URL_PATTERN_PREFIX + "*";
	
	String CONTEXT_PARAMETER_NAME_THEME_CLASS_NAME = formatCykContextParameterName("theme.class.name");
	String CONTEXT_PARAMETER_NAME_THEME_IDENTIFIER = formatCykContextParameterName("theme.identifier");
	String CONTEXT_PARAMETER_NAME_THEME_PRIMEFACES = formatCykContextParameterName("theme.primefaces");
	
	/* Favicon*/
	
	String CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_RESOURCES_FOLDER = formatCykContextParameterName("theme.favicon.file.resources.folder");
	String CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_FOLDER = formatCykContextParameterName("theme.favicon.file.folder");
	String CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_NAME_PREFIX = formatCykContextParameterName("theme.favicon.file.name.prefix");
	String CONTEXT_PARAMETER_NAME_THEME_FAVICON_FILE_NAME_EXTENSION = formatCykContextParameterName("theme.favicon.file.name.extension");
	
	/* Logo*/
	
	String CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_RESOURCES_FOLDER = formatCykContextParameterName("theme.logo.file.resources.folder");
	String CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_FOLDER = formatCykContextParameterName("theme.logo.file.folder");
	String CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME = formatCykContextParameterName("theme.logo.file.name");
	String CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME_PREFIX = formatCykContextParameterName("theme.logo.file.name.prefix");
	String CONTEXT_PARAMETER_NAME_THEME_LOGO_FILE_NAME_EXTENSION = formatCykContextParameterName("theme.logo.file.name.extension");
	
	/**/
	
	static String getConfigurationParameterValue(String name,Object context,Object request,String nullValue) {
		return (String) DependencyInjection.inject(ConfigurationParameterValueGetter.class).setName(name).setContext(context).setRequest(request)
				.setIsNullValueCheckerClass(IsNullChecker.class).setIsNullValueCheckerQualifierClass(Configuration.class).setNullValue(nullValue).execute().getOutput();
	}
	
}
