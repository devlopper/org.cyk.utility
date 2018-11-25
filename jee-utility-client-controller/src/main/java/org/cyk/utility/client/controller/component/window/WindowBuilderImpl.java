package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.scope.Scope;

public class WindowBuilderImpl extends AbstractVisibleComponentBuilderImpl<Window> implements WindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder title;
	private ViewBuilder view;
	private Theme theme;
	private MenuBuilderMap menuMap;
	
	@Override
	protected void __execute__(Window window) {
		super.__execute__(window);
		OutputStringTextBuilder title = getTitle();
		if(title!=null)
			window.setTitle(title.execute().getOutput());
		ViewBuilder view = getView();
		if(view!=null)
			window.setView(view.execute().getOutput());
		MenuBuilderMap menuMap = getMenuMap();
		if(menuMap!=null) {
			window.setMenuMap(__inject__(MenuMap.class));
			for(Map.Entry<Scope,MenuBuilder> entry : menuMap.getEntries())
				window.getMenuMap().set(entry.getKey(),entry.getValue().execute().getOutput());
		}
		Theme theme = getTheme();
		window.setTheme(theme);
	}
	
	@Override
	public OutputStringTextBuilder getTitle() {
		return title;
	}

	@Override
	public OutputStringTextBuilder getTitle(Boolean injectIfNull) {
		return (OutputStringTextBuilder) __getInjectIfNull__(FIELD_TITLE, injectIfNull);
	}
	
	@Override
	public WindowBuilder setTitleValue(String titleValue) {
		getTitle(Boolean.TRUE).setValue(titleValue);
		return this;
	}
	
	@Override
	public WindowBuilder setTitle(OutputStringTextBuilder title) {
		this.title = title;
		return this;
	}

	@Override
	public ViewBuilder getView() {
		return view;
	}
	
	@Override
	public ViewBuilder getView(Boolean injectIfNull) {
		return (ViewBuilder) __getInjectIfNull__(FIELD_VIEW, injectIfNull);
	}

	@Override
	public WindowBuilder setView(ViewBuilder view) {
		this.view = view;
		return this;
	}
	
	@Override
	public Theme getTheme() {
		return theme;
	}
	
	@Override
	public WindowBuilder setTheme(Theme theme) {
		this.theme = theme;
		return this;
	}
	
	@Override
	public MenuBuilderMap getMenuMap() {
		return menuMap;
	}
	
	@Override
	public MenuBuilderMap getMenuMap(Boolean injectIfNull) {
		return (MenuBuilderMap) __getInjectIfNull__(FIELD_MENU_MAP, injectIfNull);
	}
	
	@Override
	public WindowBuilder setMenuMap(MenuBuilderMap menuMap) {
		this.menuMap = menuMap;
		return this;
	}
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_VIEW = "view";
	public static final String FIELD_MENU_MAP = "menuMap";

}
