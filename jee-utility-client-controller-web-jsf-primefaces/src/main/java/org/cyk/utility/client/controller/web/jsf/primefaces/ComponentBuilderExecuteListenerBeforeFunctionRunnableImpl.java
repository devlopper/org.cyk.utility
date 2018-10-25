package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerBefore;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.InvisibleComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;

public class ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderExecuteListenerBefore> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				Component component = getFunction().getComponent();
				
				if(component instanceof VisibleComponent) {
					VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
					VisibleComponent visibleComponent = (VisibleComponent) component;
					visibleComponentBuilder.getStyle(Boolean.TRUE).addClasses("cyk_component");
					
					if(visibleComponent instanceof InputOutput<?>) {
						InputOutputBuilder<?, ?> inputOutputBuilder = (InputOutputBuilder<?,?>) visibleComponentBuilder;
						
						if(inputOutputBuilder instanceof InputBuilder<?,?>) {
							InputBuilder<?,?> inputBuilder = (InputBuilder<?,?>)componentBuilder;
							//Input<?> input = (Input<?>) inputOutput;
							inputBuilder.getStyle(Boolean.TRUE).addClasses("cyk_input");
						}
					}
				}
				
				if(componentBuilder instanceof InvisibleComponentBuilder<?>) {
					InvisibleComponentBuilder<?> invisibleComponentBuilder = (InvisibleComponentBuilder<?>) componentBuilder;
					
					if(invisibleComponentBuilder instanceof LayoutBuilder) {
						((Layout)component).getProperties().setClass("ui-g");	
					}
				}
			}
		});
	}
	
}