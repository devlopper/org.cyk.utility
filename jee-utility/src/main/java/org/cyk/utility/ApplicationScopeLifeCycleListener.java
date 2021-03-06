package org.cyk.utility;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.field.FieldValueCopyFunctionRunnableImpl;
import org.cyk.utility.field.FieldValueCopyImpl;
import org.cyk.utility.instance.InstanceBuilderFunctionRunnableImpl;
import org.cyk.utility.instance.InstanceBuilderImpl;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(InstanceBuilderImpl.class, InstanceBuilderFunctionRunnableImpl.class);
		for(String index : new String[] {"word","phrase"})
			__inject__(StringRepositoryResourceBundle.class).addBundle("org.cyk.utility.string.repository."+index);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
