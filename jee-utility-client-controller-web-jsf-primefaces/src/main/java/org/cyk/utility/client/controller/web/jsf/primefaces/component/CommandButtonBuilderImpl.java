package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.commandbutton.CommandButton;

public class CommandButtonBuilderImpl extends AbstractUIComponentBuilderImpl<CommandButton,Commandable> implements CommandButtonBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected CommandButton __execute__(Commandable commandable, ValueExpressionMap valueExpressionMap) throws Exception {
		CommandButton commandButton = new CommandButton();
		commandButton.setValue(commandable.getName());
		Object icon = commandable.getProperties().getIcon();
		if(icon!=null)
			commandButton.setIcon(icon.toString());
		
		Object tooltip = commandable.getTooltip();
		if(tooltip!=null)
			commandButton.setTitle(tooltip.toString());
		
		Object onComplete = commandable.getProperties().getOnComplete();
		if(onComplete!=null)
			commandButton.setOncomplete(onComplete.toString());
		
		if(commandable.getNavigation()!=null) {
			commandButton.setType("button");
			String url = null;
			if(__injectCollectionHelper__().contains(commandable.getRoles(), ComponentRole.COLLECTION_PROCESSOR)) {
				url = commandable.getNavigation().getUniformResourceLocator().toString();
			}else if(__injectCollectionHelper__().contains(commandable.getRoles(), ComponentRole.COLLECTION_ITEM_PROCESSOR)) {
				SystemAction action = commandable.getNavigation().getSystemAction();	
				String methodName = __injectStringHelper__().applyCase(StringUtils.substringBefore(action.getClass().getSimpleName(),"Impl")+"Class",Case.FIRST_CHARACTER_LOWER);
				if(__injectStringHelper__().isNotBlank(methodName))
					url = "#{indexRow.getUrlBySystemActionClass(componentHelper."+methodName+")}";
			}
			if(__inject__(StringHelper.class).isNotBlank(url))
				valueExpressionMap.set("onclick",__buildValueExpressionString__("window.open('"+url+"','_self');return false;"));
		}else if(commandable.getCommand()!=null) {
			commandButton.setType("submit");
			
			String update = __injectPrimefacesHelper__().computeAttributeUpdate(commandable,__inject__(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
			
			String commandableIdentifier = commandable.getIdentifier().toString();
			if(__inject__(StringHelper.class).isNotBlank(commandable.getCommand().getContainerContextDependencyInjectionBeanName())) {
				String actionExpressionLanguage = commandable.getCommand().getContainerContextDependencyInjectionBeanName()+".getCommandableByIdentifier('"+commandableIdentifier+"').command.function.executeToReturnVoid";
				commandButton.setActionExpression(__inject__(JavaServerFacesHelper.class).buildMethodExpression(actionExpressionLanguage, Void.class,new Class<?>[] {}));	
			}
		
			commandButton.setUpdate(update);
			
			//commandButton.setImmediate(Boolean.TRUE);
			//System.out.println("CommandButtonBuilder.build() UPDATE : "+update);
		}else {
			commandButton.setType("button");
			if(commandable.getProperties().getOnClick()!=null)
				valueExpressionMap.set("onclick",__buildValueExpressionString__(commandable.getProperties().getOnClick().toString())) ;
		}
		Object ajax = commandable.getProperties().getAjax();
		if(ajax!=null)
			commandButton.setAjax((Boolean) ajax);
		
		Events events = commandable.getEvents();
		if(__inject__(CollectionHelper.class).isNotEmpty(events)) {
			String commandableIdentifier = commandable.getIdentifier().toString();
			for(Event index : events.get()) {
				if(index.getScript()==null) {
					//String actionExpressionLanguage = commandable.getCommand().getWindowContainerManaged().getContextDependencyInjectionBeanName()+".getCommandableByIdentifier('"+commandableIdentifier+"').events.getAt(0).properties.function.executeWithOneParameterToReturnVoid";
					String actionExpressionLanguage = commandable.getCommand().getContainerContextDependencyInjectionBeanName()+".getCommandableByIdentifier('"+commandableIdentifier+"').events.getAt(0).properties.function.executeWithOneParameterToReturnVoid";
					AjaxBehavior behavior = (AjaxBehavior) FacesContext.getCurrentInstance().getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
					behavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(__inject__(JavaServerFacesHelper.class).buildMethodExpression(actionExpressionLanguage, Void.class,new Class<?>[] {})
							, __inject__(JavaServerFacesHelper.class).buildMethodExpression(actionExpressionLanguage, Void.class,new Class<?>[] {Object.class})));
					
					if(index.getProperties().getUpdate()!=null)
						behavior.setUpdate(index.getProperties().getUpdate().toString());
					//behavior.setListener(methodExpression);
					
					commandButton.addClientBehavior(index.getProperties().getEvent().toString(), behavior);	
				}
			}
		}
		
		return commandButton;
	}
	
}
