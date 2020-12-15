package org.cyk.utility.template.freemarker;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringGenerator;
import org.cyk.utility.__kernel__.string.StringTemplateGetter;
import org.cyk.utility.__kernel__.string.StringTemplateIdentifierGetter;
import org.cyk.utility.template.freemarker.annotation.FreeMarker;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		DependencyInjection.setQualifierClassTo(FreeMarker.class,StringGenerator.class,StringTemplateGetter.class,StringTemplateIdentifierGetter.class);
		LogHelper.logInfo("Freemarker template engine has been initialized", getClass());
	}

	@Override
	public void __destroy__(Object object) {
		
	}	
}