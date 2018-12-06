package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.dialog.DialogBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public interface WindowBuilder extends VisibleComponentBuilder<Window> {

	OutputStringTextBuilder getTitle();
	OutputStringTextBuilder getTitle(Boolean injectIfNull);
	WindowBuilder setTitle(OutputStringTextBuilder title);
	WindowBuilder setTitleValue(String titleValue);

	MenuBuilderMap getMenuMap();
	MenuBuilderMap getMenuMap(Boolean injectIfNull);
	WindowBuilder setMenuMap(MenuBuilderMap menuMap);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	WindowBuilder setView(ViewBuilder view);

	Theme getTheme();
	WindowBuilder setTheme(Theme theme);
	
	DialogBuilder getDialog();
	DialogBuilder getDialog(Boolean injectIfNull);
	WindowBuilder setDialog(DialogBuilder dialog);
}
