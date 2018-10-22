package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfter;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;

public class ComponentBuilderExecuteListenerAfterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderExecuteListenerAfter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentBuilderExecuteListenerAfterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				Component component = getFunction().getComponent();
				
				if(component instanceof OutputString) {
					OutputString outputString = (OutputString) component;
					outputString.setValue((String)outputString.getPropertyValue());
					
					if(outputString instanceof OutputStringLabel) {
						OutputStringLabel outputStringLabel = (OutputStringLabel) outputString;
						outputStringLabel.getProperties().setFor( ((OutputStringLabelBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );	
					}
					
					if(outputString instanceof OutputStringMessage) {
						OutputStringMessage outputStringMessage = (OutputStringMessage) outputString;
						outputStringMessage.getProperties().setFor( ((OutputStringMessageBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );
					}		
				}
				
				if(component instanceof Input) {
					Input<?> input = (Input<?>) component;
					if(input.getField()!=null)
						input.getProperties().setRequired(input.getField().getAnnotation(NotNull.class)!=null);
				}
			}
		});
	}
	
}