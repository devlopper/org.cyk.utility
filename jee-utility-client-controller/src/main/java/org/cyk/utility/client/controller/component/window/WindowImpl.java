package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.output.OutputStringTextMap;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.scope.Scope;
import org.cyk.utility.scope.ScopeSession;

public class WindowImpl extends AbstractVisibleComponentImpl implements Window,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText title;
	private View view;
	private MenuMap menuMap;
	private Dialog dialog;
	private WindowRenderType renderType;
	private OutputStringTextMap outputStringTextMap;
	
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
	public OutputStringTextMap getOutputStringTextMap() {
		return outputStringTextMap;
	}
	
	@Override
	public Window setOutputStringTextMap(OutputStringTextMap outputStringTextMap) {
		this.outputStringTextMap = outputStringTextMap;
		return this;
	}
	
	@Override
	public OutputStringText getOutputStringText(String key) {
		OutputStringTextMap outputStringTextMap = getOutputStringTextMap();
		return outputStringTextMap == null ? null : outputStringTextMap.get(key);
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
	public MenuMap getMenuMap() {
		return menuMap;
	}
	
	@Override
	public Window setMenuMap(MenuMap menuMap) {
		this.menuMap = menuMap;
		return this;
	}
	
	@Override
	public Dialog getDialog() {
		return dialog;
	}
	
	@Override
	public Window setDialog(Dialog dialog) {
		this.dialog = dialog;
		return this;
	}
	
	@Override
	public WindowRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public Window setRenderType(WindowRenderType renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public Menu getMenu(Class<? extends Scope> scopeClass) {
		MenuMap menuMap = getMenuMap();
		return menuMap == null ? null : menuMap.get(scopeClass);
	}
	
	@Override
	public Menu getMenuOfScopeSession() {
		return getMenu(ScopeSession.class);
	}

}
