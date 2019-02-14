package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.dialog.DialogBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuGetter;
import org.cyk.utility.client.controller.component.menu.MenuMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilderMap;
import org.cyk.utility.client.controller.component.output.OutputStringTextMap;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.node.SystemNodeClient;

public class WindowBuilderImpl extends AbstractVisibleComponentBuilderImpl<Window> implements WindowBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder title;
	private OutputStringTextBuilderMap outputStringTextMap;
	private ViewBuilder view;
	private Theme theme;
	private MenuBuilderMap menuMap;
	private DialogBuilder dialog;
	private WindowRenderType renderType;
	
	@Override
	protected void __execute__(Window window) {
		super.__execute__(window);
		OutputStringTextBuilder applicationNameOutputStringText = getOutputStringTextMap(Boolean.TRUE).get("applicationName");
		if(applicationNameOutputStringText == null) {
			applicationNameOutputStringText = getApplicationName(Boolean.TRUE);
		}else {
			
		}
		
		String applicationName = applicationNameOutputStringText.getValue();
		
		OutputStringTextBuilderMap outputStringTextMap = getOutputStringTextMap();
		if(outputStringTextMap!=null) {
			window.setOutputStringTextMap(__inject__(OutputStringTextMap.class));
			for(Map.Entry<String,OutputStringTextBuilder> entry : outputStringTextMap.getEntries()) {
				window.getOutputStringTextMap().set(entry.getKey(),entry.getValue().execute().getOutput());
			}
		}	
		
		WindowRenderType renderType = getRenderType();
		window.setRenderType(renderType);
		
		OutputStringTextBuilder title = getTitle();
		if(title!=null) {
			if(__inject__(StringHelper.class).isNotBlank(applicationName))
				title.setValue(applicationName+"|"+title.getValue());
			window.setTitle(title.execute().getOutput());
		}
		
		ViewBuilder view = getView();
		if(view!=null)
			window.setView(view.execute().getOutput());
		
		if(renderType == null || renderType instanceof WindowRenderTypeNormal) {
			MenuBuilderMap menuMap = getMenuMap();
			if(menuMap!=null) {
				window.setMenuMap(__inject__(MenuMap.class));
				for(@SuppressWarnings("rawtypes") Map.Entry<Class,MenuBuilder> entry : menuMap.getEntries())
					window.getMenuMap().set(entry.getKey(),__inject__(MenuGetter.class).setScopeClass(entry.getKey()).execute().getOutput());
			}	
		}
		
		Theme theme = getTheme();
		window.setTheme(theme);
		DialogBuilder dialog = getDialog(Boolean.TRUE);
		if(dialog!=null)
			window.setDialog(dialog.execute().getOutput());
	}
	
	@Override
	public OutputStringTextBuilder getApplicationName() {
		return getOutputStringTextMap(Boolean.TRUE).get("applicationName");
	}
	
	@Override
	public OutputStringTextBuilder getApplicationName(Boolean injectIfNull) {
		OutputStringTextBuilder outputStringText = getApplicationName();
		if(outputStringText == null) {
			outputStringText = __inject__(OutputStringTextBuilder.class);
			outputStringText.setValue(__inject__(SystemNodeClient.class).getName());
			outputStringText.addStyleClasses("cyk_component_window_application_name");
			setOutputStringTexts("applicationName",outputStringText);
		}
		return outputStringText;
	}
	
	@Override
	public WindowBuilder setApplicationName(OutputStringTextBuilder applicationName) {
		getOutputStringTextMap(Boolean.TRUE).set("applicationName",applicationName);
		return this;
	}
	
	@Override
	public WindowBuilder setApplicationNameValue(String applicationNameValue) {
		getApplicationName(Boolean.TRUE).setValue(applicationNameValue);
		return this;
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
	public OutputStringTextBuilderMap getOutputStringTextMap() {
		return outputStringTextMap;
	}
	
	@Override
	public OutputStringTextBuilderMap getOutputStringTextMap(Boolean injectIfNull) {
		return (OutputStringTextBuilderMap) __getInjectIfNull__(FIELD_OUTPUT_STRING_TEXT_MAP, injectIfNull);
	}
	
	@Override
	public WindowBuilder setOutputStringTextMap(OutputStringTextBuilderMap outputStringTextMap) {
		this.outputStringTextMap = outputStringTextMap;
		return this;
	}
	
	@Override
	public WindowBuilder setOutputStringTexts(Object...keyValues) {
		getOutputStringTextMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	@Override
	public WindowBuilder setOutputStringTextValue(String key, String value) {
		getOutputStringTextMap(Boolean.TRUE).get(key, Boolean.TRUE).setValue(value);
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
	
	@Override
	public DialogBuilder getDialog() {
		return dialog;
	}
	
	@Override
	public DialogBuilder getDialog(Boolean injectIfNull) {
		DialogBuilder dialog = (DialogBuilder) __getInjectIfNull__(FIELD_DIALOG, injectIfNull);
		
		return dialog;
	}
	
	@Override
	public WindowBuilder setDialog(DialogBuilder dialog) {
		this.dialog = dialog;
		return this;
	}
	
	@Override
	public WindowRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public WindowBuilder setRenderType(WindowRenderType renderType) {
		this.renderType = renderType;
		return this;
	}
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_VIEW = "view";
	public static final String FIELD_OUTPUT_STRING_TEXT_MAP = "outputStringTextMap";
	public static final String FIELD_MENU_MAP = "menuMap";
	public static final String FIELD_DIALOG = "dialog";

}
