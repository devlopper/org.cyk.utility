package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.status;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.time.TimeHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RequestStatusPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Request status";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		CommandableBuilder commandableBuilderVeryShortRun = __inject__(CommandableBuilder.class).setName("Very Short Run(0s)")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						
					}
				});
		commandableBuilderVeryShortRun.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		CommandableBuilder commandableBuilderShortRun = __inject__(CommandableBuilder.class).setName("Short Run(3s)")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						__inject__(TimeHelper.class).pause(1000l*3);
					}
				});
		commandableBuilderShortRun.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		CommandableBuilder commandableBuilderLongRun = __inject__(CommandableBuilder.class).setName("Long Run(10s)")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						__inject__(TimeHelper.class).pause(1000l*10);
					}
				});
		commandableBuilderLongRun.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		CommandableBuilder commandableBuilderVeryLongRun = __inject__(CommandableBuilder.class).setName("Very Long Run(20s)")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						__inject__(TimeHelper.class).pause(1000l*20);
					}
				});
		commandableBuilderVeryLongRun.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(commandableBuilderVeryShortRun,commandableBuilderShortRun,commandableBuilderLongRun,commandableBuilderVeryLongRun)	
		;
		return viewBuilder;
	}
	
}
