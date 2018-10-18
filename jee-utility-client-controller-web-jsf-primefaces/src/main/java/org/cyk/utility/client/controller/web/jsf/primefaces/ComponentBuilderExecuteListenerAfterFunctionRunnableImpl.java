package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfter;
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
				if(component instanceof OutputStringLabel) {
					OutputStringLabel outputStringLabel = (OutputStringLabel) component;
					outputStringLabel.getProperties().setFor( ((OutputStringLabelBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );
				}
				
				if(component instanceof OutputStringMessage) {
					OutputStringMessage outputStringMessage = (OutputStringMessage) component;
					outputStringMessage.getProperties().setFor( ((OutputStringMessageBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );
				}
				
			}
		});
	}
	
}