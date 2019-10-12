package org.cyk.utility.__kernel__;
import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils.PropertyUtils;
import org.cyk.utility.__kernel__.identifier.resource.ProxyUniformResourceIdentifierGetter;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.locale.LocaleHelper;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector());
		LocaleHelper.LOCALES.add(Locale.FRENCH);
		InternationalizationHelper.addResourceBundlesFromNames(null,null, "word","phrase","throwable","assertion");
		ProxyUniformResourceIdentifierGetter.UNIFORM_RESOURCE_IDENTIFIER.initialize();
	}

	@Override
	public void __destroy__(Object object) {
		
	}
	
}
