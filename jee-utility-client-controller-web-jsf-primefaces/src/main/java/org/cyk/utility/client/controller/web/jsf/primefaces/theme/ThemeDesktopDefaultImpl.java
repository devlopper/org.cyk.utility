package org.cyk.utility.client.controller.web.jsf.primefaces.theme;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.theme.AbstractThemeImpl;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.window.Window;

public class ThemeDesktopDefaultImpl extends AbstractThemeImpl implements ThemeDesktopDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getIdentifier__() {
		return "org.cyk.utility.client.controller.web.jsf.primefaces.desktop.default";
	}
	
	@Override
	protected String __getTemplateIdentifier__() {
		return "/template/default.xhtml";
	}
	
	@Override
	public Theme process(Window window) {
		mapViews("center",window.getView());
		return this;
	}
}
