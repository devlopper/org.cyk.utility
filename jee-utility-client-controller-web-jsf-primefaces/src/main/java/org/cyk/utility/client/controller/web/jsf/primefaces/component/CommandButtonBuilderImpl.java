package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
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
		
		if(StringHelper.isNotBlank(commandable.getUniformResourceIdentifier())) {
			commandButton.setType("button");
			String url = null;
			if(CollectionHelper.contains(commandable.getRoles(), ComponentRole.COLLECTION_PROCESSOR)) {
				url = commandable.getUniformResourceIdentifier();
			}else if(CollectionHelper.contains(commandable.getRoles(), ComponentRole.COLLECTION_ITEM_PROCESSOR)) {
				SystemAction action = commandable.getSystemAction();	
				String methodName = StringHelper.applyCase(StringUtils.substringBefore(action.getClass().getSimpleName(),"Impl")+"Class",Case.FIRST_CHARACTER_LOWER);
				if(StringHelper.isNotBlank(methodName))
					url = "#{indexRow.getUrlBySystemActionClass(request,componentHelper."+methodName+")}";
			}else
				url = commandable.getUniformResourceIdentifier();
			valueExpressionMap.set("onclick",__buildValueExpressionString__("window.open('"+url+"','_self');return false;"));
		}else if(commandable.getCommand()!=null) {
			commandButton.setType("submit");
			
			String update = __injectPrimefacesHelper__().computeAttributeUpdate(commandable,__inject__(ComponentHelper.class).getGlobalMessagesTargetsIdentifiers());
			commandButton.setUpdate(update);
			
			String commandableIdentifier = commandable.getIdentifier().toString();
			if(StringHelper.isNotBlank(commandable.getCommand().getContainerContextDependencyInjectionBeanName())) {
				String actionExpressionLanguage = commandable.getCommand().getContainerContextDependencyInjectionBeanName()+".getCommandableByIdentifier('"+commandableIdentifier+"').command.function.executeToReturnVoid";
				commandButton.setActionExpression(__inject__(JavaServerFacesHelper.class).buildMethodExpression(actionExpressionLanguage, Void.class,new Class<?>[] {}));	
			}
			
			//commandButton.setImmediate(Boolean.TRUE);
			//System.out.println("CommandButtonBuilder.build() UPDATE : "+update);
		}else {
			commandButton.setType("button");
			if(commandable.getProperties().getOnClick()!=null)
				valueExpressionMap.set("onclick",__buildValueExpressionString__(commandable.getProperties().getOnClick().toString())) ;
		}
		Object ajax = commandable.getProperties().getAjax();
		if(ajax == null && commandable.getCommand()!=null)
			ajax = Boolean.FALSE.equals(commandable.getCommand().getIsSynchronous());
		if(ajax!=null)
			commandButton.setAjax((Boolean) ajax);
		
		Events events = commandable.getEvents();
		if(CollectionHelper.isNotEmpty(events)) {
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
