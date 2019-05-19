package org.cyk.utility;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.field.FieldValueCopyFunctionRunnableImpl;
import org.cyk.utility.field.FieldValueCopyImpl;
import org.cyk.utility.stream.distributed.Topic;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class,LEVEL);
		for(String index : new String[] {"word","phrase","throwable"})
			__inject__(StringRepositoryResourceBundle.class).addBundle("org.cyk.utility.string.repository."+index);
		
		Topic.startAllConsumers();
	}

	@Override
	public void __destroy__(Object object) {
		Topic.stopAllConsumers();
	}
	
	/**/
	
	
	
	/**/
	
	public static final Integer LEVEL = new Integer("0");
	
}
