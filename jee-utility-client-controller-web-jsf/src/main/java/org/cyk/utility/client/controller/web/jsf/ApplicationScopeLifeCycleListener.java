package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.annotation.JavaServerFaces;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceEventListener;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.theme.ThemeColorGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		initialize();
	}
	
	public static void initialize() {
		initialize(ApplicationScopeLifeCycleListener.class, new Runnable() {
			@Override
			public void run() {
				/*__inject__(FunctionRunnableMap.class).set(MessagesBuilderImpl.class, MessagesBuilderFunctionRunnableImpl.class,LEVEL);
				__inject__(FunctionRunnableMap.class).set(ChoiceBuilderImpl.class, ChoiceBuilderFunctionRunnableImpl.class,LEVEL);
				__inject__(FunctionRunnableMap.class).set(RequestGetterImpl.class, RequestGetterFunctionRunnableImpl.class,LEVEL);
				__inject__(FunctionRunnableMap.class).set(ContextPropertyValueGetterImpl.class,ContextPropertyValueGetterFunctionRunnableImpl.class,LEVEL);
				*/
				org.cyk.utility.client.controller.web.ApplicationScopeLifeCycleListener.initialize();
				
				__setQualifierClassTo__(JavaServerFaces.class, FileImageBuilder.class,ThemeColorGetter.class,UserInterfaceEventListener.class,ValueConverter.class,MessageRenderer.class);
			}
		});
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	@Deprecated
	public static final Integer LEVEL = Integer.valueOf(org.cyk.utility.client.controller.web.ApplicationScopeLifeCycleListener.LEVEL+1);
}
