package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.output.OutputStringTextMap;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.scope.Scope;

public interface Window extends VisibleComponent {

	OutputStringText getTitle();
	Window setTitle(OutputStringText title);
	
	OutputStringTextMap getOutputStringTextMap();
	Window setOutputStringTextMap(OutputStringTextMap outputStringTextMap);
	OutputStringText getOutputStringText(String key);
	
	MenuMap getMenuMap();
	Window setMenuMap(MenuMap menuMap);
	Menu getMenu(Class<? extends Scope> scopeClass);
	Menu getMenuOfScopeSession();
	
	View getView();
	Window setView(View view);
	
	Dialog getDialog();
	Window setDialog(Dialog dialog);

	WindowRenderType getRenderType();
	Window setRenderType(WindowRenderType renderType);
	
	Boolean isViewCached();
}
