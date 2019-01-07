package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractWindowContainerManagedImpl extends AbstractObject implements WindowContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	private Window window;
	private WindowBuilder __windowBuilder__;
	private String contextDependencyInjectionBeanName;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setContextDependencyInjectionBeanName(__inject__(StringHelper.class).getVariableNameFrom(getClass().getSimpleName()));
	}
	
	protected <THEME extends Theme> Class<THEME> __getThemeClass__(){
		return __inject__(ThemeClassGetter.class).execute().getOutput();
	}
	
	@Override
	public Window getWindow() {
		if(__windowBuilder__ == null) {
			try {
				__windowBuilder__ = __getWindowBuilder__();
			} catch (Exception exception) {
				__windowBuilder__ = __inject__(WindowContainerManagedWindowBuilderThrowable.class).setThrowable(exception).execute().getOutput();
			}
			setWindow(__windowBuilder__.execute().getOutput());
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
		return window;
	}
	
	@Override
	public WindowContainerManaged setWindow(Window window) {
		this.window = window;
		return this;
	}
	
	/**/
	
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = null;
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = __getWindowContainerManagedWindowBuilder__();
		if(windowContainerManagedWindowBuilder == null) {
			windowBuilder = __injectWindowBuilder__().setView(__getViewBuilder__()).setMenuMap(__getMenuBuilderMap__());
			String titleValue = __getWindowTitleValue__();
			if(__inject__(StringHelper.class).isNotBlank(titleValue))
				windowBuilder.setTitleValue(titleValue);	
		}else {
			windowBuilder = windowContainerManagedWindowBuilder.setWindowContainerManaged(this).execute().getOutput();
		}
		return windowBuilder;
	}
	
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return null;
	}
	
	protected String __getWindowTitleValue__() {
		return null;
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
	
	public Commandable getCommandableByIdentifier(Object identifier) {
		Commandable commandable = getWindow().getView().getComponents().getCommandableByIdentifier(identifier, Boolean.TRUE);
		if(commandable == null) {
			CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class)
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						__inject__(ThrowableHelper.class).throwRuntimeException("Commandable with identifier <<"+identifier+">> not found.");
					}
				});
			commandable = commandableBuilder.execute().getOutput();
		}
		return commandable;
	}
	
	@Override
	public String getContextDependencyInjectionBeanName() {
		return contextDependencyInjectionBeanName;
	}
	
	@Override
	public WindowContainerManaged setContextDependencyInjectionBeanName(String contextDependencyInjectionBeanName) {
		this.contextDependencyInjectionBeanName = contextDependencyInjectionBeanName;
		return this;
	}
}
