package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderPostConstructListener;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputFileBuilder;
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.random.RandomHelper;

public class ComponentBuilderPostConstructListenerFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderPostConstructListener> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String IDENTIFIER_FORMAT = "%s_%s";
	
	public ComponentBuilderPostConstructListenerFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				Properties outputProperties = componentBuilder.getOutputProperties(Boolean.TRUE);
				
				outputProperties.setIdentifier(String.format(IDENTIFIER_FORMAT,componentBuilder.getComponentClass().getSimpleName(),__inject__(RandomHelper.class).getAlphabetic(3)));
				outputProperties.setIdentifierAsStyleClass(outputProperties.getIdentifier().toString());
				outputProperties.setWidgetVar(outputProperties.getIdentifier());
				outputProperties.setRendered(Boolean.TRUE);						
				
				if(componentBuilder instanceof VisibleComponentBuilder<?>) {
					VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
					
					if(visibleComponentBuilder instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) visibleComponentBuilder;
						inputBuilder.getMessage(Boolean.TRUE);
						if(visibleComponentBuilder instanceof InputFileBuilder) {
							InputFileBuilder inputFileBuilder = (InputFileBuilder) visibleComponentBuilder;
							inputFileBuilder.addQualifiers(Primefaces.class);
						}
					}
					
					if(visibleComponentBuilder instanceof TreeBuilder) {
						TreeBuilder treeBuilder = (TreeBuilder) visibleComponentBuilder;
						treeBuilder.getDefaultNodeFamilies(Boolean.TRUE).add("default");
						treeBuilder.addQualifiers(Primefaces.class);
					}
				}
			}
		});
	}
	
}