package org.cyk.utility.__kernel__;
import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils.PropertyUtils;
import org.cyk.utility.__kernel__.annotation.Google;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.locale.LocaleHelper;
import org.cyk.utility.__kernel__.string.barcode.BarCodeBuilder;
import org.cyk.utility.__kernel__.string.barcode.BarCodeReader;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector());
		LocaleHelper.addLocales(Locale.FRENCH);
		InternationalizationHelper.addResourceBundlesFromNames(null,null, "word","phrase","throwable","assertion");
		DependencyInjection.setQualifierClassTo(Google.class, BarCodeBuilder.class, BarCodeReader.class);
	}

	@Override
	public void __destroy__(Object object) {
		
	}	
}