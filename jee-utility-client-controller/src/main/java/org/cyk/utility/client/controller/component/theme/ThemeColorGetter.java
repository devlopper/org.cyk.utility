package org.cyk.utility.client.controller.component.theme;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public interface ThemeColorGetter {

	Object getByWindowContainerManaged(Class<? extends WindowContainerManaged> klass);
	
	default Object getByWindowContainerManaged(WindowContainerManaged windowContainerManaged) {
		if(windowContainerManaged == null)
			return null;
		return getByWindowContainerManaged(windowContainerManaged.getClass());
	}
	
	/**/
	
	static ThemeColorGetter getInstance() {
		ThemeColorGetter instance = (ThemeColorGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(ThemeColorGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", ThemeColorGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
