package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.scope.Scope;

public interface Window extends VisibleComponent {

	OutputStringText getTitle();
	Window setTitle(OutputStringText title);
	
	MenuMap getMenuMap();
	Window setMenuMap(MenuMap menuMap);
	Menu getMenu(Class<? extends Scope> scopeClass);
	
	View getView();
	Window setView(View view);
	
	Theme getTheme();
	Window setTheme(Theme theme);
	
	Dialog getDialog();
	Window setDialog(Dialog dialog);
	
}
