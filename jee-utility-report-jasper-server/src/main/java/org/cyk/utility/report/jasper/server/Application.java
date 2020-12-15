package org.cyk.utility.report.jasper.server;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.variable.VariableHelper;

@Deprecated
public interface Application {

	static void initialize() {
		VariableHelper.write(VariableName.ENABLED, Boolean.TRUE);
		LogHelper.logInfo("Jasper report server initialized", Application.class);
	}
	
}