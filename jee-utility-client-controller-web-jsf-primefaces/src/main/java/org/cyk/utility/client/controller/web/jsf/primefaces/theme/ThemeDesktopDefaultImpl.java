package org.cyk.utility.client.controller.web.jsf.primefaces.theme;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.Window;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractThemeImpl;
import org.cyk.utility.scope.ScopeSession;

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
		__north__(window);
		__center__(window);
		__south__(window);
		return this;
	}
	
	private void __north__(Window window) {
		//Build view from session menu
		Menu menu = window.getMenu(ScopeSession.class);
		if(menu!=null) {
			menu.setRenderType(__inject__(MenuRenderTypeRowBar.class));
			ViewBuilder viewBuilder = __inject__(ViewBuilder.class).setComponentsBuilder(__inject__(ComponentsBuilder.class)
					.setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
					.addComponents(menu));
			mapViews("north",viewBuilder.execute().getOutput());	
		}
	}
	
	private void __center__(Window window) {
		mapViews("center",window.getView());
	}

	private void __south__(Window window) {
		
	}

	@Override
	protected void __buildMenu__(Object menuMapKey) {
		// TODO Auto-generated method stub		
	}
}
