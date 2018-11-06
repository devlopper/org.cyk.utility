package org.cyk.utility.identifier.resource;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class UniformResourceIdentifierStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void format_arguments_http_localhost_8080_slash(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/", builder.setFormatArguments(UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_SCHEME,"http"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_HOST,"localhost"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_PORT,"8080"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_PATH,"").execute().getOutput());
	}
	
	@Test
	public void format_arguments_http_localhost_8080_slash_folder(){
		UniformResourceIdentifierStringBuilder builder = __inject__(UniformResourceIdentifierStringBuilder.class);
		assertionHelper.assertEquals("http://localhost:8080/folder", builder.setFormatArguments(UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_SCHEME,"http"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_HOST,"localhost"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_PORT,"8080"
				,UniformResourceIdentifierStringBuilder.FORMAT_ARGUMENT_PATH,"folder").execute().getOutput());
	}
}
