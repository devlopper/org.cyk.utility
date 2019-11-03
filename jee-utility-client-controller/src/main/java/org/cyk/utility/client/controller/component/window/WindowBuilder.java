package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.dialog.DialogBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public interface WindowBuilder extends VisibleComponentBuilder<Window> {

	/*
	 *  Window structure can be very complex and so impact its rendering time.
	 *  Hence the idea here will be to initialize some component at demand and not at the window build.
	 *  The following component will be initialize at demand : view
	 *  
	 *  FIXME : For now the technique will use to implement component initialization at demand will be to keep its
	 *  corresponding builder into the window and run the build at demand.
	 *  LATER try to build the component itself and run the initialize method at demand
	 */
	
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
	
	Boolean getIsViewLazyLoadable();
	WindowBuilder setIsViewLazyLoadable(Boolean isViewLazyLoadable);

	DialogBuilder getDialog();
	DialogBuilder getDialog(Boolean injectIfNull);
	WindowBuilder setDialog(DialogBuilder dialog);
	
	WindowRenderType getRenderType();
	WindowBuilder setRenderType(WindowRenderType renderType);
	
	WindowContainerManagedWindowBuilder getContainerManaged();
	WindowBuilder setContainerManaged(WindowContainerManagedWindowBuilder containerManaged);
}
