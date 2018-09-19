package org.cyk.utility.server.representation.test;

import java.net.URI;

import org.cyk.utility.test.TestSystemFunctionIntegration;

public interface TestRepresentationFunctionIntegration extends TestSystemFunctionIntegration {

	TestRepresentationFunctionIntegration setUniformResourceIdentifier(URI uri);
	TestRepresentationFunctionIntegration setUniformResourceIdentifier(String uri);
	URI getUniformResourceIdentifier();
	
}
