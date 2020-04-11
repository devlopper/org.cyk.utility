package org.cyk.utility.client.controller.component.theme;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.session.Session;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public interface ThemeColorGetter {

	default Object getByWindowContainerManaged(Class<? extends WindowContainerManaged> klass) {
		Session session = SessionHelper.getAttributeSession();
		if(session == null)
			return ConfigurationHelper.getValue(VariableName.USER_INTERFACE_THEME_COLOR);
		return session.getUserInterfaceThemeColor();
	}
	
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
	
	Value INSTANCE = new Value();
	
}
