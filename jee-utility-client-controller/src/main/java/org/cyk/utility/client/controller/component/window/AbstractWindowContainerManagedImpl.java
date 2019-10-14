package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.__kernel__.session.Session;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetter;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderType;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverity;
import org.cyk.utility.notification.NotificationSeverityError;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;
import org.cyk.utility.request.RequestGetter;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractWindowContainerManagedImpl extends AbstractObject implements WindowContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	protected SystemAction systemAction;
	protected Window window;
	protected WindowBuilder __windowBuilder__;
	protected String contextDependencyInjectionBeanName;
	@Getter @Setter @Accessors(chain=true) protected Session session;
	
	@Getter protected Boolean __isInternalLoggable__;
	@Getter protected String __windowBuildDuration__;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setContextDependencyInjectionBeanName(StringHelper.getVariableNameFrom(getClass().getSimpleName()));
		setSystemAction(__getProperty__(WindowContainerManagedProperty.SYSTEM_ACTION, SystemAction.class));
		session = SessionHelper.getAttributeSession(Boolean.TRUE);
	}
	
	protected <THEME extends Theme> Class<THEME> __getThemeClass__(){
		return __inject__(ThemeClassGetter.class).execute().getOutput();
	}
	
	@Override
	public Window getWindow() {
		if(__windowBuilder__ == null)//TODO why not window == null ??
			__buildWindow__();
		return window;
	}
	
	protected void __buildWindow__() {
		Object request = __getRequest__();
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		List<String> subDurations = new ArrayList<String>();
		try {
			__windowBuilder__ = __getWindowBuilder__(subDurations);
		} catch (Exception exception) {
			__windowBuilder__ = __inject__(WindowContainerManagedWindowBuilderThrowable.class).setThrowable(exception).setRequest(request)
					.setContext(__getContext__()).execute().getOutput();
			exception.printStackTrace();
		}
		
		DurationBuilder subDurationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		if(__windowBuilder__.getRequest() == null)
			__windowBuilder__.setRequest(request);
		
		//TODO you won't be able to customize a page with a specific theme one it has been set to session. how to make it possible then ???
		Theme theme = (Theme) SessionHelper.getAttributeValueFromRequest(SessionAttributeEnumeration.THEME,request);
		if(theme == null) {
			Class<? extends Theme> themeClass = __getThemeClass__();
			if(themeClass!=null) {
				theme = __inject__(themeClass);
				theme.setRequest(request).build();
				SessionHelper.setAttributeValueFromRequest(SessionAttributeEnumeration.THEME, theme,request);
				__windowBuilder__.setTheme(theme);
			}
		}
		
		subDurations.add("get theme : "+__inject__(DurationStringBuilder.class).setDurationBuilder(subDurationBuilder.setEndToNow()).execute().getOutput());
		subDurationBuilder.setBeginToNow();
		
		//__windowBuilder__.setLogLevel(LogLevel.INFO).setLoggable(Boolean.TRUE).addLogMessageBuilderParameter("Window build");
		Window window = __windowBuilder__.execute().getOutput();
		
		subDurations.add("run builder : "+__inject__(DurationStringBuilder.class).setDurationBuilder(subDurationBuilder.setEndToNow()).execute().getOutput());
		subDurationBuilder.setBeginToNow();
		if(window!=null) {
			if(theme!=null) {
				theme.process(window);	
			}
		}
		
		setWindow(window);
		String title = __getWindowTitleValue__();
		if(title == null && window!=null && window.getTitle()!=null)
			title = window.getTitle().getValue();
		
		//__windowBuilder__.addLogMessageBuilderParameter("title", title);
		//__windowBuilder__.addLogMessageBuilderParameter("view cached", window.isViewCached());
		
		__windowBuildDuration__ = __inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput();
		if(subDurations != null && !subDurations.isEmpty())
			__windowBuildDuration__ = __windowBuildDuration__  + subDurations;
		__logInfo__(String.format("Window container built. title=%s duration=%s. view cached=%s", title,__windowBuildDuration__,window.isViewCached()));
	}
	
	@Override
	public WindowContainerManaged setWindow(Window window) {
		this.window = window;
		return this;
	}
	
	/**/
	
	protected WindowBuilder __getWindowBuilder__(List<String> subDurations) {
		WindowBuilder windowBuilder = null;
		WindowContainerManagedWindowBuilder windowContainerManagedWindowBuilder = __getWindowContainerManagedWindowBuilder__();
		
		if(windowContainerManagedWindowBuilder == null) {
			ViewBuilder viewBuilder = __getViewBuilder__();			
			windowBuilder = __injectWindowBuilder__().setView(viewBuilder).setMenuMap(__getMenuBuilderMap__());
		}else {
			if(windowContainerManagedWindowBuilder.getRequest() == null)
				windowContainerManagedWindowBuilder.setRequest(__getRequest__());
			if(windowContainerManagedWindowBuilder.getContext() == null)
				windowContainerManagedWindowBuilder.setContext(__getContext__());
			if(windowContainerManagedWindowBuilder.getUniformResourceLocatorMap() == null)
				windowContainerManagedWindowBuilder.setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
			
			windowBuilder = windowContainerManagedWindowBuilder.setWindowContainerManaged(this).execute().getOutput();
		}
		
		if(windowBuilder!=null) {
			DurationBuilder subDurationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
			
			String titleValue = __getWindowTitleValue__();
			if(StringHelper.isNotBlank(titleValue))
				windowBuilder.setTitleValue(titleValue);	
			
			ViewBuilder view = __getViewBuilder__();
			if(view!=null) {
				if(view.getRequest() == null)
					view.setRequest(__getRequest__());
				if(view.getContext() == null)
					view.setContext(__getContext__());
				if(view.getUniformResourceLocatorMap() == null)
					view.setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
				windowBuilder.setView(view);
			}
			
			subDurations.add("process window builder : "+__inject__(DurationStringBuilder.class).setDurationBuilder(subDurationBuilder.setEndToNow()).execute().getOutput());
			subDurationBuilder.setBeginToNow();
		}
		if(windowBuilder.getContainerManaged() == null)
			windowBuilder.setContainerManaged(windowContainerManagedWindowBuilder);
		
		return windowBuilder;
	}
	
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return __inject__(WindowContainerManagedWindowBuilderGetter.class).setContainerManaged(this).execute().getOutput();
	}
	
	protected String __getWindowTitleValue__() {
		return null;
	}
	
	protected ViewBuilder __getViewBuilder__() {
		return null;
	}
	
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return __inject__(MenuBuilderMapGetter.class).setRequest(__getRequest__()).execute().getOutput();
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
						throw new RuntimeException("Commandable with identifier <<"+identifier+">> not found.");
					}
				});
			commandable = commandableBuilder.execute().getOutput();
		}
		return commandable;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public WindowContainerManaged setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
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
	
	protected Object __getRequest__() {
		return __inject__(RequestGetter.class).execute().getOutput();
	}
	
	protected Object __getContext__() {
		//TODO write ContextGetter
		return null;
	}
	
	protected Object __getUniformResourceLocatorMap__() {
		//TODO write NavigationIdentifierStringMapGetter
		return null;
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
	
	protected <T> T __getProperty__(WindowContainerManagedProperty property,Class<T> aClass) {
		return __inject__(WindowContainerManagedPropertyValueGetter.class).setContainerManaged(this).setProperty(property).execute().getOutputAs(aClass);
	}
	
	/**/

}
