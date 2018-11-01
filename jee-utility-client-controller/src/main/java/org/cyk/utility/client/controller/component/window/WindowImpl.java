package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.scope.Scope;

public class WindowImpl extends AbstractVisibleComponentImpl implements Window,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText title;
	private View view;
	private Theme theme;
	private MenuMap menuMap;
	
	@Override
	public OutputStringText getTitle() {
		return title;
	}

	@Override
	public Window setTitle(OutputStringText title) {
		this.title = title;
		return this;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public Window setView(View view) {
		this.view = view;
		return this;
	}
	
	@Override
	public Theme getTheme() {
		return theme;
	}
	
	@Override
	public Window setTheme(Theme theme) {
		this.theme = theme;
		return this;
	}
	
	@Override
	public MenuMap getMenuMap() {
		return menuMap;
	}
	
	@Override
	public Window setMenuMap(MenuMap menuMap) {
		this.menuMap = menuMap;
		return this;
	}
	
	@Override
	public Menu getMenu(Class<? extends Scope> scopeClass) {
		MenuMap menuMap = getMenuMap();
		return menuMap == null ? null : menuMap.get(scopeClass);
	}

}
