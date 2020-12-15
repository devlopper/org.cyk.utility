package org.cyk.utility.report.jasper.server;

public interface VariableName {

	static String build(String string) {
		return org.cyk.utility.__kernel__.variable.VariableName.build("jasper.server."+string);
	}
	
	String ENABLED = build("enabled");
	String URL = build("url");
	String CREDENTIAL_USERNAME = build("credential.username");
	String CREDENTIAL_PASSWORD = build("credential.password");
	
}