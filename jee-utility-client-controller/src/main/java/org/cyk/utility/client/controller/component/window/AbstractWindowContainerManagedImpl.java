package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderType;
import org.cyk.utility.client.controller.session.SessionUser;
import org.cyk.utility.client.controller.session.SessionUserGetter;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverity;
import org.cyk.utility.notification.NotificationSeverityError;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;
import org.cyk.utility.request.RequestGetter;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractWindowContainerManagedImpl extends AbstractObject implements WindowContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	private Window window;
	private WindowBuilder __windowBuilder__;
	private String contextDependencyInjectionBeanName;
	protected SessionUser sessionUser;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setContextDependencyInjectionBeanName(__inject__(StringHelper.class).getVariableNameFrom(getClass().getSimpleName()));
		setSessionUser(__getSessionUser__());
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
				exception.printStackTrace();
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
	
	@Override
	public SessionUser getSessionUser() {
		return sessionUser;
	}
	
	@Override
	public WindowContainerManaged setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
		return this;
	}
	
	/**/
	
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = null;
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = __getWindowContainerManagedWindowBuilder__();
		if(windowContainerManagedWindowBuilder == null) {
			windowBuilder = __injectWindowBuilder__().setView(__getViewBuilder__()).setMenuMap(__getMenuBuilderMap__());
		}else {
			if(windowContainerManagedWindowBuilder.getRequest() == null)
				windowContainerManagedWindowBuilder.setRequest(__getRequest__());
			windowBuilder = windowContainerManagedWindowBuilder.setWindowContainerManaged(this).execute().getOutput();
		}
		
		if(windowBuilder!=null) {
			String titleValue = __getWindowTitleValue__();
			if(__inject__(StringHelper.class).isNotBlank(titleValue))
				windowBuilder.setTitleValue(titleValue);	
		}
		return windowBuilder;
	}
	
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return __inject__(WindowContainerManagedWindowBuilderBlank.class);
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
	
	protected static SessionUser __getSessionUser__() {
		return __inject__(SessionUserGetter.class).execute().getOutput();
	}
	
	protected Object __getRequest__() {
		return __inject__(RequestGetter.class).execute().getOutput();
	}
	
	/**/
	
	protected static void __renderMessage__(String summary,String details,Class<? extends NotificationSeverity> notificationSeverityClass,Class<? extends MessageRenderType>...renderTypeClasses) {
		MessageRender messageRender = __inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class).setSummary(summary).setDetails(details)
				.setSeverity(__inject__(notificationSeverityClass)));	
		if(__inject__(ArrayHelper.class).isNotEmpty(renderTypeClasses))
			for(Class<? extends MessageRenderType> index : renderTypeClasses)
				messageRender.addTypes(__inject__(index)).execute();
		messageRender.execute();
	}
	
	protected static void __renderMessageInformation__(String summary,String details,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessage__(summary, details, NotificationSeverityInformation.class, renderTypeClasses);
	}
	
	protected static void __renderMessageInformation__(String summary,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessageInformation__(summary, summary, renderTypeClasses);
	}
	
	protected static void __renderMessageWarning__(String summary,String details,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessage__(summary, details, NotificationSeverityWarning.class, renderTypeClasses);
	}
	
	protected static void __renderMessageWarning__(String summary,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessageWarning__(summary, summary, renderTypeClasses);
	}
	
	protected static void __renderMessageError__(String summary,String details,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessage__(summary, details, NotificationSeverityError.class, renderTypeClasses);
	}
	
	protected static void __renderMessageError__(String summary,Class<? extends MessageRenderType>...renderTypeClasses) {
		__renderMessageError__(summary, summary, renderTypeClasses);
	}

	/**/
	
	protected <T> T ____getProxy____(Class<T> aClass) {
		return __getProxyByRequest__(aClass,__getRequest__());
	}
}
