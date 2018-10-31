package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.View;

public class WindowImpl extends AbstractVisibleComponentImpl implements Window,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText title;
	private View view;
	private Theme theme;
	
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

}
