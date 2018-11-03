package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractWindowContainerManagedImpl extends AbstractObject implements WindowContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	private Window window;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		WindowBuilder windowBuilder = __getWindowBuilder__();
		if(windowBuilder!=null)
			setWindow(windowBuilder.execute().getOutput());
		
		Window window = getWindow();
		if(window!=null) {
			Theme theme = window.getTheme();
			if(theme == null) {
				Class<? extends Theme> themeClass = __getThemeClass__();
				if(themeClass!=null) {
					theme = __inject__(themeClass);
					window.setTheme(theme);
				}
			}
			
			if(theme!=null) {
				theme.process(window);	
			}	
		}
	}
	
	protected <THEME extends Theme> Class<THEME> __getThemeClass__(){
		return __inject__(ThemeClassGetter.class).execute().getOutput();
	}
	
	@Override
	public Window getWindow() {
		return window;
	}
	
	@Override
	public WindowContainerManaged setWindow(Window window) {
		this.window = window;
		return this;
	}
	
	/**/
	
	protected String __getWindowTitleValue__() {
		return null;
	}
	
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = __injectWindowBuilder__().setView(__getViewBuilder__()).setMenuMap(__getMenuBuilderMap__());
		String titleValue = __getWindowTitleValue__();
		if(__inject__(StringHelper.class).isNotBlank(titleValue))
			windowBuilder.setTitleValue(titleValue);
		return windowBuilder;
	}
	
	protected ViewBuilder __getViewBuilder__() {
		return null;
	}
	
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return null;
	}
	
	protected WindowBuilder __injectWindowBuilder__() {
		return __inject__(WindowBuilder.class);
	}
}
