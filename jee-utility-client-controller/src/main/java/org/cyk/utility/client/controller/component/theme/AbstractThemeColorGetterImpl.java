package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;

public abstract class AbstractThemeColorGetterImpl extends AbstractObject implements ThemeColorGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getByWindowContainerManaged(Class<? extends WindowContainerManaged> klass) {
		if(klass == null)
			return null;
		return __getByWindowContainerManaged__(klass);
	}

	protected Object __getByWindowContainerManaged__(Class<? extends WindowContainerManaged> klass) {
		return ConfigurationHelper.getValue(VariableName.USER_INTERFACE_THEME_COLOR);
	}
	
}