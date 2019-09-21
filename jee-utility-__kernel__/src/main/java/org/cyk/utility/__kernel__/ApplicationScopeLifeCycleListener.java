package org.cyk.utility.__kernel__;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils.PropertyUtils;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		PropertyUtils.addBeanIntrospector(new FluentPropertyBeanIntrospector());
	}

	@Override
	public void __destroy__(Object object) {
		
	}
	
}
