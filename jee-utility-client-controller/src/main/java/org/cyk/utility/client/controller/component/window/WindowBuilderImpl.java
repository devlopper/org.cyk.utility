package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public class WindowBuilderImpl extends AbstractVisibleComponentBuilderImpl<Window> implements WindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder title;
	private ViewBuilder view;
	private Theme theme;
	
	@Override
	protected void __execute__(Window window) {
		super.__execute__(window);
		OutputStringTextBuilder title = getTitle();
		if(title!=null)
			window.setTitle(title.execute().getOutput());
		
		ViewBuilder view = getView();
		if(view!=null)
			window.setView(view.execute().getOutput());
		
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
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_VIEW = "view";

}
