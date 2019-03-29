package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.dialog.DialogBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public interface WindowBuilder extends VisibleComponentBuilder<Window> {

	OutputStringTextBuilderMap getOutputStringTextMap();
	OutputStringTextBuilderMap getOutputStringTextMap(Boolean injectIfNull);
	WindowBuilder setOutputStringTextMap(OutputStringTextBuilderMap outputStringTextMap);
	WindowBuilder setOutputStringTexts(Object...keyValues);
	WindowBuilder setOutputStringTextValue(String key,String value);

	OutputStringTextBuilder getTitle();
	OutputStringTextBuilder getTitle(Boolean injectIfNull);
	WindowBuilder setTitle(OutputStringTextBuilder title);
	WindowBuilder setTitleValue(String titleValue);
	
	OutputStringTextBuilder getApplicationName();
	OutputStringTextBuilder getApplicationName(Boolean injectIfNull);
	WindowBuilder setApplicationName(OutputStringTextBuilder applicationName);
	WindowBuilder setApplicationNameValue(String applicationNameValue);
	
	MenuBuilderMap getMenuMap();
	MenuBuilderMap getMenuMap(Boolean injectIfNull);
	WindowBuilder setMenuMap(MenuBuilderMap menuMap);
	
	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	WindowBuilder setView(ViewBuilder view);

	DialogBuilder getDialog();
	DialogBuilder getDialog(Boolean injectIfNull);
	WindowBuilder setDialog(DialogBuilder dialog);
	
	WindowRenderType getRenderType();
	WindowBuilder setRenderType(WindowRenderType renderType);
	
	WindowContainerManagedWindowBuilder getContainerManaged();
	WindowBuilder setContainerManaged(WindowContainerManagedWindowBuilder containerManaged);
}
