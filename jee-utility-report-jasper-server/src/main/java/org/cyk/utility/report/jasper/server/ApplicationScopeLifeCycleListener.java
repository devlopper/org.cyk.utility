package org.cyk.utility.report.jasper.server;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.variable.VariableHelper;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		VariableHelper.write(VariableName.ENABLED, Boolean.TRUE);
		LogHelper.logConfig("Jasper report server initialized", getClass());
	}

	@Override
	public void __destroy__(Object object) {
		SessionGetter.getInstance().logout();
	}	
}