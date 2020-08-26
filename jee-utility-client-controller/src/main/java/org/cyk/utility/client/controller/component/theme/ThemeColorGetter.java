package org.cyk.utility.client.controller.component.theme;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
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
		return Helper.getInstance(ThemeColorGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
